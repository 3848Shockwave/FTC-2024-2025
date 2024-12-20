package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.DoubleSupplier;

@Config
public class RunVerticalSlideCommand extends CommandBase {

    private MotorEx verticalSlideMotorTop;
    private MotorEx verticalSlideMotorBottom;
    private IntakeSubsystem intakeSubsystem;
    private Telemetry telemetry;
    public static double KV = 0.2;
    public static double KS = 0;
    public static double KG = 0;
    public static double KA = 0;
    private ElevatorFeedforward elevatorFeedforward;
    private DoubleSupplier manualVelocity;


    /**
     * Sets motor velocity based on a feedforward controller.
     * The motors will automatically run to their internal target positions, set by SetVerticalSlidePositionCommand.
     * This command acts as a control loop to set the motors' speeds
     * @param intakeSubsystem
     * @param manualVelocity
     * @param telemetry
     */
    public RunVerticalSlideCommand(IntakeSubsystem intakeSubsystem, DoubleSupplier manualVelocity, Telemetry telemetry) {
        this.intakeSubsystem = intakeSubsystem;
        verticalSlideMotorTop = intakeSubsystem.verticalSlideMotorTop;
        verticalSlideMotorBottom = intakeSubsystem.verticalSlideMotorTop;
        this.telemetry = telemetry;
        this.manualVelocity = manualVelocity;
        elevatorFeedforward = new ElevatorFeedforward(KS, KG, KV, KA);
        // THIS MAKES IT BLOCKING: IT R E Q U I R E S THE SUBSYSTEM
//        addRequirements(intakeSubsystem);
//        interruptOn(() -> false);

    }


    @Override
    public void execute() {

        setMotorsVelocities();


        telemetry.addData("Manual Velocity: ", manualVelocity.getAsDouble());




    }

    private void setMotorsVelocities() {
        double velocity = elevatorFeedforward.calculate(
                Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST,
                Constants.VERTICAL_SLIDE_MOTOR_ACCELERATION
        );

        verticalSlideMotorTop.set(velocity);
        verticalSlideMotorBottom.set(velocity);

        // OLD:
//        if (verticalSlideMotorTop.atTargetPosition() || verticalSlideMotorBottom.atTargetPosition()) {
//            verticalSlideMotorTop.stopMotor();
//            verticalSlideMotorBottom.stopMotor();
//        } else {
//
//            verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
//            verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
//
//        }
    }


}
