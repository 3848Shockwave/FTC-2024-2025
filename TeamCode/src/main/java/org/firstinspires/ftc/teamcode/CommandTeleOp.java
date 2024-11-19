package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.GyroEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.TransferCommandSequence;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private DriveCommand driveCommand;
    private GamepadEx gamepad;


    @Override
    public void initialize() {

        gamepad = new GamepadEx(gamepad1);

//        imu = new RevIMU(hardwareMap, "imu");

        driveSubsystem = new DriveSubsystem(hardwareMap, telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);

        driveCommand = new DriveCommand(driveSubsystem, gamepad::getLeftX, gamepad::getLeftY, gamepad::getRightX, () -> Constants.IS_FIELD_CENTRIC);

        // good practice to register the subsystem before setting default command
        register(driveSubsystem);
        // "always be runnin this thing"
        driveSubsystem.setDefaultCommand(driveCommand);

        // whenPressed is rising edge btw
        // a to open, b to close claw (if intaking, should be intake arm claw, and if outtaking, etc.)
        gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> {
            intakeSubsystem.openClawManual(intakeSubsystem.currentIntakeState);
        }));
        gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> {
            intakeSubsystem.closeClawManual(intakeSubsystem.currentIntakeState);
        }));

        // back button resets imu
        gamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new InstantCommand(() -> {
            driveSubsystem.resetIMU();
        }));

        // initiate transfer command sequence
        gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new TransferCommandSequence(intakeSubsystem));

    }
}
