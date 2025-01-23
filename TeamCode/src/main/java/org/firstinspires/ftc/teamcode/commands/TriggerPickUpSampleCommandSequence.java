package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import static org.firstinspires.ftc.teamcode.commands.SampleTransferCommandSequence.CLOSE_CLAW_WAIT;
import static org.firstinspires.ftc.teamcode.commands.TriggerSamplePickupAndTransferCommandSequence.DROP_CLOSE_WAIT;

public class TriggerPickUpSampleCommandSequence extends SequentialCommandGroup {

    public TriggerPickUpSampleCommandSequence(IntakeSubsystem intakeSubsystem) {
        addCommands(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
                new WaitCommand(DROP_CLOSE_WAIT),
                // close horizontal arm claw to pick up the sample
                new InstantCommand(intakeSubsystem::closeHorizontalClaw),
//                // (wait until ^ done)
                new WaitCommand(CLOSE_CLAW_WAIT),
                // set horizontal arm to pickup position
                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_PICKUP_POSITION);
                }),
                new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION))

        );
    }
}
