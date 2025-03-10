package org.firstinspires.ftc.teamcode.commands.slides;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;

public class SetHorizontalSlidePosition extends CommandBase {
    private final HorizontalSlideSubsystem horizontalSlideSubsystem;
    private final double horizontalSlidePosition;

    public SetHorizontalSlidePosition(HorizontalSlideSubsystem horizontalSlideSubsystem, double horizontalSlidePosition) {
        this.horizontalSlideSubsystem = horizontalSlideSubsystem;
        this.horizontalSlidePosition = horizontalSlidePosition;
    }

    @Override
    public void initialize() {
        horizontalSlideSubsystem.setHorizontalSlidePosition(horizontalSlidePosition);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
