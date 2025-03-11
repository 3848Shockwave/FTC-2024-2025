package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Config
public class ArmSubsystem extends SubsystemBase {

    //    public static double DIFFY_SERVO_MAX_DEGREE = 315;
    // TODO: find out what this is
    public static double SERVO_CPR = 3.217;
    public static double kP = 0, kI = 0, kD = 0, kF = 0;
    public static double WRAP_TOLERANCE = 10; // might not be necessary
    public static double POSITION_TOLERANCE = 0.05;

    private Telemetry telemetry;
    private CRServo diffyServoL, diffyServoR;
    private AnalogInput diffyServoLFeedback, diffyServoRFeedback;
    private PIDFController pidL, pidR;
    private double previousVoltageL, previousVoltageR;
    private double currentVoltageL, currentVoltageR;
    // position in counts
    private double currentPositionL, currentPositionR;
    private ServoEx clawRollServo;
    private ServoEx clawGripServo;
    // offset from the two servos from being centered
    // TODO: initialize?
    private double wristPitch;
    private double clawPitch;
    // debug
    private boolean pitchBoundsExceeded;


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

    public ArmSubsystem(HardwareMap hardwareMap, Telemetry telemetry, Type type) {
        this.telemetry = telemetry;

//        diffyServoL = new SimpleServo(hardwareMap, type.getName() + "ArmRotL", 0, DIFFY_SERVO_MAX_DEGREE);
        diffyServoL = new CRServo(hardwareMap, type.getName() + "ArmRotL");
        diffyServoR = new CRServo(hardwareMap, type.getName() + "ArmRotR");
        diffyServoR.setInverted(true);

        AnalogInput diffyServoLFeedback = hardwareMap.get(AnalogInput.class, type.getName() + "FeedbackL");
        AnalogInput diffyServoRFeedback = hardwareMap.get(AnalogInput.class, type.getName() + "FeedbackR");

        pidL = new PIDFController(kP, kI, kD, kF);
        pidR = new PIDFController(kP, kI, kD, kF);
        pidL.setTolerance(POSITION_TOLERANCE);
        pidR.setTolerance(POSITION_TOLERANCE);

        clawRollServo = new SimpleServo(hardwareMap, type.getName() + "ClawPiv", 0, 180);

        clawGripServo = new SimpleServo(hardwareMap, type.getName() + "ClawGrip", 0, 180);

        // initialize voltages
        currentVoltageL = diffyServoLFeedback.getVoltage();
        currentVoltageR = diffyServoRFeedback.getVoltage();
        previousVoltageL = currentVoltageL;
        previousVoltageR = currentVoltageR;

    }

    @Override
    public void periodic() {
        unwrapVoltage();
        setServos();

    }

    private void setServos() {
        // left
        if (!pidL.atSetPoint()) {
            double output = pidL.calculate(currentPositionL);
            diffyServoL.set(output);
        } else {
            diffyServoL.stop();
        }

        // right
        if (!pidR.atSetPoint()) {
            double output = pidR.calculate(currentPositionR);
            diffyServoR.set(output);
        } else {
            diffyServoR.stop();
        }
    }

    /**
     * the voltage from .getVoltage() is automatically wrapped between 0 and CPR
     * this method attempts to unwrap the voltage to get the actual angle
     */
    private void unwrapVoltage() {

        // LEFT
        currentVoltageL = diffyServoLFeedback.getVoltage();
        double deltaL = currentVoltageL - previousVoltageL;

        // if wraps under from 0 to CPR
        if (deltaL >= SERVO_CPR - WRAP_TOLERANCE) {
            // instead goes below 0 (deltaL is slightly more negative than +CPR)
            currentPositionL -= SERVO_CPR - deltaL;

            // if wraps over from CPR to 0
        } else if (deltaL <= SERVO_CPR - WRAP_TOLERANCE) {
            // instead goes past CPR (deltaL is slightly more positive than -CPR)
            currentPositionL += SERVO_CPR + deltaL;

        } else {
            currentPositionL += deltaL;
        }

        previousVoltageL = currentVoltageL;

        // RIGHT
        currentVoltageR = diffyServoRFeedback.getVoltage();
        double deltaR = currentVoltageR - previousVoltageR;

        // if wraps under from 0 to CPR
        if (deltaR >= SERVO_CPR - WRAP_TOLERANCE) {
            // instead goes below 0 (deltaR is slightly more negative than +CPR)
            currentPositionR -= SERVO_CPR - deltaR;

            // if wraps over from CPR to 0
        } else if (deltaR <= SERVO_CPR - WRAP_TOLERANCE) {
            // instead goes past CPR (deltaR is slightly more positive than -CPR)
            currentPositionR += SERVO_CPR + deltaR;

        } else {
            currentPositionR += deltaR;
        }

        previousVoltageR = currentVoltageR;

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

        pidL.setSetPoint((wristPitch + clawPitch) / 2);
        pidR.setSetPoint((wristPitch - clawPitch) / 2);
    }

    public void setWristPitch(double wristPitch) {
        setWristClawPitch(wristPitch, this.clawPitch);
    }
    public void setClawPitch(double clawPitch) {
        setWristClawPitch(this.wristPitch, clawPitch);
    }

    public void setClawGrip(double clawGripPosition) {
        clawGripServo.turnToAngle(clawGripPosition, AngleUnit.DEGREES);
    }

    public void setClawRoll(double clawRollPosition) {
        clawRollServo.turnToAngle(clawRollPosition, AngleUnit.DEGREES);
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
