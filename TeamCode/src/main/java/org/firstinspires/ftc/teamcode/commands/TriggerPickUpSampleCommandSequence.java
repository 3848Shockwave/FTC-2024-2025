package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import static org.firstinspires.ftc.teamcode.commands.SampleTransferCommandSequence.CLOSE_CLAW_WAIT;
import static org.firstinspires.ftc.teamcode.commands.TriggerSamplePickupAndTransferCommandSequence.DROP_CLOSE_WAIT;

public class TriggerPickUpSampleCommandSequence extends SequentialCommandGroup {

    public TriggerPickUpSampleCommandSequence(ArmSubsystem horizontalArmSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                // horizontal arm to intake position
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION),
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION),

                new WaitCommand(DROP_CLOSE_WAIT),
                // close horizontal arm claw to pick up the sample
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_CLOSED_POSITION),
//                // (wait until ^ done)
                new WaitCommand(CLOSE_CLAW_WAIT),
                // set horizontal arm to pickup position
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_PICKUP_POSITION)
                // TODO: change this maybe?
//                new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION))

        );
    }
}
