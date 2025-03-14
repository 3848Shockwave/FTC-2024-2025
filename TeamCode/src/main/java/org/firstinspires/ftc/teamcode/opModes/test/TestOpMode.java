package org.firstinspires.ftc.teamcode.opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

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

        VerticalSlideSubsystem verticalSlideSubsystem = new VerticalSlideSubsystem(hardwareMap, telemetry);


        waitForStart();

        while (opModeIsActive()) {
            verticalSlideSubsystem.verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
            verticalSlideSubsystem.verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);

            if (gamepad1.a) {
                verticalSlideSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_0);
            }
            if (gamepad1.b) {
                verticalSlideSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_0);
            }
            if (gamepad1.x) {
                verticalSlideSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_0);
                verticalSlideSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_0);
            }
            if (gamepad1.y) {
                verticalSlideSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_1);
                verticalSlideSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_1);
            }
            if (gamepad1.dpad_left) {
                verticalSlideSubsystem.verticalSlideMotorTop.setTargetPosition(POSITION_1);
            }
            if (gamepad1.dpad_right) {
                verticalSlideSubsystem.verticalSlideMotorBottom.setTargetPosition(-POSITION_1);
            }


            telemetry.addData("top motor position ", verticalSlideSubsystem.verticalSlideMotorTop.getCurrentPosition());
            telemetry.addData("bottom motor position ", verticalSlideSubsystem.verticalSlideMotorBottom.getCurrentPosition());
//
//        telemetry.addData("top motor at position", intakeSubsystem.verticalSlideMotorTop.atTargetPosition());
//        telemetry.addData("bottom motor at position", intakeSubsystem.verticalSlideMotorBottom.atTargetPosition());
//
//        telemetry.addData("target position ", getVerticalSlideMotorsTargetPosition());
//
            telemetry.addData("top motor current", verticalSlideSubsystem.verticalSlideMotorTop.motorEx.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("bottom motor current", verticalSlideSubsystem.verticalSlideMotorBottom.motorEx.getCurrent(CurrentUnit.AMPS));
//
            telemetry.addData("Top motor power", verticalSlideSubsystem.verticalSlideMotorTop.get());
            telemetry.addData("Bottom motor negative power", -verticalSlideSubsystem.verticalSlideMotorBottom.get());
            telemetry.addData("status: ", "running");

            telemetry.update();
        }

    }
}
