
package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.*;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.drive.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetHorizontalSlidePosition;
import org.firstinspires.ftc.teamcode.commands.sequences.*;
import org.firstinspires.ftc.teamcode.commands.slides.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

// BREAKS EVERYTHING
//@Photon
@TeleOp(name = "test op mode")
public class TestOpMode extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private HorizontalSlideSubsystem horizontalSlideSubsystem;
    private VerticalSlideSubsystem verticalSlideSubsystem;
    private ArmSubsystem verticalArmSubsystem, horizontalArmSubsystem;
    //    private DriveCommand driveCommand;
    private GamepadEx driverGamepad;

    private Telemetry currentTelemetry;


    @Override
    public void initialize() {

//        PinpointDrive drive = new PinpointDrive(hardwareMap, new Pose2d(0, 0, 0));
//        drive.pinpoint.getHeading();

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        driverGamepad = new GamepadEx(gamepad1);


//        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);

        horizontalSlideSubsystem = new HorizontalSlideSubsystem(hardwareMap, currentTelemetry);
        verticalSlideSubsystem = new VerticalSlideSubsystem(hardwareMap, currentTelemetry);

        verticalArmSubsystem = new ArmSubsystem(hardwareMap, currentTelemetry, ArmSubsystem.Type.VERTICAL);
        horizontalArmSubsystem = new ArmSubsystem(hardwareMap, currentTelemetry, ArmSubsystem.Type.HORIZONTAL);

//        driveCommand = new DriveCommand(driveSubsystem, driverGamepad::getLeftX, driverGamepad::getLeftY, driverGamepad::getRightX, () -> Constants.IS_FIELD_CENTRIC);

        // good practice to register the subsystem before setting default command
        register(/*driveSubsystem, */horizontalSlideSubsystem, verticalSlideSubsystem, verticalArmSubsystem, horizontalArmSubsystem);

        // "always be runnin this thing"
//        driveSubsystem.setDefaultCommand(driveCommand);


        driverGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new TriggerSampleIntakeCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem)
        );
//
        driverGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new TriggerSamplePickupAndTransferCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem)
        );

        // horizontal slide min extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_CLOSED_POSITION)
        );
        // horizontal slide middle extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION)
        );
        // horizontal slide min extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_CLOSED_POSITION)
        );
        // horizontal slide middle extension
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION)
        );

        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION)
        );


        schedule(
//                // touchpad drops specimen
//                new RunCommand(() -> {
//                    // TODO: fix maybe by adding rising edge detector or switching to a different button, as of now this repeats if touchpad is pressed
//                    if (gamepad1.touchpad) {
//                        schedule(new SpecimenHangCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem));
//                    }
//                }),

                // immediately set horizontal arm to hover
                // TODO: assess
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION),
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION),

                // immediately set vertical arm to transfer
                // TODO: assess
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION),
                // set vertical arm to transfer position
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_ROLL_TRANSFER_POSITION),

//                // immediately set vertical slide position
////        intakeSubsystem.setVerticalSlideMotorsTargetPosition(Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION);
//                new SetVerticalSlidePositionCommand(verticalSlideSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION),

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
