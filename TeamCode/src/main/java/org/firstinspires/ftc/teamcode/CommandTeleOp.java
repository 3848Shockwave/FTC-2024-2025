package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.MicroMoveHorizontalSlideManualCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.MoveHorizontalSlideTouchpadCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private DriveCommand driveCommand;
    private GamepadEx driverGamepad;
    private GamepadEx utilityGamepad;

    private FtcDashboard dashboard;

    private Telemetry currentTelemetry;


    @Override
    public void initialize() {

        dashboard = FtcDashboard.getInstance();
        currentTelemetry = dashboard.getTelemetry();
//        currentTelemetry = telemetry;

        driverGamepad = new GamepadEx(gamepad1);
        utilityGamepad = new GamepadEx(gamepad2);


        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap, currentTelemetry);

        driveCommand = new DriveCommand(driveSubsystem, driverGamepad::getLeftX, driverGamepad::getLeftY, driverGamepad::getRightX, () -> Constants.IS_FIELD_CENTRIC);

//        // good practice to register the subsystem before setting default command
        register(driveSubsystem, intakeSubsystem);
//        // "always be runnin this thing"
//        driveSubsystem.setDefaultCommand(driveCommand);
//        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
//                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION, currentTelemetry)
//        );
//        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
//                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION, currentTelemetry)
//        );

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openClawManual(IntakeSubsystem.IntakeState.INTAKE)
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeClawManual(IntakeSubsystem.IntakeState.INTAKE)
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openClawManual(IntakeSubsystem.IntakeState.DEPOSIT)
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeClawManual(IntakeSubsystem.IntakeState.DEPOSIT)
        ));


        // put the claw inside the sample but don't close it
        driverGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new ParallelCommandGroup(
                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
                        new InstantCommand(() -> gamepad1.rumble(250))
                )
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new DropAndResetToIntakeCommandSequence(intakeSubsystem, telemetry)
        );
//        driverGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
//                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT)
//        );

        // SPECIMEN TRANSFER SEQUENCE
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(
                new SpecimenTransferCommandSequence(intakeSubsystem, currentTelemetry)
        );

        // TRANSFER SEQUENCE
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(
                new SampleTransferCommandSequence(intakeSubsystem, currentTelemetry)
        );

        // triggers to control claw roll
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new InstantCommand(() -> intakeSubsystem.horizontalClawRollServo.turnToAngle(Constants.HORIZONTAL_CLAW_ROLL_PARALLEL_POSITION))
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new InstantCommand(() -> intakeSubsystem.horizontalClawRollServo.turnToAngle(Constants.HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION))
        );

        schedule(new MoveHorizontalSlideTouchpadCommand(
                intakeSubsystem,
                () -> gamepad1.touchpad_finger_1_x,
                () -> gamepad1.touchpad_finger_1
        ));

        schedule(new MicroMoveHorizontalSlideManualCommand(
                intakeSubsystem,
                () -> driverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> driverGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
        ));


//        // triggers to control claw roll
//        schedule(new RunCommand(() -> {
//            double leftTriggerValue = driverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
//            double rightTriggerValue = driverGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
//            intakeSubsystem.horizontalClawRollServo.rotateByAngle(Constants.HORIZONTAL_CLAW_ROLL_SPEED * (leftTriggerValue - rightTriggerValue));
////            intakeSubsystem.horizontalClawRollServo.rotateByAngle(rightTriggerValue - leftTriggerValue);
//        }));

        schedule(new RunCommand(() -> currentTelemetry.update()));

//        // whenPressed is rising edge btw
//        // a to open, b to close claw (if intaking, should be intake arm claw, and if outtaking, etc.)

        // back button resets imu
        driverGamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new InstantCommand(() -> {
            driveSubsystem.resetIMU();
        }));


//        schedule(new InstantCommand(() -> {
//            currentTelemetry.addData("Command TeleOp", "initialized");
//            currentTelemetry.update();
//        }));

    }
}
