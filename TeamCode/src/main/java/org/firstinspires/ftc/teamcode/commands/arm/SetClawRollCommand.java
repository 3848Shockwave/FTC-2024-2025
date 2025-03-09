package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class SetClawRollCommand extends CommandBase {

    private final ArmSubsystem armSubsystem;
    private final double angle;

    public SetClawRollCommand(ArmSubsystem armSubsystem, double angle) {
        this.armSubsystem = armSubsystem;
        this.angle = angle;
        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {
        armSubsystem.setClawRoll(angle);

    }

//    @Override
//    public void execute() {
//    }
//
//    @Override
//    public void end(boolean interrupted) {
//    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
