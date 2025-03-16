package org.firstinspires.ftc.teamcode.commands.sequences;

import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

public class VerticalArmToSpecimenDropoffCommandSequence extends SequentialCommandGroup {
    public VerticalArmToSpecimenDropoffCommandSequence(ArmSubsystem verticalArmSubsystem, VerticalSlideSubsystem verticalSlideSubsystem, int wait) {
        addCommands(
                // set vertical slide position to deposit position, after start of this command: wait 500 ms, then set vertical arm to deposit position
                // set vertical slide position to transfer position
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_SPECIMEN_UP_POSITION),

                new WaitCommand(wait),

                // set vertical claw to specimen dropoff position
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_SPECIMEN_DROPOFF_POSITION),
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_SPECIMEN_DROPOFF_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_ROLL_SPECIMEN_DROPOFF_POSITION)
        );

    }
}
