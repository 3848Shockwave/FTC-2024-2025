package org.firstinspires.ftc.teamcode.opModes.test;

import android.icu.text.Transliterator;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.CRServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@Config
@TeleOp(name = "SERVO TEST")
public class ServoTestOpMode extends LinearOpMode {

    // PID(F) declaration
    // kp = 0, ki = 0, kd = 0, kf = 0;
    public static double POSITION_0 = 0;
    public static double MAX_DEGREE = 315;
    public static double POSITION_1 = 180;

    public static double kP = 0, kI = 0, kD = 0, kF = 0;
    PIDFController pid = new PIDFController(kP, kI, kD, kF);

    @Override
    public void runOpMode() throws InterruptedException {
        MultipleTelemetry telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), this.telemetry);

//        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
//        ServoEx servo = new SimpleServo(hardwareMap, "servo", 0, MAX_DEGREE);
//        CRServoImplEx servo = hardwareMap.get(CRServoImplEx.class, "crservo");
        AnalogInput input = hardwareMap.get(AnalogInput.class, "input");
        CRServo servo = new CRServo(hardwareMap, "crservo");


        waitForStart();

        while (opModeIsActive()) {
            servo.set(POSITION_0);
//            servo.turnToAngle(POSITION_0);
            telemetry.addData("voltage", input.getVoltage());
            telemetry.update();
        }

    }

}
