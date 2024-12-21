package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.drive.StandardTrackingWheelLocalizer;

import java.util.ArrayList;

@Config
public class DriveSubsystem extends SubsystemBase {
    private final Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private final MecanumDrive mecanumDrive;
    private IMU imu;
    private Telemetry telemetry;

    private double heading;
    private StandardTrackingWheelLocalizer localizer;
//    private HolonomicOdometry odometry;

    public static double VELOCITY_CURVE_EXPONENT = 1;
    public static double TRACK_WIDTH = 10.80;
    public static double CENTER_WHEEL_OFFSET = 1;
    public static boolean USE_IMU_OVER_DEAD_WHEELS = true;

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

        this.imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP
        ));
        imu.initialize(parameters);
        imu.resetYaw();

        mecanumDrive = new MecanumDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);

        //TODO: https://learnroadrunner.com/advanced.html#using-road-runner-in-teleop
        localizer = new StandardTrackingWheelLocalizer(hardwareMap, new ArrayList<>(), new ArrayList<>());
        localizer.setPoseEstimate(new Pose2d(0, 0, 0));

    }

    @Override
    public void periodic() {
        localizer.update();

        telemetry.addData("Current Heading:", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        telemetry.addData("is field centric:", Constants.IS_FIELD_CENTRIC);
        telemetry.addData("roadrunner localizer (odo pods) heading: ", localizer.getPoseEstimate().getHeading());
        telemetry.addData("roadrunner localizer (odo pods) x: ", localizer.getPoseEstimate().getX());
        telemetry.addData("roadrunner localizer (odo pods) y: ", localizer.getPoseEstimate().getY());

//        odometry.updatePose();
    }

    public void resetIMU() {
        imu.resetYaw();
    }

    public void driveFieldCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        strafeSpeed = applyVelocityCurves(strafeSpeed);
        forwardSpeed = applyVelocityCurves(forwardSpeed);
        rotationSpeed = applyVelocityCurves(rotationSpeed);

        rotationSpeed *= 1.1;
        if (USE_IMU_OVER_DEAD_WHEELS) {
            heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
        } else {
            heading = localizer.getPoseEstimate().getHeading();
        }
        mecanumDrive.driveFieldCentric(strafeSpeed, forwardSpeed, rotationSpeed, heading);
    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        strafeSpeed = applyVelocityCurves(strafeSpeed);
        forwardSpeed = applyVelocityCurves(forwardSpeed);
        rotationSpeed = applyVelocityCurves(rotationSpeed);

        rotationSpeed *= 1.1;
        mecanumDrive.driveRobotCentric(strafeSpeed, forwardSpeed, rotationSpeed);
    }

    private double applyVelocityCurves(double inputSpeed) {
        return Math.signum(inputSpeed) * Math.pow(Math.abs(inputSpeed), VELOCITY_CURVE_EXPONENT);
    }

}
