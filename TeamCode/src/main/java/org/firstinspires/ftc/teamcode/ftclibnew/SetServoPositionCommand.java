package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

public class SetServoPositionCommand extends CommandBase {

    private double position;
    private MySubsystem subsystem;

    public SetServoPositionCommand(MySubsystem subsystem, double position) {
        this.position = position;
        this.subsystem = subsystem;
    }

    @Override
    public void initialize() {
        subsystem.setServoPosition(position);

    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
