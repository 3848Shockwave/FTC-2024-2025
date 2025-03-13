package org.firstinspires.ftc.teamcode.servoEncoder;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogInput;

@Config
public class ServoEncoder {
    private AnalogInput diffyServoFeedback;
    private final double SERVO_CPR;
    public static double DELTA_THRESHOLD = 0.03; // IS VERY NECESSARY
    public static double INITIAL_VOLTAGE_THRESHOLD = 0.01;
    public static double ERROR_CORRECTION = 0.1;
    private double previousVoltage;
    private double currentVoltage;
    private double deltaVoltage;
    private double previousDeltaVoltage;

    // debug
    public double debugDelta;
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

        // initialize wrapped voltages

    }

    /**
     *
     * @return if the voltages of the encoders aren't 0, in other words, if they exist
     */
    public boolean isVoltageInitialized() {
        double initialVoltage = diffyServoFeedback.getVoltage();
        return voltageInitialized = initialVoltage < -INITIAL_VOLTAGE_THRESHOLD
                || initialVoltage > INITIAL_VOLTAGE_THRESHOLD;
    }

    /**
     * should only be called after voltages are initialized
     */
    public void initializePosition() {
        currentPosition = diffyServoFeedback.getVoltage();
        initialVoltage = currentPosition;
        // initially, set to negative if position is close to CPR (between CPR/2 and CPR)
        if (currentPosition > SERVO_CPR / 2) currentPosition -= SERVO_CPR;
    }

    /**
     * the voltage from .getVoltage() is automatically wrapped between 0 and CPR
     * this method attempts to unwrap the voltage to get the actual, real-life servo positions
     *
     * essentially ignores spikes in voltage, which is when the servo's position crosses 0
     */
    public void calculatePosition() {

        // LEFT
        currentVoltage = diffyServoFeedback.getVoltage();
        deltaVoltage = currentVoltage - previousVoltage;

        // if wraps under from 0 to CPR, or if there's a large spike in delta
        if (deltaVoltage >= DELTA_THRESHOLD) {
            // interpolate delta
            if (previousDeltaVoltage <= DELTA_THRESHOLD) {
                currentPosition += previousDeltaVoltage;
            }
            debugDelta = deltaVoltage;

            // if wraps over from CPR to 0, or if there's a large negative spike in delta
        } else if (deltaVoltage <= -DELTA_THRESHOLD) {
            // interpolate delta
            if (previousDeltaVoltage >= -DELTA_THRESHOLD) {
                currentPosition += previousDeltaVoltage;
            }
            debugDelta = deltaVoltage;

            // run only if there are no spikes
        } else {
            // update currentPosition based on speed
            currentPosition += deltaVoltage;
            // correct for error (doesn't work)
            // currentVoltage - mod(currentPosition, SERVO_CPR)
//            error = currentVoltage - (currentPosition - SERVO_CPR * Math.floor(currentPosition / SERVO_CPR));
//            currentPosition += error * ERROR_CORRECTION;

        }

        // save previous voltage
        previousVoltage = currentVoltage;
        previousDeltaVoltage = deltaVoltage;

    }

    public double getPosition() {
        return currentPosition;
    }

    public double getDelta() {
        return deltaVoltage;
    }

    public double getVoltage() {
        return currentVoltage;
    }
}
