package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetHorizontalArmPositionCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final IntakeSubsystem.IntakeState intakeState;

    public SetHorizontalArmPositionCommand(IntakeSubsystem intakeSubsystem, IntakeSubsystem.IntakeState intakeState) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeState = intakeState;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void execute() {
        intakeSubsystem.setHorizontalArmPosition(intakeState);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
