package org.firstinspires.ftc.teamcode;

import android.os.Handler;
import android.os.Looper;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import java.util.ArrayList;

public class Robot {

    boolean fieldCentric = false;
    boolean gripHorizontalToggled = false;
    boolean gripVerticalToggled = false;
    GamepadConfig gamepadConfig;
    double step = .01;
    int count =0;

    long toggleDelay = 350; // delay in milliseconds
    Hardware hardware;
    StateHandler handle;
    double threshold = 100;
    double maxThreshold=4450;
    public Robot(boolean isFieldCentric, GamepadConfig gpConfig, Hardware hw) {
        // Constructor
        this.fieldCentric = isFieldCentric;
        this.gamepadConfig = gpConfig;
        this.hardware = hw;
        handle = new StateHandler(hw);
    }

    public void setup() {
        // Setup method
        hardware.setDcMotorZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hardware.DcMotorMap.get("frontRight").setDirection(DcMotor.Direction.FORWARD);
        hardware.DcMotorMap.get("frontLeft").setDirection(DcMotor.Direction.REVERSE);
        hardware.DcMotorMap.get("backRight").setDirection(DcMotor.Direction.REVERSE);
        hardware.DcMotorMap.get("backLeft").setDirection(DcMotor.Direction.REVERSE);
        hardware.DcMotorMap.get("spoolLeft").setDirection(DcMotor.Direction.REVERSE);
        hardware.DcMotorMap.get("spoolRight").setDirection(DcMotor.Direction.FORWARD);
        if (fieldCentric) {
            hardware.initIMU();
            hardware.getIMU().resetYaw();
        }
        hardware.getDcMotor("spoolLeft").setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        hardware.getDcMotor("spoolLeft").setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public ArrayList<Double> driveCalc() {
        ArrayList<Double> powers = new ArrayList<Double>();
        // Drive method
        if (fieldCentric) {
            IMU emu = hardware.getIMU();
            double y = gamepadConfig.getGamepad1LeftStickY(); // Remember, Y stick value is reversed
            double x = gamepadConfig.getGamepad1LeftStickX();
            // Counteract imperfect strafing
            double rx = gamepadConfig.getGamepad1RightStickX();

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepadConfig.getGamepad1A()) {
                emu.resetYaw();
            }

            // get bot heading relative to control hub IMU
            double botHeading = emu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            rotX = rotX * 1.1; // counteract imperfect strafing

            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;


            powers.add(frontLeftPower);
            powers.add(backLeftPower);
            powers.add(frontRightPower);
            powers.add(backRightPower);


        } else {
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

    public void setPowers(ArrayList<Double> powers) {
        // Set the powers to the motors
        hardware.setDcMotorPower("frontLeft", powers.get(0));
        hardware.setDcMotorPower("backLeft", powers.get(1));
        hardware.setDcMotorPower("frontRight", powers.get(2));
        hardware.setDcMotorPower("backRight", powers.get(3));
    }

    public void checkStates() {
        if (gamepadConfig.getGamepad2RightStickY() > 0) {
            handle.extendHorizontal.setState(StateType.POSTIVE);
        } else if (gamepadConfig.getGamepad2RightStickY() < 0) {
            handle.extendHorizontal.setState(StateType.NEGATIVE);
        } else {
            handle.extendHorizontal.setState(StateType.INACTIVE);
        }
        if (gamepadConfig.getGamepad2LeftStickY() > 0) {
            handle.extendVertical.setState(StateType.POSTIVE);
        } else if (gamepadConfig.getGamepad2LeftStickY() < 0) {
            handle.extendVertical.setState(StateType.NEGATIVE);
        } else {
            handle.extendVertical.setState(StateType.INACTIVE);
        }
        if (gamepadConfig.getGamepad2A()) {
            gripHorizontalToggled = !gripHorizontalToggled;
            handle.gripHorizontal.setState(gripHorizontalToggled ? StateType.POSTIVE : StateType.NEGATIVE);
        }

        if (gamepadConfig.getGamepad2B()) {
            gripVerticalToggled = !gripVerticalToggled;
            handle.gripVertical.setState(gripVerticalToggled ? StateType.POSTIVE : StateType.NEGATIVE);
        }
        handle.handler();

    }

    public void cope() {
        //TODO (REMOVE ALL OF THIS AND WRITE THIS PROPERLY)
        if (gamepadConfig.getGamepad2LeftTrigger() > 0) {
            hardware.setServoPosition("horzExtL", hardware.getServoPosition("horzExtL") + .5 * step);
            hardware.setServoPosition("horzExtR", hardware.getServoPosition("horzExtR") - .5 * step);
        } else if (gamepadConfig.getGamepad2RightTrigger() > 0) {
            hardware.setServoPosition("horzExtL", hardware.getServoPosition("horzExtL") - .5 * step);
            hardware.setServoPosition("horzExtR", hardware.getServoPosition("horzExtR") + .5 * step);
        }


        if (gamepadConfig.getGamepad2RightBumper()
                && hardware.getEncoderValue("spoolLeft") <= maxThreshold
        ) {
            hardware.setDcMotorPower("spoolLeft", 1);
            hardware.setDcMotorPower("spoolRight", 1);
        } else if (gamepadConfig.getGamepad2LeftBumper()
                && hardware.getEncoderValue("spoolLeft") >= threshold
        ) {
            hardware.setDcMotorPower("spoolLeft", -1);
            hardware.setDcMotorPower("spoolRight", -1);
        } else if (!gamepadConfig.getGamepad2LeftBumper() && !gamepadConfig.getGamepad2RightBumper()) {
            hardware.setDcMotorPower("spoolLeft", 0);
            hardware.setDcMotorPower("spoolRight", 0);
        } else if (hardware.getEncoderValue("spoolLeft") >= maxThreshold || hardware.getEncoderValue("spoolLeft") <= threshold) {
            hardware.setDcMotorPower("spoolLeft", 0);
            hardware.setDcMotorPower("spoolRight", 0);

        }
        if (gamepadConfig.getGamepad2Y()) {
            long lastToggleTime = 0;
            if (System.currentTimeMillis() - lastToggleTime > toggleDelay) {
                boolean isClosed = hardware.getServoPosition("horzClawGrip") == servoPositions.HORZCLAWGRIPCLOSE.getPosition();
                hardware.setServoPosition("horzClawGrip", isClosed ? servoPositions.HORZCLAWGRIPOPEN.getPosition() : servoPositions.HORZCLAWGRIPCLOSE.getPosition());
                lastToggleTime = System.currentTimeMillis();
            }
        }
        if (gamepadConfig.getGamepad2B()) {
            long lastToggleTime = 0;
            boolean isClosed = hardware.getServoPosition("horzClawPiv") == servoPositions.HORZCLAWPIVCLOSE.getPosition();
            if (System.currentTimeMillis() - lastToggleTime > toggleDelay) {
                hardware.setServoPosition("horzClawPiv", isClosed ? servoPositions.HORZCLAWPIVOPEN.getPosition() : servoPositions.HORZCLAWPIVCLOSE.getPosition());
                lastToggleTime = System.currentTimeMillis();
            }
        }
        if (gamepadConfig.getGamepad2RightStickX() > 0) {
            hardware.setServoPosition("horzClawRot", hardware.getServoPosition("horzClawRot") + step);
        } else if (gamepadConfig.getGamepad2RightStickX() < 0) {
            hardware.setServoPosition("horzClawRot", hardware.getServoPosition("horzClawRot") - step);

        }
        if (gamepadConfig.getGamepad2RightStickY() > .5) {
            hardware.setServoPosition("horzArmRotL", hardware.getServoPosition("horzArmRotL") + step);
            hardware.setServoPosition("horzArmRotR", hardware.getServoPosition("horzArmRotR") - step);
        } else if (gamepadConfig.getGamepad2RightStickY() < -.5) {
            hardware.setServoPosition("horzArmRotL", hardware.getServoPosition("horzArmRotL") - step);
            hardware.setServoPosition("horzArmRotR", hardware.getServoPosition("horzArmRotR") + step);
        }
        if (gamepadConfig.getGamepad2DpadUp()) {
            long lastToggleTime = 0;
            if (System.currentTimeMillis() - lastToggleTime > toggleDelay) {
                boolean isClosed = hardware.getServoPosition("vertClawGrip") == servoPositions.VERTCLAWGRIPCLOSE.getPosition();
                hardware.setServoPosition("vertClawGrip", isClosed ? servoPositions.VERTCLAWGRIPOPEN.getPosition() : servoPositions.VERTCLAWGRIPCLOSE.getPosition());
                lastToggleTime = System.currentTimeMillis();
            }
        }
        if (gamepadConfig.getGamepad2DpadLeft()) {
            long lastToggleTime = 0;
            if (System.currentTimeMillis() - lastToggleTime > toggleDelay) {
                boolean isClosed = hardware.getServoPosition("vertClawPiv") == servoPositions.VERTCLAWPIVCLOSE.getPosition();
                hardware.setServoPosition("vertClawPiv", isClosed ? servoPositions.VERTCLAWPIVOPEN.getPosition() : servoPositions.VERTCLAWPIVCLOSE.getPosition());
                lastToggleTime = System.currentTimeMillis();
            }
        }
        if (gamepadConfig.getGamepad2LeftStickX() > 0) {
            hardware.setServoPosition("vertClawRot", hardware.getServoPosition("vertClawRot") + step);
        } else if (gamepadConfig.getGamepad2LeftStickX() < 0) {
            hardware.setServoPosition("vertClawRot", hardware.getServoPosition("vertClawRot") - step);

        }
        if (gamepadConfig.getGamepad2LeftStickY() > .5) {
            hardware.setServoPosition("vertArmRotL", hardware.getServoPosition("vertArmRotL") + step);
            hardware.setServoPosition("vertArmRotR", hardware.getServoPosition("vertArmRotR") - step);
        } else if (gamepadConfig.getGamepad2LeftStickY() < -.5) {
            hardware.setServoPosition("vertArmRotL", hardware.getServoPosition("vertArmRotL") - step);
            hardware.setServoPosition("vertArmRotR", hardware.getServoPosition("vertArmRotR") + step);
        }
        if (gamepadConfig.getGamepad2A()) {
            // Move retract spools to max
            //hardware.setDcMotorPower("spoolLeft", -1);
            // hardware.setDcMotorPower("spoolRight", -1);

            // Retract horzext to max
            hardware.setServoPosition("horzExtL", .9);
            hardware.setServoPosition("horzExtR", -.9);
            // hardware.setServoPosition("horzClawGrip", servoPositions.HORZCLAWGRIPCLOSE.getPosition());
            //hardware.setServoPosition("vertClawGrip", servoPositions.VERTCLAWGRIPCLOSE.getPosition());
            // Set horz and vert pivs to transfer enum
            hardware.setServoPosition("horzClawPiv", servoPositions.HORZPIVTRANSFER.getPosition());

            // Start the sequential operations
            hardware.setServoPosition("horzClawRot", servoPositions.HORZCLAWROTTRANSFER.getPosition());
            hardware.setServoPosition("horzArmRotL", servoPositions.HORZARMROTLEFTTRANSFER.getPosition());
            hardware.setServoPosition("horzArmRotR", servoPositions.HORZARMROTRIGHTTRANSFER.getPosition());

// Initialize a handler to execute tasks sequentially
            Handler handler = new Handler(Looper.getMainLooper());

// Step 2: Perform vertical claw rotation and arm adjustments after 1000ms
            handler.postDelayed(() -> {
                hardware.setServoPosition("vertClawRot", servoPositions.VERTCLAWROTTRANSFER.getPosition());
                hardware.setServoPosition("vertArmRotL", servoPositions.VERTARMROTLEFTTRANSFER.getPosition());
                hardware.setServoPosition("vertArmRotR", servoPositions.VERTARMROTRIGHTTRANSFER.getPosition());
                hardware.setServoPosition("vertClawPiv", servoPositions.VERTPIVTRANSFER.getPosition());
                hardware.setServoPosition("vertClawGrip", servoPositions.VERTCLAWGRIPCLOSE.getPosition());
            }, 1500);

// Step 3: Close horizontal claw grip after another 1000ms (2000ms total from start)
            handler.postDelayed(() -> {
                hardware.setServoPosition("vertClawGrip", servoPositions.VERTCLAWGRIPOPEN.getPosition());
            }, 2500);

// Step 4: Open vertical claw grip immediately after the last action (no additional delay)
            handler.postDelayed(() -> {
                hardware.setServoPosition("horzClawGrip", servoPositions.HORZCLAWGRIPCLOSE.getPosition());
            }, 2750);
        }
        if(gamepadConfig.getGamepad2X() && count == 0) {
    hardware.setServoPosition("horzClawRot", servoPositions.HORZROTPICK.getPosition());
    hardware.setServoPosition("horzArmRotL", servoPositions.HORZARMLEFTPICK.getPosition());
    hardware.setServoPosition("horzArmRotR", servoPositions.HORZARMRIGHTPICK.getPosition());
    hardware.setServoPosition("horzClawGrip",servoPositions.HORZCLAWGRIPCLOSE.getPosition());
    count += 1;
} else if(gamepadConfig.getGamepad2X() && count == 1) {
    // Add code to move other things when the button is pressed again
            hardware.setServoPosition("horzArmRotL", -1);
            hardware.setServoPosition("horzArmRotR", 1);

            count = 0;
}
    }
}
