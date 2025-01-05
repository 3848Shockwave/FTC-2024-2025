package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import static org.firstinspires.ftc.teamcode.commands.SampleTransferCommandSequence.CLOSE_CLAW_WAIT;

public class PickUpSampleCommandSequence extends SequentialCommandGroup {

    public PickUpSampleCommandSequence(IntakeSubsystem intakeSubsystem) {
        addCommands(
                // close horizontal arm claw to pick up the sample
                new InstantCommand(intakeSubsystem::closeHorizontalClaw),
//                // (wait until ^ done)
                new WaitCommand(CLOSE_CLAW_WAIT),
                // set horizontal arm to vertical position
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL)

        );
    }
}
