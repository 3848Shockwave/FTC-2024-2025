package org.firstinspires.ftc.teamcode.commands.sequences;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetHorizontalSlidePosition;
import org.firstinspires.ftc.teamcode.commands.slides.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

@Config
public class TriggerSampleIntakeCommandSequence extends SequentialCommandGroup {

    public static int EXTEND_HOVER_WAIT = 350;

    public TriggerSampleIntakeCommandSequence(ArmSubsystem horizontalArmSubsystem, ArmSubsystem verticalArmSubsystem, HorizontalSlideSubsystem horizontalSlideSubsystem, VerticalSlideSubsystem verticalSlideSubsystem) {
        addCommands(
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION),
                new SetHorizontalSlidePosition(horizontalSlideSubsystem, Constants.HORIZONTAL_SLIDE_MAX_POSITION),

                new WaitCommand(EXTEND_HOVER_WAIT),

                // horizontal arm to hover over sample
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION),
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION),
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),

                // vertical arm to transfer position
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION)
        );
        addRequirements(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem);
    }

}
