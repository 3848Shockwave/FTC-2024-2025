package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.util.ElapsedTime;

public class WaitCommand extends CommandBase {

    private final double duration;
    private ElapsedTime timer;

    public WaitCommand(double duration) {
        this.duration = duration;
    }

    @Override
    public void initialize() {
        timer = new ElapsedTime();
    }

    @Override
    public boolean isFinished() {
        return timer.time() >= duration;
    }
}
