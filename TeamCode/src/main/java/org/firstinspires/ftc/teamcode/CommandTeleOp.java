package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.TransferCommandSequence;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name = "Command TeleOp")
public class CommandTeleOp extends CommandOpMode {

    private DriveSubsystem driveSubsystem;
    private IntakeSubsystem intakeSubsystem;
    private DriveCommand driveCommand;
    private GamepadEx gamepad;

    private Robot robot;


    @Override
    public void initialize() {
        robot = Robot.getRobot();
        robot.init(hardwareMap);


        gamepad = new GamepadEx(gamepad1);

//        imu = new RevIMU(hardwareMap, "imu");

        driveSubsystem = new DriveSubsystem(robot);
        intakeSubsystem = new IntakeSubsystem(robot, telemetry);

        driveCommand = new DriveCommand(driveSubsystem, gamepad::getLeftX, gamepad::getLeftY, gamepad::getRightX, robot.imu::getAbsoluteHeading);

        // good practice to register the subsystem before setting default command
        register(driveSubsystem);
        // "always be runnin this thing"
        driveSubsystem.setDefaultCommand(driveCommand);

        // a to open, b to close claw (if intaking, should be intake arm claw, and if outtaking, etc.)
        gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> {
            intakeSubsystem.openClawManual(intakeSubsystem.currentIntakeState);
        }));
        gamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> {
            intakeSubsystem.closeClawManual(intakeSubsystem.currentIntakeState);
        }));

        gamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new TransferCommandSequence(intakeSubsystem));

    }
}