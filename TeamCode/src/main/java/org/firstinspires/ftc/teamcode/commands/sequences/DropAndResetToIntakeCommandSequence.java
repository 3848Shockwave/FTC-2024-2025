package org.firstinspires.ftc.teamcode.commands.sequences;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.commands.slides.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

@Config
public class DropAndResetToIntakeCommandSequence extends SequentialCommandGroup {

    public static int WAIT0 = 500;
    public static int WAIT1 = 250;

    public DropAndResetToIntakeCommandSequence(ArmSubsystem horizontalArmSubsystem, ArmSubsystem verticalArmSubsystem, HorizontalSlideSubsystem horizontalSlideSubsystem, VerticalSlideSubsystem verticalSlideSubsystem) {
        addCommands(
                // open vertical claw
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION),

                // open horizontal claw
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),

                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION),

                new WaitCommand(WAIT0),
                // set horizontal arm to transfer position
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION),
                new SetClawRollCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),

                new WaitCommand(WAIT1),
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION)
        );
        addRequirements(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem);
    }


}
