package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;

@Config
public class IntakeSubsystem extends SubsystemBase {
    // first let's do horizontal
    private Telemetry telemetry;
    public IntakeState currentIntakeState = IntakeState.INTAKE;


    // horizontal components
    public ServoEx horizontalSlideServoL, horizontalSlideServoR;
    public ServoEx horizontalClawGripServo;
    public ServoEx horizontalClawRollServo;
    public ServoEx horizontalClawPitchServo;
    public ServoEx horizontalWristPitchServoL, horizontalWristPitchServoR;

    // vertical components
    public Motor verticalSlideMotorTop, verticalSlideMotorBottom;
    public ServoEx verticalClawGripServo;
    public ServoEx verticalClawRollServo;
    public ServoEx verticalClawPitchServo;
    public ServoEx verticalWristPitchServoL, verticalWristPitchServoR;


    public enum IntakeState {
        INTAKE,
        TRANSFER,
        DEPOSIT

    }

    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
//        horizontalClawGripServo = robot.horizontalClawGripServo;
        this.telemetry = telemetry;
        // horizontal
        // arm
        horizontalClawGripServo = new SimpleServo(hardwareMap, "horzClawGrip", 0, 180);
        horizontalClawRollServo = new SimpleServo(hardwareMap, "horzClawPiv", 0, 180);
        horizontalClawPitchServo = new SimpleServo(hardwareMap, "horzClawRot", 0, 180);
        horizontalWristPitchServoL = new SimpleServo(hardwareMap, "horzArmRotL", 0, 180);
        horizontalWristPitchServoR = new SimpleServo(hardwareMap, "horzArmRotR", 0, 180);

        // slides
        horizontalSlideServoL = new SimpleServo(hardwareMap, "horzExtL", 0, 180);
        horizontalSlideServoR = new SimpleServo(hardwareMap, "horzExtL", 0, 180);

        // vertical
        // arm
        verticalClawGripServo = new SimpleServo(hardwareMap, "vertClawGrip", 0, 180);
        verticalClawRollServo = new SimpleServo(hardwareMap, "vertClawPiv", 0, 180);
        verticalClawPitchServo = new SimpleServo(hardwareMap, "vertClawRot", 0, 180);
        verticalWristPitchServoL = new SimpleServo(hardwareMap, "vertArmRotL", 0, 180);
        verticalWristPitchServoR = new SimpleServo(hardwareMap, "vertArmRotR", 0, 180);

        // vertical slide motors
        verticalSlideMotorBottom = new Motor(hardwareMap, "spoolRight");
        verticalSlideMotorBottom.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorBottom.setPositionCoefficient(0.1);
        verticalSlideMotorBottom.setPositionTolerance(10);
        verticalSlideMotorBottom.setTargetPosition(5000);
        verticalSlideMotorBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        verticalSlideMotorTop = new Motor(hardwareMap, "spoolLeft");
        verticalSlideMotorTop.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorTop.setPositionCoefficient(0.1);
        verticalSlideMotorTop.setPositionTolerance(10);
        verticalSlideMotorTop.setTargetPosition(5000);
        verticalSlideMotorTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    // constantly updating
    @Override
    public void periodic() {
        telemetry.addData("claw grippy (periodic)", horizontalClawGripServo.getPosition());
        telemetry.update();
    }

    public void setCurrentState(IntakeState intakeState) {
        currentIntakeState = intakeState;
    }


    // add a bajillion more methods here for the commands class
    public void closeClawManual(IntakeState intakeState) {
        if (intakeState == IntakeState.INTAKE) {
            closeHorizontalClaw();
        } else if (intakeState == IntakeState.DEPOSIT) {
            closeVerticalClaw();
        }
    }

    public void openClawManual(IntakeState intakeState) {
        if (intakeState == IntakeState.INTAKE) {
            openHorizontalClaw();
        } else if (intakeState == IntakeState.DEPOSIT) {
            openVerticalClaw();
        }
    }

    public void openVerticalClaw() {
        verticalClawGripServo.setPosition(Constants.CLAW_GRIP_OPEN_POSITION);
    }

    public void closeVerticalClaw() {
        verticalClawGripServo.setPosition(Constants.CLAW_GRIP_CLOSED_POSITION);
    }

    public void openHorizontalClaw() {
        horizontalClawGripServo.setPosition(Constants.CLAW_GRIP_OPEN_POSITION);
    }

    public void closeHorizontalClaw() {
        horizontalClawGripServo.setPosition(Constants.CLAW_GRIP_CLOSED_POSITION);
    }

    public void setVerticalArmPosition(IntakeState intakeState) {
        switch (intakeState) {
            case TRANSFER:
                // claw should already be closed
//                                    intakeSubsystem.closeClaw();
                setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION);
                // bring back slides
                setVerticalSlidePosition(Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION);
                setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION);
                setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_TRANSFER_POSITION);
                break;
            case DEPOSIT:
                setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_DEPOSIT_POSITION);
                setVerticalSlidePosition(Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION);
                setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_DEPOSIT_POSITION);
                setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_DEPOSIT_POSITION);
                break;
        }

    }

    public void setHorizontalArmPosition(IntakeState intakeState) {
        switch (intakeState) {
            case INTAKE:
                // claw should already be closed
//                                    intakeSubsystem.closeClaw();
                setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION);
                // bring back slides
                setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_INTAKE_POSITION);
                setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION);
                setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_INTAKE_POSITION);
                break;
            case TRANSFER:
                setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION);
                setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_TRANSFER_POSITION);
                setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION);
                setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION);
                break;
        }

    }

    public void setHorizontalWristPitchPosition(double position) {
        horizontalWristPitchServoL.setPosition(-position);
        horizontalWristPitchServoR.setPosition(position);
    }

    public void setHorizontalSlidePosition(double position) {
        horizontalSlideServoL.setPosition(-position);
        horizontalSlideServoR.setPosition(position);
    }

    public void setHorizontalClawRollPosition(double position) {
        horizontalClawRollServo.setPosition(position);
    }

    public void setHorizontalClawPitchPosition(double position) {
        horizontalClawPitchServo.setPosition(position);
    }

    public void setVerticalWristPitchPosition(double position) {
        verticalWristPitchServoL.setPosition(-position);
        verticalWristPitchServoR.setPosition(position);
    }

    public void setVerticalSlidePosition(int position) {
        verticalSlideMotorBottom.setTargetPosition(-position);
        verticalSlideMotorTop.setTargetPosition(position);
    }

    public void setVerticalClawRollPosition(double position) {
        verticalClawRollServo.setPosition(position);
    }

    public void setVerticalClawPitchPosition(double position) {
        verticalClawPitchServo.setPosition(position);
    }
}


