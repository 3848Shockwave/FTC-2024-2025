package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
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
import org.firstinspires.ftc.robotcore.external.Telemetry;
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
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION, Constants.TEST_DOUBLE, currentTelemetry)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(
                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION, Constants.TEST_DOUBLE, currentTelemetry)
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

        // put the claw inside the sample but don't close it
        driverGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT)
        );

        // put the arm in hover-over-sample position
        driverGamepad.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE)
        );

        // TRANSFER SEQUENCE
        driverGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(
                new TransferCommandSequence(intakeSubsystem, currentTelemetry)
        );
        schedule(new RunCommand(() -> currentTelemetry.update()));

//        // whenPressed is rising edge btw
//        // a to open, b to close claw (if intaking, should be intake arm claw, and if outtaking, etc.)

        // back button resets imu
//        gamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new InstantCommand(() -> {
//            driveSubsystem.resetIMU();
//        }));


//        schedule(new InstantCommand(() -> {
//            currentTelemetry.addData("Command TeleOp", "initialized");
//            currentTelemetry.update();
//        }));

    }
}
