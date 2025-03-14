package org.firstinspires.ftc.teamcode.commands.sequences;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

public class TouchBarCommandSequence extends SequentialCommandGroup {
    public TouchBarCommandSequence(ArmSubsystem verticalArmSubsystem, VerticalSlideSubsystem verticalSlideSubsystem) {

        addCommands(
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TOUCH_BAR_POSITION),

                // set vertical arm position to deposit
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_DEPOSIT_POSITION),
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_DEPOSIT_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_ROLL_DEPOSIT_POSITION)
        );

        addRequirements(verticalArmSubsystem, verticalSlideSubsystem);

    }
}
