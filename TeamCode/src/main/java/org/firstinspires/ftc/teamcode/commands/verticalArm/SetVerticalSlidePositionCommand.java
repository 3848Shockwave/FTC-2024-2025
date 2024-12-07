package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetVerticalSlidePositionCommand extends CommandBase {

    private Motor verticalSlideMotorTop;
    private Motor verticalSlideMotorBottom;
    private IntakeSubsystem intakeSubsystem;
    private int targetPosition;
    private Telemetry telemetry;


    public SetVerticalSlidePositionCommand(IntakeSubsystem intakeSubsystem, int targetPosition, Telemetry telemetry) {
        this.intakeSubsystem = intakeSubsystem;
        verticalSlideMotorTop = intakeSubsystem.verticalSlideMotorTop;
        verticalSlideMotorBottom = intakeSubsystem.verticalSlideMotorTop;
        this.targetPosition = targetPosition;
        this.telemetry = telemetry;
        // THIS MAKES IT BLOCKING: IT R E Q U I R E S THE SUBSYSTEM
//        addRequirements(intakeSubsystem);

    }

    @Override
    public void initialize() {
        intakeSubsystem.setVerticalSlideMotorsTargetPosition(targetPosition);
    }


    @Override
    public void execute() {
            verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
            verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);

//        if ((double) Math.abs(verticalSlideMotorTop.getCurrentPosition() - targetPosition) / targetPosition < 0.5) {
//            verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_SLOW);
//            verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_SLOW);
//        } else {
//            verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
//            verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
//        }

        telemetry.addLine("if you are seeing this and the motors aren't moving, that's bad and it's probably my fault.");
    }

    @Override
    public void end(boolean interrupted) {
        verticalSlideMotorTop.stopMotor();
        verticalSlideMotorBottom.stopMotor();
    }

    @Override
    public boolean isFinished() {
        return verticalSlideMotorTop.atTargetPosition() || verticalSlideMotorBottom.atTargetPosition();
    }
}
