package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ArmSubsystemTest extends SubsystemBase {

    //    public static double DIFFY_SERVO_MAX_DEGREE = 315;
    // TODO: find out what this is
    public static double SERVO_CPR = 3.274;
    public static double kP = 0.4, kI = 0, kD = 0, kF = 0;
    public static double WRAP_TOLERANCE = 1;
    public static double POSITION_TOLERANCE = 0.01;

    private Telemetry telemetry;
    private CRServo diffyServoL;
    private AnalogInput diffyServoLFeedback;
    private PIDFController pidL;
    private double previousVoltageL;
    private double currentVoltageL;
    // WRAPPED voltage reading from the feedback wire in counts
    private double currentPositionL;
    // offset from the two servos from being centered
    // TODO: initialize?
    private double wristPitch;
    private double clawPitch;
    // debug


    public enum Type {
        HORIZONTAL("horz"),
        VERTICAL("vert");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

    public ArmSubsystemTest(HardwareMap hardwareMap, Telemetry telemetry, Type type) {
        this.telemetry = telemetry;

        diffyServoL = new CRServo(hardwareMap, "crservo");
//        diffyServoL = new CRServo(hardwareMap, type.getName() + "ArmRotL");
//        diffyServoR = new CRServo(hardwareMap, type.getName() + "ArmRotR");
//        // TODO: since the servo is reversed, the PID controller or encoder wire feedback or whatever might also have to be reversed, we'll see
//        diffyServoR.setInverted(true);

//        AnalogInput diffyServoLFeedback = hardwareMap.get(AnalogInput.class, type.getName() + "FeedbackL");
        diffyServoLFeedback = hardwareMap.get(AnalogInput.class, "input");
//        AnalogInput diffyServoRFeedback = hardwareMap.get(AnalogInput.class, type.getName() + "FeedbackR");

        pidL = new PIDFController(kP, kI, kD, kF);
//        pidR = new PIDFController(kP, kI, kD, kF);
        pidL.setTolerance(POSITION_TOLERANCE);
//        pidR.setTolerance(POSITION_TOLERANCE);

//        clawRollServo = new SimpleServo(hardwareMap, type.getName() + "ClawPiv", 0, 180);
//        clawGripServo = new SimpleServo(hardwareMap, type.getName() + "ClawGrip", 0, 180);

        // initialize voltages
        // get current voltages
        currentVoltageL = diffyServoLFeedback.getVoltage();
//        currentVoltageR = diffyServoRFeedback.getVoltage();

        // initialize previous voltages
        previousVoltageL = currentVoltageL;
//        previousVoltageR = currentVoltageR;

        // initialize wrapped voltages
        currentPositionL = currentVoltageL;
//        currentPositionR = currentVoltageR;

    }

    @Override
    public void periodic() {
        unwrapVoltage();
        setServos();
        telemetry.addData("servo voltage", diffyServoLFeedback.getVoltage());
        telemetry.addData("servo position", currentPositionL);
        telemetry.addData("last delta L", lastDeltaL);
        telemetry.update();

    }

    /**
     * sets the servos to the output of the PID controllers
     * basically speeds up the servos to reach the desired position
     */
    private void setServos() {
        // left
        double output = pidL.calculate(currentPositionL);
        if (output < -1) output = -1;
        if (output > 1) output = 1;
        telemetry.addData("output", output);
        diffyServoL.set(output);
        telemetry.addData("error", pidL.getPositionError());

//        // right
//        if (!pidR.atSetPoint()) {
//            double output = pidR.calculate(currentPositionR);
//            diffyServoR.set(output);
//        } else {
//            diffyServoR.stop();
//        }
    }

    double lastDeltaL = 0;
    /**
     * the voltage from .getVoltage() is automatically wrapped between 0 and CPR
     * this method attempts to unwrap the voltage to get the actual, real-life servo positions
     */
    private void unwrapVoltage() {

        // LEFT
        currentVoltageL = diffyServoLFeedback.getVoltage();
        double deltaL = currentVoltageL - previousVoltageL;

        // if wraps under from 0 to CPR, or if there's a large spike in deltaL
        if (deltaL >= SERVO_CPR - WRAP_TOLERANCE) {
            // instead goes below 0 (deltaL is slightly more negative than +CPR)
            currentPositionL += SERVO_CPR - deltaL;
            lastDeltaL = deltaL;

            // if wraps over from CPR to 0, or if there's a large negative spike in deltaL
        } else if (deltaL <= -(SERVO_CPR - WRAP_TOLERANCE)) {
            // instead goes past CPR (deltaL is slightly more positive than -CPR)
            currentPositionL -= SERVO_CPR + deltaL;
            lastDeltaL = deltaL;

        } else {
            currentPositionL -= deltaL;
        }

        previousVoltageL = currentVoltageL;

//        // RIGHT
//        currentVoltageR = diffyServoRFeedback.getVoltage();
//        double deltaR = currentVoltageR - previousVoltageR;
//
//        // if wraps under from 0 to CPR
//        if (deltaR >= SERVO_CPR - WRAP_TOLERANCE) {
//            // instead goes below 0 (deltaR is slightly more negative than +CPR)
//            currentPositionR -= SERVO_CPR - deltaR;
//
//            // if wraps over from CPR to 0
//        } else if (deltaR <= -(SERVO_CPR - WRAP_TOLERANCE)) {
//            // instead goes past CPR (deltaR is slightly more positive than -CPR)
//            currentPositionR += SERVO_CPR + deltaR;
//
//        } else {
//            currentPositionR += deltaR;
//        }
//
//        previousVoltageR = currentVoltageR;

    }

    private void initServoPositions() {
    }

    /**
     * takes in pitches and sets the setpoints for the PID controllers
     * @param wristPitch desired wrist pitch in servo degrees
     * @param clawPitch desired claw pitch in servo degrees
     */
    public void setWristClawPitch(double wristPitch, double clawPitch) {
        this.wristPitch = wristPitch;
        this.clawPitch = clawPitch;

        // convert to counts
        wristPitch *= (SERVO_CPR / 360);
        clawPitch *= (SERVO_CPR / 360);

        // set the pid's setpoint to the desired servo positions, effectively telling the servos to move to position
        pidL.setSetPoint((wristPitch + clawPitch) / 2);
        // TODO: figure out if this should really be negative or not (since the right servo is reversed)
    }

    public void setWristPitch(double wristPitch) {
        setWristClawPitch(wristPitch, this.clawPitch);
    }

    public void setClawPitch(double clawPitch) {
        setWristClawPitch(this.wristPitch, clawPitch);
    }

    /**
     * @return servo claw pitch in degrees
     */
    public double getClawPitch() {
        return this.clawPitch * (360 / SERVO_CPR);
    }

    /**
     * @return servo wrist pitch in degrees
     */
    public double getWristPitch() {
        return this.wristPitch * (360 / SERVO_CPR);
    }


}
