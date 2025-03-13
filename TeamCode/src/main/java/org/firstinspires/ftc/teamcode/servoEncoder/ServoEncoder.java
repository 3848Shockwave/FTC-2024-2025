package org.firstinspires.ftc.teamcode.servoEncoder;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.AnalogInput;

@Config
public class ServoEncoder {
    private AnalogInput diffyServoFeedback;
    private final double SERVO_CPR;
    public static double WRAP_TOLERANCE = 0.3; // IS VERY NECESSARY
    private double previousVoltage;
    private double currentVoltage;
    private double currentDelta;
    private double previousDelta;
    private double debugDelta;


    private double currentPosition;
    private boolean voltageInitialized = false;

    public ServoEncoder(AnalogInput diffyServoFeedback, double servoCPR) {
        this.diffyServoFeedback = diffyServoFeedback;
        this.SERVO_CPR = servoCPR;

        currentVoltage = diffyServoFeedback.getVoltage();

        // initialize previous voltages
        previousVoltage = currentVoltage;

        // initialize wrapped voltages
        currentPosition = currentVoltage;

    }

    public boolean isVoltageInitialized() {
         return voltageInitialized = diffyServoFeedback.getVoltage() != 0;
    }

    /**
     * the voltage from .getVoltage() is automatically wrapped between 0 and CPR
     * this method attempts to unwrap the voltage to get the actual, real-life servo positions
     *
     * essentially ignores spikes in voltage, which is when the servo's position crosses 0
     */
    public double calculatePosition() {

        // LEFT
        currentVoltage = diffyServoFeedback.getVoltage();
        currentDelta = currentVoltage - previousVoltage;

        // if wraps under from 0 to CPR, or if there's a large spike in delta
        if (currentDelta >= WRAP_TOLERANCE) {
            // DO NOTHING
            debugDelta = currentDelta;

            // if wraps over from CPR to 0, or if there's a large negative spike in delta
        } else if (currentDelta <= -WRAP_TOLERANCE) {
            // DO NOTHING
            debugDelta = currentDelta;

            // run only if there are no spikes
        } else {
            // update currentPosition based on speed
            currentPosition -= currentDelta;

            previousDelta = currentDelta;

        }

        // save previous voltage
        previousVoltage = currentVoltage;

        return currentPosition;
    }

    public double getPosition() {
        return currentPosition;
    }

    public double getDelta() {
        return currentDelta;
    }

    public double getVoltage() {
        return currentVoltage;
    }
}
