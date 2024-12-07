package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class ResetToIntakeCommandSequence extends SequentialCommandGroup {

    public static int WAIT0 = 2000;

    public ResetToIntakeCommandSequence(IntakeSubsystem intakeSubsystem, Telemetry telemetry) {
        addCommands(
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
                new WaitCommand(WAIT0),
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE)
        );
        addRequirements(intakeSubsystem);
    }


}
