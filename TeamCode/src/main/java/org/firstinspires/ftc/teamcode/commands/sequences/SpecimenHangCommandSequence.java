package org.firstinspires.ftc.teamcode.commands.sequences;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

@Config
public class SpecimenHangCommandSequence extends SequentialCommandGroup {

    public static long WAIT0 = 150;
    public static long WAIT1 = 50;

    public SpecimenHangCommandSequence(ArmSubsystem horizontalArmSubsystem, ArmSubsystem verticalArmSubsystem, HorizontalSlideSubsystem horizontalSlideSubsystem, VerticalSlideSubsystem verticalSlideSubsystem) {
        addCommands(
                // put vertical slides back down
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_SPECIMEN_ON_BAR_POSITION),

                // STOP! wait a minute
                new WaitCommand(WAIT0),
                // open vertical claw to release
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION),

                // STOP! wait a minute
                new WaitCommand(WAIT1),
                // put vertical slides back down
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION),
                // set vertical arm to transfer position
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_ROLL_TRANSFER_POSITION),
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION),

                // open horizontal claw
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),

                // set horizontal arm to vertical
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION),
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION)

        );
        addRequirements(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem);
    }
}
