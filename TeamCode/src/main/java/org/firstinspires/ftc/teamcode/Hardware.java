package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.hardware.Sensor;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import java.util.ArrayList;
import java.util.HashMap;

public class Hardware {
    private IMU imu;
    HardwareMap hardwareMap;
    HashMap<String, DcMotorEx> DcMotorMap = new HashMap<String, DcMotorEx>();
    HashMap<String, ServoImplEx> ServoMap = new HashMap<String, ServoImplEx>();
    public Hardware(ArrayList<String> DcMotorNames, ArrayList<String> ServoNames, HardwareMap hwMap) {
        this.hardwareMap = hwMap;

        if (DcMotorNames!=null) {
            DcMotorMap.put(DcMotorNames.get(0), hardwareMap.get(DcMotorEx.class, "frontRight"));
            DcMotorMap.put(DcMotorNames.get(1), hardwareMap.get(DcMotorEx.class, "frontLeft"));
            DcMotorMap.put(DcMotorNames.get(2), hardwareMap.get(DcMotorEx.class, "backRight"));
            DcMotorMap.put(DcMotorNames.get(3), hardwareMap.get(DcMotorEx.class, "backLeft"));
        }
    }
    public DcMotorEx getDcMotor(String motorName) {
        return DcMotorMap.get(motorName);
    }
    public ServoImplEx getServo(String servoName) {
        return ServoMap.get(servoName);
    }
    public void setDcMotorPower(String motorName, double power) {
        DcMotorMap.get(motorName).setPower(power);
    }
    public void setServoPosition(String servoName, double position) {
        ServoMap.get(servoName).setPosition(position);
    }
    public void getDcMotorPower(String motorName) {
        DcMotorMap.get(motorName).getPower();
    }
    public void getServoPosition(String servoName) {
        ServoMap.get(servoName).getPosition();
    }
    public void initIMU(){
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT, RevHubOrientationOnRobot.UsbFacingDirection.UP)));
    }
    public IMU getIMU(){
        return imu;
    }

    public void setDcMotorZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior behavior) {
        for (DcMotorEx motor : DcMotorMap.values()) {
            motor.setZeroPowerBehavior(behavior);
        }
    }
}
