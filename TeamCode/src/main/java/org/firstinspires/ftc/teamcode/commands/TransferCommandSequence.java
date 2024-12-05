package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class TransferCommandSequence extends SequentialCommandGroup {

    IntakeSubsystem intakeSubsystem;

    public TransferCommandSequence(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        addCommands(
                // set vertical arm to transfer position
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
//                // (wait until ^ done)
                new WaitCommand(1000),
                // set horizontal arm to transfer position
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
                new WaitCommand(1000),
//                // close vertical arm claw
                new InstantCommand(intakeSubsystem::closeVerticalClaw),
//                // (wait until ^ done)
                new WaitCommand(1000),
//                // open horizontal arm claw
                new InstantCommand(intakeSubsystem::openHorizontalClaw),
//                // (wait until ^ done)
                new WaitCommand(1000),
//                // set horizontal arm to deposit position
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT)
//                // DONE!
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
