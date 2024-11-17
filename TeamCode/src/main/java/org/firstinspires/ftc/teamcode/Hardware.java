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
            DcMotorMap.put(DcMotorNames.get(4), hardwareMap.get(DcMotorEx.class, "spoolLeft"));
            DcMotorMap.put(DcMotorNames.get(5), hardwareMap.get(DcMotorEx.class, "spoolRight"));
        }
        if (ServoNames!=null){
            ServoMap.put(ServoNames.get(0), hardwareMap.get(ServoImplEx.class, "vertClawGrip"));
            ServoMap.put(ServoNames.get(1), hardwareMap.get(ServoImplEx.class, "vertClawPiv"));
            ServoMap.put(ServoNames.get(2), hardwareMap.get(ServoImplEx.class, "vertClawRot"));
            ServoMap.put(ServoNames.get(3), hardwareMap.get(ServoImplEx.class, "vertArmRotL"));
            ServoMap.put(ServoNames.get(4), hardwareMap.get(ServoImplEx.class, "vertArmRotR"));
            ServoMap.put(ServoNames.get(5), hardwareMap.get(ServoImplEx.class, "horzClawGrip"));
            ServoMap.put(ServoNames.get(6), hardwareMap.get(ServoImplEx.class, "horzClawPiv"));
            ServoMap.put(ServoNames.get(7), hardwareMap.get(ServoImplEx.class, "horzClawRot"));
            ServoMap.put(ServoNames.get(8), hardwareMap.get(ServoImplEx.class, "horzArmRotL"));
            ServoMap.put(ServoNames.get(9), hardwareMap.get(ServoImplEx.class, "horzArmRotR"));
            ServoMap.put(ServoNames.get(10), hardwareMap.get(ServoImplEx.class, "horzExtL"));
            ServoMap.put(ServoNames.get(11), hardwareMap.get(ServoImplEx.class, "horzExtR"));

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
    public double getDcMotorPower(String motorName) {
      return  DcMotorMap.get(motorName).getPower();
    }
    public double getServoPosition(String servoName) {
      return  ServoMap.get(servoName).getPosition();
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

    public double getEncoderValue(String motorName) {
        return DcMotorMap.get(motorName).getCurrentPosition();

    }
}
