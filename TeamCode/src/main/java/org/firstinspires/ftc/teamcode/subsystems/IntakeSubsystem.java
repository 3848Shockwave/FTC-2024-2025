package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Robot;

@Config
public class IntakeSubsystem extends SubsystemBase {
    // first let's do horizontal
    private Telemetry telemetry;
    public IntakeState currentIntakeState;

    private Robot robot;

    public enum ArmPosition {

    }

    public enum IntakeState {
        INTAKE,
        TRANSFER,
        DEPOSIT

    }

    public IntakeSubsystem(Robot robot, Telemetry telemetry) {
//        horizontalClawGripServo = robot.horizontalClawGripServo;
        this.robot = robot;
        this.telemetry = telemetry;
    }

    // constantly updating
    @Override
    public void periodic() {
        telemetry.addData("claw grippy (periodic)", robot.horizontalClawGripServo.getPosition());
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
        robot.verticalClawGripServo.setPosition(Constants.CLAW_GRIP_OPEN_POSITION);
    }

    public void closeVerticalClaw() {
        robot.verticalClawGripServo.setPosition(Constants.CLAW_GRIP_CLOSED_POSITION);
    }

    public void openHorizontalClaw() {
        robot.horizontalClawGripServo.setPosition(Constants.CLAW_GRIP_OPEN_POSITION);
    }

    public void closeHorizontalClaw() {
        robot.horizontalClawGripServo.setPosition(Constants.CLAW_GRIP_CLOSED_POSITION);
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
                setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION);
                // bring back slides
                setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_TRANSFER_POSITION);
                setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION);
                setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION);
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
        robot.horizontalWristPitchServoL.setPosition(-position);
        robot.horizontalWristPitchServoR.setPosition(position);
    }

    public void setHorizontalSlidePosition(double position) {
        robot.horizontalSlideServoL.setPosition(-position);
        robot.horizontalSlideServoR.setPosition(position);
    }

    public void setHorizontalClawRollPosition(double position) {
        robot.horizontalClawRollServo.setPosition(position);
    }

    public void setHorizontalClawPitchPosition(double position) {
        robot.horizontalClawPitchServo.setPosition(position);
    }

    public void setVerticalWristPitchPosition(double position) {
        robot.verticalWristPitchServoL.setPosition(-position);
        robot.verticalWristPitchServoR.setPosition(position);
    }

    public void setVerticalSlidePosition(int position) {
        robot.verticalSlideMotorBottom.setTargetPosition(-position);
        robot.verticalSlideMotorTop.setTargetPosition(position);
    }

    public void setVerticalClawRollPosition(double position) {
        robot.verticalClawRollServo.setPosition(position);
    }

    public void setVerticalClawPitchPosition(double position) {
        robot.verticalClawPitchServo.setPosition(position);
    }
}


