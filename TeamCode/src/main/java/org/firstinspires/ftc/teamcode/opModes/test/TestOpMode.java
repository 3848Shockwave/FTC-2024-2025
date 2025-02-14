package org.firstinspires.ftc.teamcode.opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
@TeleOp(name = "Test op mode")
public class TestOpMode extends LinearOpMode {

    // PID(F) declaration
    // kp = 0, ki = 0, kd = 0, kf = 0;
    public static double ANGLE_1 = 0;
    public static double ANGLE_2 = 180;

    @Override
    public void runOpMode() throws InterruptedException {
        MultipleTelemetry telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), this.telemetry);

        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);


        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.b) {
                intakeSubsystem.horizontalWristPitchServoL.turnToAngle(ANGLE_2);
            }
            if (gamepad1.x) {
                intakeSubsystem.horizontalWristPitchServoL.turnToAngle(ANGLE_1);
            }
            if (gamepad1.dpad_left) {
                intakeSubsystem.horizontalWristPitchServoR.turnToAngle(ANGLE_2);
            }
            if (gamepad1.dpad_right) {
                intakeSubsystem.horizontalWristPitchServoR.turnToAngle(ANGLE_1);
            }

            if (gamepad1.left_bumper){
                intakeSubsystem.horizontalWristPitchServoL.turnToAngle(180-ANGLE_1);
                intakeSubsystem.horizontalWristPitchServoR.turnToAngle(ANGLE_1);
            }
            if (gamepad1.right_bumper){
                intakeSubsystem.horizontalWristPitchServoL.turnToAngle(180-ANGLE_2);
                intakeSubsystem.horizontalWristPitchServoR.turnToAngle(ANGLE_2);
            }

//            if (gamepad1.x) intakeSubsystem.horizontalWristPitchServoL.turnToAngle(180 - ANGLE_1);
//            if (gamepad1.y) intakeSubsystem.horizontalWristPitchServoL.turnToAngle(180 - ANGLE_1);

            telemetry.addData("left servo angle: ", intakeSubsystem.horizontalWristPitchServoL.getAngle());
            telemetry.addData("right servo angle: ", intakeSubsystem.horizontalWristPitchServoR.getAngle());
            telemetry.addData("status: ", "running");

            telemetry.update();
        }

    }
}
