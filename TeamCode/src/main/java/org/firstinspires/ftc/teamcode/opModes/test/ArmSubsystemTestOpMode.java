package org.firstinspires.ftc.teamcode.opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristClawPitchCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystemTest;

@Config
@TeleOp(name = "Arm Subsystem Test")
public class ArmSubsystemTestOpMode extends CommandOpMode {

//    ArmSubsystemTest armSubsystem;
    ArmSubsystem armSubsystem;
    private Telemetry currentTelemetry;
    public static double WRIST_PITCH_0 = -360;
    public static double CLAW_PITCH_0 = 0;
    public static double WRIST_PITCH_1 = 360;
    public static double CLAW_PITCH_1 = 0;
    private GamepadEx driverGamepad;

    @Override
    public void initialize() {
        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        armSubsystem = new ArmSubsystem(hardwareMap, currentTelemetry, ArmSubsystem.Type.HORIZONTAL);
        driverGamepad = new GamepadEx(gamepad1);

        register(armSubsystem);

//        schedule(new RunCommand(() -> {
//            armSubsystem.setWristClawPitch(WRIST_PITCH, CLAW_PITCH);
//            currentTelemetry.update();
//        }));

        driverGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(() ->
                armSubsystem.setWristClawPitch(WRIST_PITCH_0, CLAW_PITCH_0)
        );
        driverGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(() ->
                armSubsystem.setWristClawPitch(WRIST_PITCH_1, CLAW_PITCH_1)
        );


    }

}
