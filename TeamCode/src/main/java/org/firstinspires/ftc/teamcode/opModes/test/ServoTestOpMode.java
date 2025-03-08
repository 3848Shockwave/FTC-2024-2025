package org.firstinspires.ftc.teamcode.opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name = "SERVO TEST")
public class ServoTestOpMode extends LinearOpMode {

    // PID(F) declaration
    // kp = 0, ki = 0, kd = 0, kf = 0;
    public static double POSITION_0 = 0;
    public static double MAX_DEGREE = 315;
    public static double POSITION_1 = 180;

    @Override
    public void runOpMode() throws InterruptedException {
        MultipleTelemetry telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), this.telemetry);

//        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        ServoEx servo = new SimpleServo(hardwareMap, "servo", 0, MAX_DEGREE);


        waitForStart();

        while (opModeIsActive()) {
            servo.turnToAngle(POSITION_0);
        }

    }
}
