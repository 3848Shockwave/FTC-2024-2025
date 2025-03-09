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
    // TODO: initialize?
    private double clawPitch;
    private double clawRoll;

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
        wristPitchServoL.setInverted(true);
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
//        diffyServoR.turnToAngle(0);
//        diffyServoL.turnToAngle(0);
//        clawPitch = 0;
//        clawRoll = 0;
    }

    public void setClawPitchRoll(double pitch, double roll) {
        this.clawPitch = pitch;
        this.clawRoll = roll;
        diffyServoL.turnToAngle((pitch + roll) / 2);
        diffyServoR.turnToAngle((pitch - roll) / 2);
    }

    public void setClawPitch(double pitch) {
        setClawPitchRoll(pitch, this.clawRoll);
    }

    public void setClawRoll(double roll) {
        setClawPitchRoll(this.clawPitch, roll);
    }

    public void setClawGrip(double clawGripPosition) {
        clawGripServo.turnToAngle(clawGripPosition, AngleUnit.DEGREES);
    }

    public void setWristPitch(double degrees) {
        wristPitchServoL.turnToAngle(180 - degrees);
        wristPitchServoR.turnToAngle(degrees);
    }

    public double getClawPitch() {
        return clawPitch;
    }

    public double getClawRoll() {
        return clawRoll;
    }

    public double getWristPitch() {
        return wristPitchServoR.getAngle();
    }

}
