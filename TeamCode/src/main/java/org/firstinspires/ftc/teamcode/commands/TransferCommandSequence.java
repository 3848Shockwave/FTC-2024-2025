package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class TransferCommandSequence extends SequentialCommandGroup {

    IntakeSubsystem intakeSubsystem;
    public TransferCommandSequence(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        addCommands(
                new ParallelCommandGroup(
                        // set horizontal arm to transfer position
                        // wait like 50-200 ms
                        // set vertical arm to transfer position (this is going to happen during
                )
                //...

                // (wait until ^ done)
                // close vertical arm claw
                ,new InstantCommand()
                // (wait until ^ done)
                // open horizontal arm claw
                // (wait until ^ done)
                // set horizontal arm to outtake position
                // DONE!
        );
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.currentState = IntakeSubsystem.State.TRANSFERRING;
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.currentState = IntakeSubsystem.State.OUTTAKING;
    }
}
