package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class EndgameCommand extends SequentialCommandGroup {
    public EndgameCommand(IntakeSubsystem intakeSubsystem) {

        addCommands(
//                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION),
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT)
        );

    }
}
