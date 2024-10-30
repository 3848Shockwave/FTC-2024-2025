package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.internal.opmode.TelemetryImpl;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Robot {

    boolean fieldCentric = false;
    GamepadConfig gamepadConfig;
    Hardware hardware ;
    public Robot(boolean isFieldCentric, GamepadConfig gpConfig, Hardware hw){
        // Constructor
        this.fieldCentric = isFieldCentric;
        this.gamepadConfig = gpConfig;
        this.hardware=hw;
    }
    public void setup(){
        // Setup method
        hardware.setDcMotorZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.DcMotorMap.get("frontRight").setDirection(DcMotor.Direction.FORWARD);
        hardware.DcMotorMap.get("frontLeft").setDirection(DcMotor.Direction.REVERSE);
        hardware.DcMotorMap.get("backRight").setDirection(DcMotor.Direction.FORWARD);
        hardware.DcMotorMap.get("backLeft").setDirection(DcMotor.Direction.REVERSE);
        if (fieldCentric) {
            hardware.initIMU();
            hardware.getIMU().resetYaw();
        }
    }

    public ArrayList<Double> driveCalc(){
       ArrayList<Double> powers = new ArrayList<Double>();
        // Drive method
        if (fieldCentric){
            IMU emu = hardware.getIMU();
            double rotationCurve = gamepadConfig.getGamepad1RightStickX();
            // Field Centric Drive
            double y = gamepadConfig.getGamepad1LeftStickY(); // Remember, Y stick value is reversed
            double x = gamepadConfig.getGamepad1LeftStickX();
            double rx = rotationCurve;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepadConfig.getGamepad1Y()) {
                emu.resetYaw();
            }

            double botHeading = emu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            powers.add(frontLeftPower);
            powers.add(backLeftPower);
            powers.add(frontRightPower);
            powers.add(backRightPower);



        }
        else{
            // Robot Centric Drive
            double y = gamepadConfig.getGamepad1LeftStickY(); // Remember, Y stick value is reversed
            double x = gamepadConfig.getGamepad1LeftStickX();
            double rx = gamepadConfig.getGamepad1RightStickX();

            double frontLeftPower = y + x + rx;
            double backLeftPower = y - x + rx;
            double frontRightPower = y - x - rx;
            double backRightPower = y + x - rx;

            powers.add(frontLeftPower);
            powers.add(backLeftPower);
            powers.add(frontRightPower);
            powers.add(backRightPower);

        }
    return powers;
    }
    public void setPowers(ArrayList<Double> powers){
        // Set the powers to the motors
        hardware.setDcMotorPower("frontLeft",powers.get(0));
        hardware.setDcMotorPower("backLeft",powers.get(1));
        hardware.setDcMotorPower("frontRight",powers.get(2));
        hardware.setDcMotorPower("backRight",powers.get(3));

    }

}
