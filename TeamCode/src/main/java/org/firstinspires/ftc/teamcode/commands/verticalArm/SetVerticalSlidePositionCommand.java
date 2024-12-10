package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class SetVerticalSlidePositionCommand extends CommandBase {

    private Motor verticalSlideMotorTop;
    private Motor verticalSlideMotorBottom;
    private IntakeSubsystem intakeSubsystem;
    private int targetPosition;
    private Telemetry telemetry;

    public static double SLIDE_SLOWDOWN_THRESHOLD = 0.2;


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

//        // if the target position is the transfer position (all the way down),
//        // when the motors reach a certain point, slow them down
//        if (targetPosition == Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION) {
//            if ((double) Math.abs(verticalSlideMotorTop.getCurrentPosition() - targetPosition) / targetPosition < SLIDE_SLOWDOWN_THRESHOLD) {
//                verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_SLOW);
//                verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_SLOW);
//            } else {
//                verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
//                verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
//            }
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
