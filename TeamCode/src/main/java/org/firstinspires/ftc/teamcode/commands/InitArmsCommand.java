package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class InitArmsCommand extends CommandBase {
    IntakeSubsystem intakeSubsystem;
    private ElapsedTime timer;
    public InitArmsCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
//        intakeSubsystem.closeHorizontalClaw();
    }

}
