package org.firstinspires.ftc.teamcode.commands.sequences;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

import static org.firstinspires.ftc.teamcode.commands.sequences.SampleTransferCommandSequence.WAIT4;

public class WallPickupGoUpCommandSequence extends SequentialCommandGroup {
    public static long WAIT0 = 250;
    public WallPickupGoUpCommandSequence(ArmSubsystem verticalArmSubsystem, VerticalSlideSubsystem verticalSlideSubsystem) {
        addCommands(
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_CLOSED_POSITION),
                new WaitCommand(WAIT0),
                new VerticalArmToSpecimenDropoffCommandSequence(verticalArmSubsystem, verticalSlideSubsystem, 500)
        );

    }
}
