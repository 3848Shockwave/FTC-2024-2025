//package org.firstinspires.ftc.teamcode;
//
//
//import com.qualcomm.robotcore.hardware.HardwareMap;
//import com.qualcomm.robotcore.hardware.PwmControl;
//import com.qualcomm.robotcore.hardware.ServoImplEx;
//
//import static org.firstinspires.ftc.teamcode.MainDrive.*;
//
//public class Claw {
//
//    private ServoImplEx servo;
//
//    private double servoClosedTime;
//    private double servoSlightOpenTime;
//
//    final double SERVO_OPENED_POSITION = 0;
//
//    final double SERVO_CLOSED_POSITION = 1;
//
//    final double SERVO_CLOSED_DURATION = 1;
//
//    final double SERVO_SLIGHTLY_OPENED_POSITION = 0.5;
//
//    final double SERVO_SLIGHTLY_OPENED_DURATION = 0.3;
//
//    private boolean isPickingUp = false;
//    private Gamepads gamepads;
//
//    public Claw(HardwareMap hardwareMap, Gamepads gamepads) {
//        this.gamepads = gamepads;
//        servo = hardwareMap.get(ServoImplEx.class, "claw");
//
//        // pulse width modulation??
//        servo.setPwmEnable();
//        servo.setPwmRange(new PwmControl.PwmRange(1400, 1900));
//    }
//
//    public void update() {
//
//
//        // open on rising edge
//        if (gamepads.currentGamepad1.b) {
//            resetServoToOpen();
//        }
//
//        // servo close and start the timer on falling edge
//        if (gamepads.currentGamepad1.a && !gamepads.previousGamepad1.a && !isPickingUp) {
//            startPickingUp();
//        }
//
//        if (isPickingUp) {
//            handlePickingUp();
//        }
//    }
//
//    private void resetServoToOpen() {
//        servo.setPosition(SERVO_OPENED_POSITION);
//        servoClosedTime = 0;
//        isPickingUp = false;
//    }
//
//    private void startPickingUp() {
//        // close servo
//        servo.setPosition(SERVO_CLOSED_POSITION);
//        // start the timer to slightly open
//        servoClosedTime = runtime.time();
//        // reset this back to default
//        servoSlightOpenTime = 0;
//        isPickingUp = true;
//    }
//
//
//    private void handlePickingUp() {
//        // if the claw is just closed and a short amount of time has passed,
//        // slightly open the claw and wait a bit
//        if (servoClosedTime > 0 && runtime.time() - servoClosedTime >= SERVO_CLOSED_DURATION) {
//            servo.setPosition(SERVO_SLIGHTLY_OPENED_POSITION);
//            // start slight open timer
//            servoSlightOpenTime = runtime.time();
//            // reset this back to default
//            servoClosedTime = 0;
//
//        }
//
//        // after the short amount of time of the claw slightly opened,
//        // close the claw again
//        if (servoSlightOpenTime > 0 && runtime.time() - servoSlightOpenTime >= SERVO_SLIGHTLY_OPENED_DURATION) {
//            // close the servo
//            servo.setPosition(SERVO_CLOSED_POSITION);
//            // reset this back to default;
//            servoSlightOpenTime = 0;
//            // set no longer picking up
//            isPickingUp = false;
//        }
//
//    }
//
//}
