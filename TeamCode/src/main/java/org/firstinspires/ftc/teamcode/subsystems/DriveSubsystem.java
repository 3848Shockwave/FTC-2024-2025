package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;

@Config
public class DriveSubsystem extends SubsystemBase {
    private final Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private final MecanumDrive mecanumDrive;
    private IMU imu;
    private Telemetry telemetry;

    private double heading;

    public static double VELOCITY_CURVE_EXPONENT = 1;

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
    }

    @Override
    public void periodic() {
        telemetry.addData("Current Heading:", imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
        telemetry.addData("is field centric:", Constants.IS_FIELD_CENTRIC);
    }

    public void resetIMU() {
        imu.resetYaw();
    }

    public void driveFieldCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        strafeSpeed = applyVelocityCurves(strafeSpeed);
        forwardSpeed = applyVelocityCurves(forwardSpeed);
        rotationSpeed = applyVelocityCurves(rotationSpeed);

        rotationSpeed *= 1.1;
        heading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
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

//    public void driveRobotCentric(double x, double y, double rx) {
//        y = -y;
//        // Denominator is the largest motor power (absolute value) or 1
//        // This ensures all the powers maintain the same ratio,
//        // but only if at least one is out of the range [-1, 1]
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        double frontLeftPower = (y + x + rx) / denominator;
//        double backLeftPower = (y - x + rx) / denominator;
//        double frontRightPower = (y - x - rx) / denominator;
//        double backRightPower = (y + x - rx) / denominator;
//
//        frontLeftMotor.set(frontLeftPower);
//        backLeftMotor.set(backLeftPower);
//        frontRightMotor.set(frontRightPower);
//        backRightMotor.set(backRightPower);
//    }

//    public void driveFieldCentric(double x, double y, double rx) {
//
//        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
//
//        // Rotate the movement direction counter to the bot's rotation
//        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
//        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);
//
//        rotX = rotX * 1.1;  // Counteract imperfect strafing
//
//        // Denominator is the largest motor power (absolute value) or 1
//        // This ensures all the powers maintain the same ratio,
//        // but only if at least one is out of the range [-1, 1]
//        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
//        double frontLeftPower = (rotY + rotX + rx) / denominator;
//        double backLeftPower = (rotY - rotX + rx) / denominator;
//        double frontRightPower = (rotY - rotX - rx) / denominator;
//        double backRightPower = (rotY + rotX - rx) / denominator;
//
//        frontLeftMotor.set(frontLeftPower);
//        backLeftMotor.set(backLeftPower);
//        frontRightMotor.set(frontRightPower);
//        backRightMotor.set(backRightPower);
//
//
//    }
}
