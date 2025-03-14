package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static org.firstinspires.ftc.teamcode.constants.ServoMaxes.AGFRC_SERVO_MAX_DEGREE;
import static org.firstinspires.ftc.teamcode.constants.ServoMaxes.AXON_SERVO_MAX_DEGREE;


@Config
public class ArmSubsystem extends SubsystemBase {

    private Telemetry telemetry;
    private ServoEx wristPitchServoL, wristPitchServoR;
    private ServoEx clawPitchServo;
    private ServoEx clawRollServo;
    private ServoEx clawGripServo;


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

        wristPitchServoL = new SimpleServo(hardwareMap, type.getName() + "ArmRotL", 0, AXON_SERVO_MAX_DEGREE);
        wristPitchServoR = new SimpleServo(hardwareMap, type.getName() + "ArmRotR", 0, AXON_SERVO_MAX_DEGREE);
        wristPitchServoL.setInverted(true);

        clawPitchServo = new SimpleServo(hardwareMap, type.getName() + "ClawRot", 0, AXON_SERVO_MAX_DEGREE);
        clawGripServo = new SimpleServo(hardwareMap, type.getName() + "ClawGrip", 0, AGFRC_SERVO_MAX_DEGREE);
        clawRollServo = new SimpleServo(hardwareMap, type.getName() + "ClawPiv", 0, AGFRC_SERVO_MAX_DEGREE);

    }

    @Override
    public void periodic() {
        telemetry.addData("wrist Pitch L", wristPitchServoL.getAngle());
        telemetry.addData("claw pitch", clawPitchServo.getAngle());
        telemetry.addData("claw roll", clawRollServo.getAngle());
        telemetry.addData("claw grip", clawGripServo.getAngle());
    }


    public void setClawPitch(double degrees) {
        clawPitchServo.turnToAngle(degrees);
    }

    public void setClawRoll(double degrees) {
        clawRollServo.turnToAngle(degrees);
    }

    public void setClawGrip(double clawGripPosition) {
        clawGripServo.turnToAngle(clawGripPosition);
    }

    public void setWristPitch(double degrees) {
        wristPitchServoL.turnToAngle(180 - degrees);
        wristPitchServoR.turnToAngle(degrees);
    }

}
