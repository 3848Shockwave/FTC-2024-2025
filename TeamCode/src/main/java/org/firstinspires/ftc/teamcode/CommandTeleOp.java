package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.arcrobotics.ftclib.command.*;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.MoveHorizontalSlideWithTriggersCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.RunVerticalSlideCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name = "Command TeleOp - Main Drive Code (use this)")
public class CommandTeleOp extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private DriveCommand driveCommand;
    private GamepadEx driverGamepad;
    private GamepadEx utilityGamepad;

    private FtcDashboard dashboard;

    private Telemetry currentTelemetry;

    private double requestedSlideVelocity;


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
        driveSubsystem.setDefaultCommand(driveCommand);

        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openVerticalClaw()
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeVerticalClaw()
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openHorizontalClaw()
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeHorizontalClaw()
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION)
        );
        utilityGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_HANG_POSITION)
        );
//        // utility gamepad vertical slide controls
//        utilityGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new InstantCommand(() ->
//                intakeSubsystem.setVerticalSlideMotorsVelocity(-Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST)
//        ));
//        utilityGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new InstantCommand(() ->
//                intakeSubsystem.setVerticalSlideMotorsVelocity(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST)
//        ));

        utilityGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION)
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_DEPOSIT_POSITION)
        ));
        utilityGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new DropAndResetToIntakeCommandSequence(intakeSubsystem)
//                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION)
        );
//        utilityGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
//                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION)
//        );
        // triggers to control claw roll
        schedule(new RunCommand(() -> {
            double leftTriggerValue = utilityGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER);
            double rightTriggerValue = utilityGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER);
            intakeSubsystem.horizontalClawRollServo.rotateByAngle(Constants.HORIZONTAL_CLAW_ROLL_SPEED * (leftTriggerValue - rightTriggerValue));
        }));

        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whileHeld(new InstantCommand(() ->
                intakeSubsystem.horizontalClawRollServo.rotateByAngle(Constants.HORIZONTAL_CLAW_ROLL_SPEED)
        ));
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whileHeld(new InstantCommand(() ->
                intakeSubsystem.horizontalClawRollServo.rotateByAngle(Constants.HORIZONTAL_CLAW_ROLL_SPEED)
        ));


        // horizontal slide min extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_EXTENSION)
        ));
        // horizontal slide middle extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIDDLE_EXTENSION)
        ));
        // horizontal slide max extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_EXTENSION)
        ));


        // put the claw touching the sample but don't close the claw
        driverGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new ParallelCommandGroup(
                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
                        new InstantCommand(() -> gamepad1.rumble(100))
                )
        );
        // hover arm over sample
        driverGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE)
        );
        // drop the sample and reset both arms to intake position
        driverGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new DropAndResetToIntakeCommandSequence(intakeSubsystem)
        );


        // SPECIMEN TRANSFER SEQUENCE
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(
                new SpecimenTransferCommandSequence(intakeSubsystem)
        );

        // SAMPLE TRANSFER SEQUENCE
        driverGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                new SampleTransferCommandSequence(intakeSubsystem)
        );
//        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(
//                new SampleTransferCommandSequence(intakeSubsystem, currentTelemetry)
//        );

        // bumpers control claw roll
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new InstantCommand(() -> intakeSubsystem.horizontalClawRollServo.turnToAngle(Constants.HORIZONTAL_CLAW_ROLL_PARALLEL_POSITION))
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new InstantCommand(() -> intakeSubsystem.horizontalClawRollServo.turnToAngle(Constants.HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION))
        );

        // move horizontal slide with touchpad lmao
//        schedule(new MoveHorizontalSlideTouchpadCommand(
//                intakeSubsystem,
//                () -> gamepad1.touchpad_finger_1_x,
//                () -> gamepad1.touchpad_finger_1
//        ));


        // initially set horizontal arm position to hover
        schedule(new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE));

        // triggers shall move horizontal slides
        schedule(new MoveHorizontalSlideWithTriggersCommand(
                intakeSubsystem,
                () -> driverGamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER),
                () -> driverGamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)
        ));

        // back button resets imu
        driverGamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new InstantCommand(() -> {
            driveSubsystem.resetIMU();
        }));

        intakeSubsystem.setVerticalSlideMotorsTargetPosition(Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION);

        schedule(
                new RunVerticalSlideCommand(intakeSubsystem, () -> requestedSlideVelocity, currentTelemetry)
        );

        // update telemetry
        schedule(new RunCommand(() -> currentTelemetry.update()));


//        schedule(new InstantCommand(() -> {
//            currentTelemetry.addData("Command TeleOp", "initialized");
//        }));

    }
}
