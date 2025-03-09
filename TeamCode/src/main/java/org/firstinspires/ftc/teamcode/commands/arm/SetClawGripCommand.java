package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class SetClawGripCommand extends CommandBase {

    private final double angle;
    private final ArmSubsystem armSubsystem;

    public SetClawGripCommand(ArmSubsystem armSubsystem, double angle) {
        this.angle = angle;
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {
        armSubsystem.setClawGripPosition(angle);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
