package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetVerticalSlidePositionCommand extends CommandBase {

    private IntakeSubsystem intakeSubsystem;
    private int targetPosition;


    public SetVerticalSlidePositionCommand(IntakeSubsystem intakeSubsystem, int targetPosition) {
        this.intakeSubsystem = intakeSubsystem;
        this.targetPosition = targetPosition;
        // THIS MAKES IT BLOCKING: IT R E Q U I R E S THE SUBSYSTEM
//        addRequirements(intakeSubsystem);
//        interruptOn(() -> false);

    }

    @Override
    public void initialize() {
        intakeSubsystem.setVerticalSlideMotorsTargetPosition(targetPosition);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
