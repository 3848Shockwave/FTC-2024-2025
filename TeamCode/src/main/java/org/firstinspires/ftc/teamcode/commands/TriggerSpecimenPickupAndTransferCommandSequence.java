package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;


@Config
public class TriggerSpecimenPickupAndTransferCommandSequence extends SequentialCommandGroup {
    public enum Type {
        SAMPLE,
        SPECIMEN
    }
    public static int PICKUP_TRANSFER_WAIT = 100;
    public TriggerSpecimenPickupAndTransferCommandSequence(IntakeSubsystem intakeSubsystem) {
        addCommands(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
                new WaitCommand(PICKUP_TRANSFER_WAIT),
                new SpecimenTransferCommandSequence(intakeSubsystem)
        );
        addRequirements(intakeSubsystem);
    }
}
