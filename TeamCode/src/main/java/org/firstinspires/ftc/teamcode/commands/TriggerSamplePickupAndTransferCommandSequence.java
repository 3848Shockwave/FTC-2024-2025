package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;


@Config
public class TriggerSamplePickupAndTransferCommandSequence extends SequentialCommandGroup {
    public enum Type {
        SAMPLE,
        SPECIMEN
    }
    public static int DROP_CLOSE_WAIT = 100;
    public TriggerSamplePickupAndTransferCommandSequence(ArmSubsystem horizontalArmSubsystem, ArmSubsystem verticalArmSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                // set horizontal arm to intake position
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION),
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION),

                new WaitCommand(DROP_CLOSE_WAIT),

                new SampleTransferCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, intakeSubsystem)
        );

        // TODO: might cause conflict
        addRequirements(horizontalArmSubsystem, verticalArmSubsystem, intakeSubsystem);
    }
}
