package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetHorizontalArmPositionCommand extends SequentialCommandGroup {
    private final IntakeSubsystem intakeSubsystem;
    private final IntakeSubsystem.IntakeState intakeState;

    public SetHorizontalArmPositionCommand(IntakeSubsystem intakeSubsystem, IntakeSubsystem.IntakeState intakeState) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeState = intakeState;
//        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        switch (intakeState) {
            case HOVER_OVER_SAMPLE:
                intakeSubsystem.openHorizontalClaw();
                intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION);
                intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION);

                break;
            case INTAKE:
                intakeSubsystem.closeHorizontalClaw();
                intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION);
                intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION);
                // don't set roll
//                intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_INTAKE_POSITION);

                break;
            case TRANSFER:
                intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION);
                // bring back slides
                intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_TRANSFER_POSITION);
                intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION);
                intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION);

                break;
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }


}
