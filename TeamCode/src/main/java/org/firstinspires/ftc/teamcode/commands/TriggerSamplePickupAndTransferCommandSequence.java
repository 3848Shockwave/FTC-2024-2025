package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;


@Config
public class TriggerSamplePickupAndTransferCommandSequence extends SequentialCommandGroup {
    public enum Type {
        SAMPLE,
        SPECIMEN
    }
    public static int DROP_CLOSE_WAIT = 100;
    public TriggerSamplePickupAndTransferCommandSequence(IntakeSubsystem intakeSubsystem) {
        addCommands(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
                new WaitCommand(DROP_CLOSE_WAIT),
                new SampleTransferCommandSequence(intakeSubsystem)
        );
        addRequirements(intakeSubsystem);
    }
}
