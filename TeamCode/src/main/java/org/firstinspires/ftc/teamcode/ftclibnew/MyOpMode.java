package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
public class MyOpMode extends CommandOpMode {



    GamepadEx gamepad;

    @Override
    public void initialize() {

        gamepad = new GamepadEx(gamepad1);

        MySubsystem mySubsystem = new MySubsystem(hardwareMap);
        DriveSubsystem driveSubsystem = new DriveSubsystem(hardwareMap);
        DriveCommand driveCommand = new DriveCommand(driveSubsystem, () -> gamepad.getLeftX(), () -> gamepad.getLeftY(), () -> gamepad.getRightX());

        register(driveSubsystem);
        driveSubsystem.setDefaultCommand(driveCommand);



        gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
            new SetServoPositionCommand(mySubsystem, 0.5)
        );

        Runnable myAction = () -> mySubsystem.setServoPosition(0.5);

        gamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(myAction));

    }
}
