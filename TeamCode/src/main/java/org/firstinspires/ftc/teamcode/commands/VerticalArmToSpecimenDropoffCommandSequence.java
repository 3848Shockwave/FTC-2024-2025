package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.SpecimenConstants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class VerticalArmToSpecimenDropoffCommandSequence extends ParallelCommandGroup {
    public VerticalArmToSpecimenDropoffCommandSequence(IntakeSubsystem intakeSubsystem, int wait) {
        addCommands(
                // set vertical slide position to deposit position, after start of this command: wait 500 ms, then set vertical arm to deposit position
                new ParallelCommandGroup(
                        // set vertical slide position to transfer position
                        new SetVerticalSlidePositionCommand(intakeSubsystem, SpecimenConstants.VERTICAL_SLIDE_MOTOR_SPECIMEN_UP_POSITION),
                        new SequentialCommandGroup(
                                new WaitCommand(wait),
                                // set vertical claw to specimen dropoff position
                                new InstantCommand(() -> {
                                    intakeSubsystem.setVerticalWristPitchPosition(SpecimenConstants.VERTICAL_WRIST_PITCH_SPECIMEN_DROPOFF_POSITION);
                                    intakeSubsystem.setVerticalClawPitchPosition(SpecimenConstants.VERTICAL_CLAW_PITCH_SPECIMEN_DROPOFF_POSITION);
                                    intakeSubsystem.setVerticalClawRollPosition(SpecimenConstants.VERTICAL_CLAW_ROLL_SPECIMEN_DROPOFF_POSITION);
                                }),
                                // set horizontal arm to be straight up
                                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL)
                        )
                )
        );

    }
}
