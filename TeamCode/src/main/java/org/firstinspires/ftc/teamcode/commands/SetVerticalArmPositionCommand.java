package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetVerticalArmPositionCommand extends SequentialCommandGroup {
    private final IntakeSubsystem intakeSubsystem;
    private final IntakeSubsystem.IntakeState intakeState;

    public SetVerticalArmPositionCommand(IntakeSubsystem intakeSubsystem, IntakeSubsystem.IntakeState intakeState) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeState = intakeState;
        switch (intakeState) {
            case TRANSFER:
                addCommands(
                        new InstantCommand(() -> {
                            // claw should already be closed
//                                    intakeSubsystem.closeClaw();
                            intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION);
                            // bring back slides
                            // TODO WE'LL COME BACK TO THIS LATER V
//                setVerticalSlidePosition(Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION);
                            intakeSubsystem.setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION);
                            intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_TRANSFER_POSITION);
                        })
                );
                break;
            case DEPOSIT:
                addCommands(
                        new InstantCommand(() -> {
                            intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_DEPOSIT_POSITION);
//                setVerticalSlideMotorCurrentTargetPosition(Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION);
                            intakeSubsystem.setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_DEPOSIT_POSITION);
                            intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_DEPOSIT_POSITION);

                        })
                );
                break;
        }
//        addRequirements(intakeSubsystem);
    }


}
