package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class SpecimenHangCommandSequence extends SequentialCommandGroup {

    public static long WAIT0 = 50;
    public static long WAIT1 = 50;

    public SpecimenHangCommandSequence(IntakeSubsystem intakeSubsystem) {
        addCommands(
                // put vertical slides back down
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_SPECIMEN_ON_BAR_POSITION),
                // STOP! wait a minute
                new WaitCommand(WAIT0),
                // open vertical claw (redundant)
                new InstantCommand(intakeSubsystem::openVerticalClaw),
                // STOP! wait a minute
                new WaitCommand(WAIT1),
                // put vertical slides back down
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION),
                // set vertical arm to transfer position
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
                // open horizontal claw
                new InstantCommand(intakeSubsystem::openHorizontalClaw),
                // set horizontal arm to vertical
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL)

        );
        addRequirements(intakeSubsystem);
    }
}
