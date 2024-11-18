package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;

public class CommandTeleOp extends CommandOpMode {

    private Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    private DriveSubsystem driveSubsystem;
    private DriveCommand driveCommand;
    private GamepadEx gamepad;
    private RevIMU imu;

    @Override
    public void initialize() {
        frontLeftMotor = new Motor(hardwareMap, "frontLeft");
        frontRightMotor = new Motor(hardwareMap, "frontRight");
        backLeftMotor = new Motor(hardwareMap, "backLeft");
        backRightMotor = new Motor(hardwareMap, "backRight");

        gamepad = new GamepadEx(gamepad1);
        imu = new RevIMU(hardwareMap, "imu");
        driveSubsystem = new DriveSubsystem(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
        driveCommand = new DriveCommand(driveSubsystem, gamepad::getLeftX, gamepad::getLeftY, gamepad::getRightX, imu::getAbsoluteHeading);

    }
}
