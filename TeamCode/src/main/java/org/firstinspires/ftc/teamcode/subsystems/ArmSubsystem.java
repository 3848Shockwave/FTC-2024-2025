package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Config
public class ArmSubsystem extends SubsystemBase {

    private Telemetry telemetry;
    private ServoEx diffyServoL, diffyServoR;
    private ServoEx wristPitchServoL, wristPitchServoR;
    private ServoEx clawGripServo;

    // offset from the two servos from being centered
    private double currentClawCenterOffset;
    private double currentWristAngle;
    public static double DIFFY_SERVO_MAX_DEGREE = 315;

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

        diffyServoL = new SimpleServo(hardwareMap, type.getName() + "DiffyL", 0, DIFFY_SERVO_MAX_DEGREE);
        diffyServoR = new SimpleServo(hardwareMap, type.getName() + "DiffyR", 0, DIFFY_SERVO_MAX_DEGREE);

        wristPitchServoL = new SimpleServo(hardwareMap, type.getName() + "ArmRotL", 0, 180);
        wristPitchServoR = new SimpleServo(hardwareMap, type.getName() + "ArmRotR", 0, 180);

        clawGripServo = new SimpleServo(hardwareMap, type.getName() + "ClawGrip", 0, 180);

    }

    @Override
    public void periodic() {

    }

    private void initServoPositions() {
        // center servos
        // effectively zeroes claw rotation and wrist angle
        diffyServoR.turnToAngle(0);
        diffyServoL.turnToAngle(DIFFY_SERVO_MAX_DEGREE);
        currentClawCenterOffset = 0;
        currentWristAngle = 0;
    }

    public void turnClawPitchToAngle(double angle) {
        this.currentWristAngle = angle;
        // both servos rotate opposite directions (ex. CW and CCW)
        // TODO: switch if necessary
        // TODO: add check for out of bounds

//        // the offset between the two servos, or in simple terms, how much the claw is rotated
//        double servosCenterOffset = (leftServo.getAngle() - (MAX_DEGREE - rightServo.getAngle())) / 2;

        diffyServoL.turnToAngle(currentClawCenterOffset + angle);
        diffyServoR.turnToAngle((DIFFY_SERVO_MAX_DEGREE - currentClawCenterOffset) - angle);

    }

    public void turnClawRollToAngle(double angle) {
        this.currentClawCenterOffset = angle;
        // both servos rotate same direction (ex. CCW, CCW)
        // TODO: switch if necessary
        // TODO: add check for out of bounds
        diffyServoL.turnToAngle(currentWristAngle + angle);
        diffyServoR.turnToAngle((DIFFY_SERVO_MAX_DEGREE - currentWristAngle) + angle);

    }

    public void setClawGripPosition(double clawGripPosition) {
        clawGripServo.turnToAngle(clawGripPosition, AngleUnit.DEGREES);
    }

    public void setWristPitchPosition(double degrees) {
        wristPitchServoL.turnToAngle(180 - degrees);
        wristPitchServoR.turnToAngle(degrees);
    }



}
