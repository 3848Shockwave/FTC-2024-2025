package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

public class DriveSubsystem extends SubsystemBase {
    private Motor frontLeft, frontRight, backLeft, backRight;
    private MecanumDrive mecanumDrive;
    private IMU imu;


    public  DriveSubsystem(HardwareMap hardwareMap) {
        frontLeft = new Motor(hardwareMap, "frontLeft");
        frontRight = new Motor(hardwareMap, "frontRight");
        backLeft = new Motor(hardwareMap, "backLeft");
        backRight = new Motor(hardwareMap, "backRight");
        mecanumDrive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
        imu = hardwareMap.get(IMU.class, "imu");
//        imu
    }


    public void driveFieldCentric() {

    }
    public void driveRobotCentric(double strafeSpeed, double forwardSpeed, double rotationSpeed) {
        mecanumDrive.driveRobotCentric(strafeSpeed, forwardSpeed, rotationSpeed);
    }
}
