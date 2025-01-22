package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.GoBildaPinpointDriver;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;

@Config
public class DriveSubsystem extends SubsystemBase {
    private final Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private final MecanumDrive mecanumDrive;
    private IMU imu;
    private GoBildaPinpointDriver pinpoint;
    private Telemetry telemetry;

    private double heading;
    public static double VELOCITY_CURVE_EXPONENT = 1;

    // how much to use pinpoint over imu (1 is only pinpoint, 0 is only imu)
    public static double PINPOINT_IMU_WEIGHT = 0.5;

    public DriveSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        frontLeftMotor = new Motor(hardwareMap, "frontLeft");
        frontRightMotor = new Motor(hardwareMap, "frontRight");
        backLeftMotor = new Motor(hardwareMap, "backLeft");
        backRightMotor = new Motor(hardwareMap, "backRight");

        frontLeftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        backLeftMotor.setInverted(true);
        frontLeftMotor.setInverted(true);
        frontRightMotor.setInverted(true);


        this.pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(PinpointDrive.PARAMS.xOffset, PinpointDrive.PARAMS.yOffset);
        pinpoint.setEncoderResolution(PinpointDrive.PARAMS.encoderResolution);
        pinpoint.setEncoderDirections(PinpointDrive.PARAMS.xDirection, PinpointDrive.PARAMS.yDirection);
        pinpoint.resetPosAndIMU();

        this.imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(parameters);
        imu.resetYaw();

        mecanumDrive = new MecanumDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);


    }

    @Override
    public void periodic() {

        pinpoint.update();

        telemetry.addData("pinpoint heading", Math.toDegrees(pinpoint.getHeading()));
        telemetry.addData("pinpoint status", pinpoint.getDeviceStatus());

        telemetry.addData("weighted heading", heading);

    }

    public void resetIMU() {
        imu.resetYaw();
    }

    public void driveFieldCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        strafeSpeed = applyVelocityCurves(strafeSpeed);
        forwardSpeed = applyVelocityCurves(forwardSpeed);
        rotationSpeed = applyVelocityCurves(rotationSpeed);

//        rotationSpeed *= 1.1;
        double imuHeading = AngleUnit.normalizeDegrees(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        double pinpointHeading = Math.toDegrees(pinpoint.getHeading());
        double weight = PINPOINT_IMU_WEIGHT;
        // failsafe for if imu is bad (0.0 is a bad value)
        if (imuHeading == 0.0) weight = 0;

        heading = weight * pinpointHeading + (1 - weight) * imuHeading;

        mecanumDrive.driveFieldCentric(strafeSpeed, forwardSpeed, rotationSpeed, heading);
    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        strafeSpeed = applyVelocityCurves(strafeSpeed);
        forwardSpeed = applyVelocityCurves(forwardSpeed);
        rotationSpeed = applyVelocityCurves(rotationSpeed);

//        rotationSpeed *= 1.1;
        mecanumDrive.driveRobotCentric(strafeSpeed, forwardSpeed, rotationSpeed);
    }

    private double applyVelocityCurves(double inputSpeed) {
        return Math.signum(inputSpeed) * Math.pow(Math.abs(inputSpeed), VELOCITY_CURVE_EXPONENT);
    }

}
