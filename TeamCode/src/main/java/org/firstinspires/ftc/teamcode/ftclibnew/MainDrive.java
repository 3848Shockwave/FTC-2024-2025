package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public class MainDrive extends LinearOpMode {
    private Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private Motor verticalSlideMotorTop, verticalSlideMotorBottom;
    private MecanumDrive mecanumDrive;
    private GamepadEx gamepad;
    private RevIMU imu;
    private enum DriveMode {
        ROBOT_CENTRIC,
        FIELD_CENTRIC
    }
    private DriveMode currentDriveMode;
    @Override
    public void runOpMode() throws InterruptedException {
        imu = new RevIMU(hardwareMap, "imu");

        frontLeftMotor = new Motor(hardwareMap, "frontLeft");
        frontRightMotor = new Motor(hardwareMap, "frontRight");
        backLeftMotor = new Motor(hardwareMap, "backLeft");
        backRightMotor = new Motor(hardwareMap, "backRight");

        verticalSlideMotorBottom = new Motor(hardwareMap, "verticalSlideBottom");
        verticalSlideMotorBottom.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorBottom.setPositionCoefficient(0.1);
        verticalSlideMotorBottom.setPositionTolerance(10);
        verticalSlideMotorBottom.setTargetPosition(5000);
        verticalSlideMotorBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        verticalSlideMotorTop = new Motor(hardwareMap, "verticalSlideBottom");
        verticalSlideMotorTop.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorTop.setPositionCoefficient(0.1);
        verticalSlideMotorTop.setPositionTolerance(10);
        verticalSlideMotorTop.setTargetPosition(5000);
        verticalSlideMotorTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        gamepad = new GamepadEx(gamepad1);

        mecanumDrive = new MecanumDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);

        waitForStart();
        while (opModeIsActive()) {
            update();
        }
    }
    private void update() {
        switch (currentDriveMode) {
            case FIELD_CENTRIC:
//                double currentBotHeading = imu.getHeading();
                double currentBotHeading = imu.getAbsoluteHeading();
                mecanumDrive.driveFieldCentric(gamepad.getLeftX(), gamepad.getLeftY(), gamepad.getRightX(), currentBotHeading);
            case ROBOT_CENTRIC:
                mecanumDrive.driveRobotCentric(gamepad.getLeftX(), gamepad.getLeftY(), gamepad.getRightX());
        }
    }
}
