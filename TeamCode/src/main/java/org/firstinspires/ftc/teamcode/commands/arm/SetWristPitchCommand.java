package org.firstinspires.ftc.teamcode.commands.arm;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class SetWristPitchCommand extends CommandBase {

    private final double angle;
    private final ArmSubsystem armSubsystem;

    public SetWristPitchCommand(ArmSubsystem armSubsystem, double angle) {
        this.angle = angle;
        this.armSubsystem = armSubsystem;
    }

    @Override
    public void initialize() {
        armSubsystem.setWristPitch(angle);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
