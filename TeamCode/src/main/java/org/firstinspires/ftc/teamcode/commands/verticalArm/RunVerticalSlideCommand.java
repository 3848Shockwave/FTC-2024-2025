package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;

@Config
public class RunVerticalSlideCommand extends CommandBase {

    private MotorEx verticalSlideMotorTop;
    private MotorEx verticalSlideMotorBottom;
    private IntakeSubsystem intakeSubsystem;
    private Telemetry telemetry;
    public static double KP = 0.001;
    public static double KI = 0;
    public static double KD = 0;
    public static double KF = 0;

    public PIDFController slidePIDFController = new PIDFController(KP, KI, KD, KF);

    public IntSupplier targetPosition;


    /**
     * Sets motor velocity based on a feedforward controller.
     * The motors will automatically run to their internal target positions, set by SetVerticalSlidePositionCommand.
     * This command acts as a control loop to set the motors' speeds
     * @param intakeSubsystem
     * @param telemetry
     */
    public RunVerticalSlideCommand(IntakeSubsystem intakeSubsystem, IntSupplier targetPosition, Telemetry telemetry) {
        this.intakeSubsystem = intakeSubsystem;
        verticalSlideMotorTop = intakeSubsystem.verticalSlideMotorTop;
        verticalSlideMotorBottom = intakeSubsystem.verticalSlideMotorTop;
        this.telemetry = telemetry;
        this.targetPosition = targetPosition;
    }


    @Override
    public void execute() {

        if (!verticalSlideMotorTop.atTargetPosition()) {
            verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
            verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
        } else {
            verticalSlideMotorTop.stopMotor();
            verticalSlideMotorBottom.stopMotor();
        }

    }

}
