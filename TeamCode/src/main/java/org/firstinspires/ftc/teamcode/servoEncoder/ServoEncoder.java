package org.firstinspires.ftc.teamcode.servoEncoder;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class ServoEncoder {
    private AnalogInput diffyServoFeedback;
    private final double SERVO_CPR;
    public static double DELTA_THRESHOLD = 0.03; // IS VERY NECESSARY
    public static double ALTERNATE_DELTA_THRESHOLD = 0.4; // IS VERY NECESSARY
    public static double INIT_DELTA_THRESHOLD = 0.03;
    public static double INITIAL_VOLTAGE_THRESHOLD = 0.01;
    public static double ERROR_CORRECTION = 0.1;
    private double previousVoltage;
    private double currentVoltage;
    private double deltaVoltage;
    private double previousDeltaVoltage;
    public ElapsedTime timer;
    public ElapsedTime messageTimer;
    public boolean emergencyActivated;
    public int loopNumber = 0;

    // debug
    public double deltaDuringWrap;
    public double initialVoltage;
    public double error;


    private double currentPosition;
    private boolean voltageInitialized = false;

    public ServoEncoder(AnalogInput diffyServoFeedback, double servoCPR) {
        this.diffyServoFeedback = diffyServoFeedback;
        this.SERVO_CPR = servoCPR;

        currentVoltage = diffyServoFeedback.getVoltage();

        // initialize previous voltages
        previousVoltage = currentVoltage;
        timer = new ElapsedTime();
//        messageTimer = new ElapsedTime();

        // initialize wrapped voltages

    }

    /**
     *
     * @return if the voltages of the encoders aren't 0, in other words, if they exist
     */
    public boolean isVoltageInitialized() {

        currentVoltage = diffyServoFeedback.getVoltage();
        deltaVoltage = currentVoltage - previousVoltage;
        // if the voltage is a set distance away from 0 and the difference between current and previous voltage is small
        if ((currentVoltage < -INITIAL_VOLTAGE_THRESHOLD || currentVoltage > INITIAL_VOLTAGE_THRESHOLD)
                && deltaVoltage < INIT_DELTA_THRESHOLD) {

            // initialize voltage
            voltageInitialized = true;
            return true;

        }
        previousVoltage = currentVoltage;
        return false;
    }

    /**
     * should only be called after voltages are initialized
     */
    public void initializePosition() {
        currentPosition = diffyServoFeedback.getVoltage();
        initialVoltage = currentPosition;
        // initially, set to negative if position is close to CPR (between CPR/2 and CPR)
        if (currentPosition > SERVO_CPR / 2) {
//            currentPosition -= SERVO_CPR;

            loopNumber = -1;
            currentPosition = loopNumber * SERVO_CPR + initialVoltage;
        }
    }

//    /**
//     * the voltage from .getVoltage() is automatically wrapped between 0 and CPR
//     * this method attempts to unwrap the voltage to get the actual, real-life servo positions
//     *
//     * essentially ignores spikes in voltage, which is when the servo's position crosses 0
//     */
//    public void calculatePosition() {
//
//        // LEFT
//        currentVoltage = diffyServoFeedback.getVoltage();
//        deltaVoltage = currentVoltage - previousVoltage;
//
//        // if wraps under from 0 to CPR, or if there's a large spike in delta
//        if (deltaVoltage >= DELTA_THRESHOLD) {
//            // interpolate delta
//            if (previousDeltaVoltage <= DELTA_THRESHOLD) {
//                currentPosition += previousDeltaVoltage;
//            }
//            deltaDuringWrap = deltaVoltage;
//
//            // if wraps over from CPR to 0, or if there's a large negative spike in delta
//        } else if (deltaVoltage <= -DELTA_THRESHOLD) {
//            // interpolate delta
//            if (previousDeltaVoltage >= -DELTA_THRESHOLD) {
//                currentPosition += previousDeltaVoltage;
//            }
//            deltaDuringWrap = deltaVoltage;
//
//            // run only if there are no spikes
//        } else {
//            // update currentPosition based on speed
//            currentPosition += deltaVoltage;
//            // correct for error (doesn't work)
//            // currentVoltage - mod(currentPosition, SERVO_CPR)

    ////            error = currentVoltage - (currentPosition - SERVO_CPR * Math.floor(currentPosition / SERVO_CPR));
    ////            currentPosition += error * ERROR_CORRECTION;
//
//        }
//
//        // save previous voltage
//        previousVoltage = currentVoltage;
//        previousDeltaVoltage = deltaVoltage;
//
//    }
    public double getPosition() {
        return currentPosition;
    }

    public double getDelta() {
        return deltaVoltage;
    }

    public double getVoltage() {
        return currentVoltage;
    }

    public static double WRAP_TOLERANCE = 0.8;
    public double currentVoltageDuringWrap;
    public double previousVoltageDuringWrap;
    public double deltaVoltageDuringWrap;
    public static double delay = 0.03;

    public void calculatePositionDiscrete() {

        // LEFT
        currentVoltage = diffyServoFeedback.getVoltage();
        // DELAY HERE?
        timer.reset();
        while (timer.time() <= delay) {
            // no op
        }

        deltaVoltage = currentVoltage - previousVoltage;

        // if voltage spike detected
        // if wraps over from CPR to 0, or if there's a large negative spike in delta
        // the problem is during initialization, there is a high spike in delta from 0 to 2.9
        if (currentVoltage < SERVO_CPR / 2 - WRAP_TOLERANCE && previousVoltage > SERVO_CPR / 2 + WRAP_TOLERANCE && deltaVoltage < DELTA_THRESHOLD) {

            loopNumber++;

            // if wraps under from 0 to CPR, or if there's a large spike in delta
        } else if (currentVoltage > SERVO_CPR / 2 + WRAP_TOLERANCE && previousVoltage < SERVO_CPR / 2 - WRAP_TOLERANCE && deltaVoltage > DELTA_THRESHOLD) {
            loopNumber--;

            // alternative, emergency backup
        } /*else if (deltaVoltage > ALTERNATE_DELTA_THRESHOLD) {
            loopNumber--;
//            emergencyActivated = true;
        } else if (deltaVoltage < -ALTERNATE_DELTA_THRESHOLD) {
            loopNumber++;
//            emergencyActivated = true;
        }*/

        if (Math.abs(deltaVoltage) > DELTA_THRESHOLD) {
            currentVoltageDuringWrap = currentVoltage;
            previousVoltageDuringWrap = previousVoltage;
            deltaVoltageDuringWrap = deltaVoltage;
        }

        // update currentPosition based on current voltage and loop number
        currentPosition = loopNumber * SERVO_CPR + currentVoltage;

        // NEED A DELAY HERE

        // save previous voltage
        previousVoltage = currentVoltage;

    }
}
