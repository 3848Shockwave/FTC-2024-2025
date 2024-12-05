package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.PerpetualCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private DriveCommand driveCommand;
    private GamepadEx driverGamepad;
    private GamepadEx utilityGamepad;


    @Override
    public void initialize() {

        driverGamepad = new GamepadEx(gamepad1);
        utilityGamepad = new GamepadEx(gamepad2);

//        imu = new RevIMU(hardwareMap, "imu");

        driveSubsystem = new DriveSubsystem(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);

        driveCommand = new DriveCommand(driveSubsystem, driverGamepad::getLeftX, driverGamepad::getLeftY, driverGamepad::getRightX, () -> Constants.IS_FIELD_CENTRIC);

//        // good practice to register the subsystem before setting default command
        register(driveSubsystem, intakeSubsystem);
//        // "always be runnin this thing"
//        driveSubsystem.setDefaultCommand(driveCommand);
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION, Constants.TEST_DOUBLE, telemetry)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION, Constants.TEST_DOUBLE, telemetry)
        );

        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openClawManual(IntakeSubsystem.IntakeState.INTAKE)
        ));
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeClawManual(IntakeSubsystem.IntakeState.INTAKE)
        ));
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.openClawManual(IntakeSubsystem.IntakeState.DEPOSIT)
        ));
        driverGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() ->
                intakeSubsystem.closeClawManual(IntakeSubsystem.IntakeState.DEPOSIT)
        ));
        driverGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER)
//                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.TEST_INT1, Constants.TEST_DOUBLE, telemetry)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT)
//                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.TEST_INT0, Constants.TEST_DOUBLE, telemetry)
        );
        schedule(new RunCommand(() -> telemetry.update()));

//        // whenPressed is rising edge btw
//        // a to open, b to close claw (if intaking, should be intake arm claw, and if outtaking, etc.)

        // back button resets imu
//        gamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new InstantCommand(() -> {
//            driveSubsystem.resetIMU();
//        }));

//        // initiate transfer command sequence
//        driverGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
//                new TransferCommandSequence(intakeSubsystem)
//        );

//        schedule(new InstantCommand(() -> {
//            telemetry.addData("Command TeleOp", "initialized");
//            telemetry.update();
//        }));

    }
}
