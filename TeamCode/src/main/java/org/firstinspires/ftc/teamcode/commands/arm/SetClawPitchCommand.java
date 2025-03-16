package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class SetClawPitchCommand extends CommandBase {

    private final ArmSubsystem armSubsystem;
    private final double angle;

    public SetClawPitchCommand(ArmSubsystem armSubsystem, double angle) {
        this.armSubsystem = armSubsystem;
        this.angle = angle;
    }

    @Override
    public void initialize() {
        armSubsystem.setClawPitch(angle);

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
