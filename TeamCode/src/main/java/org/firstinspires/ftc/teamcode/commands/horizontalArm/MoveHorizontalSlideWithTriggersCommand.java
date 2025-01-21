package org.firstinspires.ftc.teamcode.commands.horizontalArm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

@Config
public class MoveHorizontalSlideWithTriggersCommand extends CommandBase {

    private IntakeSubsystem intakeSubsystem;
    private DoubleSupplier leftTriggerValue;
    private DoubleSupplier rightTriggerValue;

    public MoveHorizontalSlideWithTriggersCommand(IntakeSubsystem intakeSubsystem, DoubleSupplier leftTriggerValue, DoubleSupplier rightTriggerValue) {
        this.intakeSubsystem = intakeSubsystem;
        this.leftTriggerValue = leftTriggerValue;
        this.rightTriggerValue = rightTriggerValue;
    }

    @Override
    public void execute() {
        double leftTriggerValue = this.leftTriggerValue.getAsDouble();
        double rightTriggerValue = this.rightTriggerValue.getAsDouble();

        if (leftTriggerValue > Constants.TRIGGER_DEADZONE || rightTriggerValue > Constants.TRIGGER_DEADZONE) {
            intakeSubsystem.moveHorizontalSlide((rightTriggerValue - leftTriggerValue) * Constants.HORIZONTAL_SLIDE_SPEED_MANUAL);
        }
    }
}
