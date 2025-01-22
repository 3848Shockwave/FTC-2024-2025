package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.command.*;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

// BREAKS EVERYTHING
//@Photon
@TeleOp(name = "Command TeleOp - Main Drive Code (use this)")
public class CommandTeleOp extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private DriveCommand driveCommand;
    private GamepadEx driverGamepad;
    private GamepadEx auxiliaryGamepad;

    private Telemetry currentTelemetry;


    @Override
    public void initialize() {

//        PinpointDrive drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));
//        drive.pinpoint.getHeading();

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        driverGamepad = new GamepadEx(gamepad1);
        auxiliaryGamepad = new GamepadEx(gamepad2);


        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap, currentTelemetry);

        driveCommand = new DriveCommand(driveSubsystem, driverGamepad::getLeftX, driverGamepad::getLeftY, driverGamepad::getRightX, () -> Constants.IS_FIELD_CENTRIC);

        // good practice to register the subsystem before setting default command
        register(driveSubsystem, intakeSubsystem);

        // "always be runnin this thing"
        driveSubsystem.setDefaultCommand(driveCommand);

        // AUXILIARY GAMEPAD!!!!!!!!!
        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openVerticalClaw()
        ));
        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeVerticalClaw()
        ));
        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openHorizontalClaw()
        ));
        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeHorizontalClaw()
        ));
        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION)
        );
        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TOUCH_BAR_POSITION)
        );
        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new TouchBarCommand(intakeSubsystem)
        );

        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new DropAndResetToIntakeCommandSequence(intakeSubsystem)
        );

//        // bumpers finely control claw roll
//        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whileHeld(new InstantCommand(() -> {
//            // do it if the current angle is greater than the min
//            if (intakeSubsystem.horizontalClawRollServo.getAngle() > Constants.HORIZONTAL_CLAW_ROLL_PARALLEL_POSITION) {
//                intakeSubsystem.horizontalClawRollServo.rotateByAngle(-Constants.HORIZONTAL_CLAW_ROLL_SPEED);
//            }
//        }));

        auxiliaryGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new InstantCommand(() -> {
            // do it if the current angle is greater than the min
            if (intakeSubsystem.horizontalClawRollServo.getAngle() < Constants.HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION) {
                intakeSubsystem.horizontalClawRollServo.rotateByAngle(Constants.HORIZONTAL_CLAW_ROLL_SPEED);
            }
        }));


        // control claw roll
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new InstantCommand(() -> intakeSubsystem.horizontalClawRollServo.turnToAngle(Constants.HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION))
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new InstantCommand(() -> intakeSubsystem.horizontalClawRollServo.turnToAngle(Constants.HORIZONTAL_CLAW_ROLL_PARALLEL_POSITION))
        );

        // DRIVER GAMEPAD!!!!!!!!!

        // horizontal slide min extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION)
        ));
        // horizontal slide middle extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIDDLE_POSITION)
        ));
        // horizontal slide max extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_POSITION)
        ));


        driverGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE)
        );
        // drop the sample and reset both arms to intake position
        driverGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new DropAndResetToIntakeCommandSequence(intakeSubsystem)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                new TouchBarCommand(intakeSubsystem)
        );

        // right stick to transfer specimen
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(
                new SpecimenTransferCommandSequence(intakeSubsystem)
        );

        // back button resets imu
        driverGamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(
                new ParallelCommandGroup(
                        new InstantCommand(driveSubsystem::resetIMU),
                        new InstantCommand(() -> gamepad1.rumble(200)
                        )
                )
        );


        // triggers for intake
        TriggerReader rightTriggerReader = new TriggerReader(driverGamepad, GamepadKeys.Trigger.RIGHT_TRIGGER);
        TriggerReader leftTriggerReader = new TriggerReader(driverGamepad, GamepadKeys.Trigger.LEFT_TRIGGER);
        schedule(new RunCommand(() -> {
            rightTriggerReader.readValue();
            leftTriggerReader.readValue();

            // TRIGGER PRESSED: set horizontal slide to middle extension and hover over sample/specimen
            if (rightTriggerReader.wasJustPressed()) {
                schedule(
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem)
                );
            }

            if (rightTriggerReader.wasJustReleased()) {
                schedule(
                        new TriggerSamplePickupAndTransferCommandSequence(intakeSubsystem)
                );
            }
            if (leftTriggerReader.wasJustPressed()) {
                schedule(
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem)
                );
            }
            // TRIGGER RELEASED: close claw and pick up sample to put to observation zone
            if (leftTriggerReader.wasJustReleased()) {
                schedule(
                        new TriggerPickUpSampleCommandSequence(intakeSubsystem)
                );
            }
        }));


        schedule(
                // touchpad drops specimen
                new RunCommand(() -> {
                    if (gamepad1.touchpad) {
                        schedule(new SpecimenHangCommandSequence(intakeSubsystem));
                    }
                }),

                // immediately set horizontal arm to hover
                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION);
                    intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION);
                }),

                // immediately set vertical slide position
//        intakeSubsystem.setVerticalSlideMotorsTargetPosition(Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION);
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION),

                // update telemetry
                new RunCommand(() -> currentTelemetry.update())
        );

        // move horizontal slide with touchpad lmao
//        schedule(new MoveHorizontalSlideTouchpadCommand(
//                intakeSubsystem,
//                () -> gamepad1.touchpad_finger_1_x,
//                () -> gamepad1.touchpad_finger_1
//        ));

//        schedule(new InstantCommand(() -> {
//            currentTelemetry.addData("Command TeleOp", "initialized");
//        }));

    }
}
