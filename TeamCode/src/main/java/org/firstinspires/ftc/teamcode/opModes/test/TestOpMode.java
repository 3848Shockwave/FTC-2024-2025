package org.firstinspires.ftc.teamcode.opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
@TeleOp(name = "Test op mode")
public class TestOpMode extends LinearOpMode {

    // PID(F) declaration
    // kp = 0, ki = 0, kd = 0, kf = 0;
    public static int POSITION_0 = 0;
    public static int POSITION_1 = 1000;

    @Override
    public void runOpMode() throws InterruptedException {
        MultipleTelemetry telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), this.telemetry);

        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);


        waitForStart();

        while (opModeIsActive()) {
            intakeSubsystem.verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
            intakeSubsystem.verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);

            if (gamepad1.a) {
                intakeSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_0);
            }
            if (gamepad1.b) {
                intakeSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_0);
            }
            if (gamepad1.x) {
                intakeSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_0);
                intakeSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_0);
            }
            if (gamepad1.y) {
                intakeSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_1);
                intakeSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_1);
            }
            if (gamepad1.dpad_left) {
                intakeSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_1);
            }
            if (gamepad1.dpad_right) {
                intakeSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_1);
            }


            telemetry.addData("top motor position ", intakeSubsystem.verticalSlideMotorTop.getCurrentPosition());
            telemetry.addData("bottom motor position ", intakeSubsystem.verticalSlideMotorBottom.getCurrentPosition());
//
//        telemetry.addData("top motor at position", intakeSubsystem.verticalSlideMotorTop.atTargetPosition());
//        telemetry.addData("bottom motor at position", intakeSubsystem.verticalSlideMotorBottom.atTargetPosition());
//
//        telemetry.addData("target position ", getVerticalSlideMotorsTargetPosition());
//
            telemetry.addData("top motor current", intakeSubsystem.verticalSlideMotorTop.motorEx.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("bottom motor current", intakeSubsystem.verticalSlideMotorBottom.motorEx.getCurrent(CurrentUnit.AMPS));
//
            telemetry.addData("Top motor power", intakeSubsystem.verticalSlideMotorTop.get());
            telemetry.addData("Bottom motor negative power", -intakeSubsystem.verticalSlideMotorBottom.get());
            telemetry.addData("status: ", "running");

            telemetry.update();
        }

    }
}
