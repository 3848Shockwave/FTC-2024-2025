package org.firstinspires.ftc.teamcode.commands.arm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

import java.util.function.DoubleSupplier;

@Config
public class SetWristClawPitchCommand extends CommandBase {

    private double diffyServoLSetPoint;
    private double diffyServoRSetPoint;
    private double wristPitch;
    private double clawPitch;
    private ArmSubsystem armSubsystem;

    public SetWristClawPitchCommand(ArmSubsystem armSubsystem, double wristPitch, double clawPitch) {
        this.wristPitch = wristPitch;
        this.clawPitch = clawPitch;
        this.armSubsystem = armSubsystem;

    }

    @Override
    public void initialize() {
        this.diffyServoLSetPoint = (wristPitch + clawPitch) / 2;
        this.diffyServoRSetPoint = (wristPitch - clawPitch) / 2;


    }

//    @Override
//    public void execute() {
//
//    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
