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
        switch (intakeState) {
            case INTAKE:
                addCommands(
                        new InstantCommand(() -> {
                            // claw should already be closed
//                                    intakeSubsystem.closeClaw();
                            intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION);
                            // bring back slides
                            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_INTAKE_POSITION);
                            intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION);
                            intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_INTAKE_POSITION);

                        })

                );
                break;
            case TRANSFER:
                addCommands(
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION);
                            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_TRANSFER_POSITION);
                            intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION);
                            intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION);

                        })
                );
                break;
        }
//        addRequirements(intakeSubsystem);
    }


}
