package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.servoEncoder.ServoEncoder;

@Config
public class ArmSubsystem extends SubsystemBase {

    //    public static double DIFFY_SERVO_MAX_DEGREE = 315;
    // TODO: find out what this is
    public static double SERVO_CPR = 3.274;
    public static double kP = 0.8, kI = 0, kD = 0, kF = 0;
    public static double WRAP_TOLERANCE = 0.3; // IS VERY NECESSARY
    public static double POSITION_TOLERANCE = 0.03;

    private Telemetry telemetry;
    private CRServo diffyServoL, diffyServoR;
    private AnalogInput diffyServoLFeedback, diffyServoRFeedback;
    private ServoEncoder diffyServoLEncoder, diffyServoREncoder;
    private PIDFController pidL, pidR;
    private boolean diffyServosInitialized = false;
    private ServoEx clawRollServo;
    private ServoEx clawGripServo;
    // offset from the two servos from being centered
    // TODO: initialize?
    private double wristPitch;
    private double clawPitch;


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
        // TODO: since the servo is reversed, the PID controller or encoder wire feedback or whatever might also have to be reversed, we'll see

        diffyServoLFeedback = hardwareMap.get(AnalogInput.class, type.getName() + "FeedbackL");
        diffyServoRFeedback = hardwareMap.get(AnalogInput.class, type.getName() + "FeedbackR");

        diffyServoLEncoder = new ServoEncoder(diffyServoLFeedback, SERVO_CPR);
        diffyServoREncoder = new ServoEncoder(diffyServoRFeedback, SERVO_CPR);

        pidL = new PIDFController(kP, kI, kD, kF);
        pidR = new PIDFController(kP, kI, kD, kF);
        pidL.setTolerance(POSITION_TOLERANCE);
        pidR.setTolerance(POSITION_TOLERANCE);

//        clawRollServo = new SimpleServo(hardwareMap, type.getName() + "ClawPiv", 0, 180);
//
//        clawGripServo = new SimpleServo(hardwareMap, type.getName() + "ClawGrip", 0, 180);

    }

    @Override
    public void periodic() {
        if (!diffyServosInitialized && diffyServoLEncoder.isVoltageInitialized() && diffyServoREncoder.isVoltageInitialized()) {
            diffyServosInitialized = true;
        }

        if (diffyServosInitialized) {
            diffyServoLEncoder.calculatePosition();
            diffyServoREncoder.calculatePosition();
            double servoPositionL = diffyServoLEncoder.getPosition();
            double servoPositionR = diffyServoREncoder.getPosition();
            setServos(servoPositionL, servoPositionR);
        }

        telemetry.addData("current delta L", diffyServoLEncoder.getDelta());
        telemetry.addData("current delta R", diffyServoREncoder.getDelta());
//        telemetry.addData("debug delta L", debugDeltaL);
//        telemetry.addData("debug delta R", debugDeltaR);
//        telemetry.addData("pid L error", pidL.getPositionError());
//        telemetry.addData("pid R error", pidL.getPositionError());
        telemetry.addData("voltage L", diffyServoLEncoder.getVoltage());
        telemetry.addData("voltage R", diffyServoREncoder.getVoltage());
        telemetry.addData("position L", diffyServoLEncoder.getPosition());
        telemetry.addData("position R", diffyServoREncoder.getPosition());

        telemetry.update();

    }

    /**
     * sets the servos to the output of the PID controllers
     * basically speeds up the servos to reach the desired position
     */
    private void setServos(double leftPosition, double rightPosition) {
        // left
        double leftOutput = pidL.calculate(leftPosition);
        diffyServoL.set(leftOutput);

        // right
        double rightOutput = pidR.calculate(rightPosition);
        diffyServoR.set(rightOutput);

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
        pidR.setSetPoint(-((wristPitch - clawPitch) / 2));
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
