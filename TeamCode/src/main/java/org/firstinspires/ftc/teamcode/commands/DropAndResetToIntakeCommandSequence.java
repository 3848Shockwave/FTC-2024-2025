package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class DropAndResetToIntakeCommandSequence extends SequentialCommandGroup {

    public static int WAIT0 = 1000;
    public static int WAIT1 = 1000;
    public static int WAIT2 = 1000;
    private IntakeSubsystem intakeSubsystem;

    public DropAndResetToIntakeCommandSequence(IntakeSubsystem intakeSubsystem, Telemetry telemetry) {
        interruptOn(() -> false);
        addCommands(
                new InstantCommand(intakeSubsystem::openVerticalClaw),
                new WaitCommand(WAIT0),
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
                new WaitCommand(WAIT1),
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL),
                new WaitCommand(WAIT2),
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION, telemetry)
        );
        addRequirements(intakeSubsystem);
    }



}
