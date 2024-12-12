package org.firstinspires.ftc.teamcode.commands.horizontalArm;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class MoveHorizontalSlideTouchpadCommand extends CommandBase {
    private IntakeSubsystem intakeSubsystem;
    private DoubleSupplier touchpadX;
    private BooleanSupplier fingerIsOn;

    public MoveHorizontalSlideTouchpadCommand(IntakeSubsystem intakeSubsystem, DoubleSupplier touchpadX, BooleanSupplier fingerIsOn) {
        this.intakeSubsystem = intakeSubsystem;
        this.touchpadX = touchpadX;
        this.fingerIsOn = fingerIsOn;
    }

    @Override
    public void execute() {
        // don't do anything if finger isn't on
        boolean fingerIsOn = this.fingerIsOn.getAsBoolean();
        if (!fingerIsOn) return;

        double touchpadX = this.touchpadX.getAsDouble();
        double position = Constants.HORIZONTAL_SLIDE_MIN_EXTENSION + touchpadX * Constants.HORIZONTAL_SLIDE_MAX_EXTENSION;

        intakeSubsystem.setHorizontalSlidePosition(position);

    }
}
