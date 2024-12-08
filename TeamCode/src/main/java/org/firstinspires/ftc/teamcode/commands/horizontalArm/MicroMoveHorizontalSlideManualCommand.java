package org.firstinspires.ftc.teamcode.commands.horizontalArm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

@Config
public class MicroMoveHorizontalSlideManualCommand extends CommandBase {

    public static double HORIZONTAL_SLIDE_SPEED = 0.75;
    public static double TRIGGER_THRESHOLD = 0.2;

    private IntakeSubsystem intakeSubsystem;
    private DoubleSupplier leftTriggerValue;
    private DoubleSupplier rightTriggerValue;

    public MicroMoveHorizontalSlideManualCommand(IntakeSubsystem intakeSubsystem, DoubleSupplier leftTriggerValue, DoubleSupplier rightTriggerValue) {
        this.intakeSubsystem = intakeSubsystem;
        this.leftTriggerValue = leftTriggerValue;
        this.rightTriggerValue = rightTriggerValue;
    }

    @Override
    public void execute() {
        double leftTriggerValue = this.leftTriggerValue.getAsDouble();
        double rightTriggerValue = this.rightTriggerValue.getAsDouble();

        if (leftTriggerValue > TRIGGER_THRESHOLD || rightTriggerValue > TRIGGER_THRESHOLD) {
            intakeSubsystem.moveHorizontalSlide((rightTriggerValue - leftTriggerValue) * HORIZONTAL_SLIDE_SPEED);
        }
    }
}
