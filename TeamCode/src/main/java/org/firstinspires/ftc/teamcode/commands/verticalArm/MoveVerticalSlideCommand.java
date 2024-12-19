package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class MoveVerticalSlideCommand extends CommandBase {

    private int targetPosition;
    private IntakeSubsystem intakeSubsystem;
    public MoveVerticalSlideCommand(IntakeSubsystem intakeSubsystem, int positionDelta) {
        this.intakeSubsystem = intakeSubsystem;
        this.targetPosition = intakeSubsystem.verticalSlideMotorTop.getCurrentPosition() + positionDelta;
    }



}
