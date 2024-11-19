package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class InitArmsCommand extends CommandBase {
    IntakeSubsystem intakeSubsystem;
    public InitArmsCommand(IntakeSubsystem intakeSubsystem) {
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
//        intakeSubsystem.initArms();
    }


}
