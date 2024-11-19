package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import org.firstinspires.ftc.teamcode.Robot;

public class DriveSubsystem extends SubsystemBase {
    private Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;
    private MecanumDrive mecanumDrive;

    public DriveSubsystem(Robot robot) {
        this.frontLeftMotor = robot.frontLeftMotor;
        this.frontRightMotor = robot.frontRightMotor;
        this.backLeftMotor = robot.backLeftMotor;
        this.backRightMotor = robot.backRightMotor;

        mecanumDrive = new MecanumDrive(frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor);
    }

    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        mecanumDrive.driveRobotCentric(strafeSpeed, forwardSpeed, rotationSpeed);
    }

    public void driveFieldCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed, double heading) {
        mecanumDrive.driveFieldCentric(strafeSpeed, forwardSpeed, rotationSpeed, heading);
    }
}
