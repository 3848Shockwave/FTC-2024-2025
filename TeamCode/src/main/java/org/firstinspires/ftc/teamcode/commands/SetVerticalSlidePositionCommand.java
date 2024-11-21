package org.firstinspires.ftc.teamcode.commands;

import android.sax.StartElementListener;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetVerticalSlidePositionCommand extends CommandBase {

    private Motor verticalSlideMotorTop;
    private IntakeSubsystem intakeSubsystem;
    private int targetPosition;
    private double speed;
    private Telemetry telemetry;


    public SetVerticalSlidePositionCommand(IntakeSubsystem intakeSubsystem, int targetPosition, double speed, Telemetry telemetry) {
        this.intakeSubsystem = intakeSubsystem;
        verticalSlideMotorTop = intakeSubsystem.verticalSlideMotorTop;
        this.targetPosition = targetPosition;
        this.speed = speed;
        this.telemetry = telemetry;
        addRequirements(intakeSubsystem);

    }

    @Override
    public void initialize() {
        verticalSlideMotorTop.setTargetPosition(targetPosition);
    }


    @Override
    public void execute() {
        verticalSlideMotorTop.set(speed);
    }

    @Override
    public void end(boolean interrupted) {
        verticalSlideMotorTop.stopMotor();
        telemetry.addLine("command ended");

    }

    @Override
    public boolean isFinished() {

        return verticalSlideMotorTop.atTargetPosition();
    }
}
