package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.Motor;

public class DriveSubsystem extends SubsystemBase {
    private Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private MecanumDrive mecanumDrive;

    public DriveSubsystem(Motor frontLeftMotor, Motor frontRightMotor, Motor backLeftMotor, Motor backRightMotor) {
        this.frontLeftMotor = frontLeftMotor;
        this.frontRightMotor = frontRightMotor;
        this.backLeftMotor = backLeftMotor;
        this.backRightMotor = backRightMotor;

        mecanumDrive = new MecanumDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        mecanumDrive.driveRobotCentric(strafeSpeed, forwardSpeed, rotationSpeed);
    }

    public void driveFieldCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed, double heading) {
        mecanumDrive.driveFieldCentric(strafeSpeed, forwardSpeed, rotationSpeed, heading);
    }
}
