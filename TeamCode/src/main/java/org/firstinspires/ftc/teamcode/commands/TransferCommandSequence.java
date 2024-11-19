package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class TransferCommandSequence extends SequentialCommandGroup {

    IntakeSubsystem intakeSubsystem;

    public TransferCommandSequence(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        addCommands(
                // set horizontal arm to transfer position
                new InstantCommand(
                        () -> {
                            intakeSubsystem.setHorizontalArmPosition(
                                    IntakeSubsystem.IntakeState.TRANSFER
                            );
                        }
                ),
                // wait like 50-200 ms
                // 1 is here for all the WaitCommands as a testing placeholder
                new WaitCommand(1),

                // set vertical arm to transfer position (this is going to happen during
                new InstantCommand(
                        () -> {
                            intakeSubsystem.setVerticalArmPosition(
                                    IntakeSubsystem.IntakeState.TRANSFER
                            );
                        }
                ),
                // (wait until ^ done)
                new WaitCommand(1),
                // close vertical arm claw
                new InstantCommand(intakeSubsystem::closeVerticalClaw),
                // (wait until ^ done)
                new WaitCommand(1),
                // open horizontal arm claw
                new InstantCommand(intakeSubsystem::openHorizontalClaw),
                // (wait until ^ done)
                new WaitCommand(1),
                // set horizontal arm to deposit position
                new InstantCommand(
                        () -> {
                            intakeSubsystem.setHorizontalArmPosition(
                                    IntakeSubsystem.IntakeState.DEPOSIT
                            );
                        }
                )
                // DONE!
        );
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.setCurrentState(IntakeSubsystem.IntakeState.TRANSFER);
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.currentIntakeState = IntakeSubsystem.IntakeState.DEPOSIT;
    }
}
