package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ElevatorFeedforward;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class RunVerticalSlideCommand extends CommandBase {

    private MotorEx verticalSlideMotorTop;
    private MotorEx verticalSlideMotorBottom;
    private IntakeSubsystem intakeSubsystem;
    private Telemetry telemetry;
    public static double KV = 1;
    public static double KS = 0;
    public static double KG = 0;
    public static double KA = 0;
    public ElevatorFeedforward elevatorFeedforward = new ElevatorFeedforward(KS, KG, KV, KA);



    public RunVerticalSlideCommand(IntakeSubsystem intakeSubsystem, Telemetry telemetry) {
        this.intakeSubsystem = intakeSubsystem;
        verticalSlideMotorTop = intakeSubsystem.verticalSlideMotorTop;
        verticalSlideMotorBottom = intakeSubsystem.verticalSlideMotorTop;
        this.telemetry = telemetry;
        // THIS MAKES IT BLOCKING: IT R E Q U I R E S THE SUBSYSTEM
//        addRequirements(intakeSubsystem);
//        interruptOn(() -> false);

    }


    @Override
    public void execute() {

//        elevatorFeedforward.calculate(Constants.VERTICAL_SLIDE_MOTOR_SPEED_SLOW);
//

        if (verticalSlideMotorTop.atTargetPosition() || verticalSlideMotorBottom.atTargetPosition()) {
            verticalSlideMotorTop.stopMotor();
            verticalSlideMotorBottom.stopMotor();
        } else {

            verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
            verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);

        }



//        double velocity = elevatorFeedforward.calculate(verticalSlideMotorTop.getCurrentPosition());
//        verticalSlideMotorTop.setVelocity(velocity);
//        verticalSlideMotorBottom.setVelocity(-velocity);


    }


}
