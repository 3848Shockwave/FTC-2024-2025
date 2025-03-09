package org.firstinspires.ftc.teamcode.commands.horizontalArm;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetHorizontalSlidePosition extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final double horizontalSlidePosition;

    public SetHorizontalSlidePosition(IntakeSubsystem intakeSubsystem, double horizontalSlidePosition) {
        this.intakeSubsystem = intakeSubsystem;
        this.horizontalSlidePosition = horizontalSlidePosition;
    }

    @Override
    public void initialize() {
        intakeSubsystem.setHorizontalSlidePosition(horizontalSlidePosition);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
