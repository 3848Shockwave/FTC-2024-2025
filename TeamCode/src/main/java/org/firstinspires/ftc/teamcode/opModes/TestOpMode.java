package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp(name = "Test op mode")
public class TestOpMode extends LinearOpMode {

    // PID(F) declaration
    // kp = 0, ki = 0, kd = 0, kf = 0;
    public static double KP = 0.1;
    public static double KI = 0;
    public static double KD = 0;
    public static double KF = 0;
    private PIDFController examplePIDF = new PIDFController(KP, KI, KD, KF);

    @Override
    public void runOpMode() throws InterruptedException {
        // https://cookbook.dairy.foundation/pidf_controllers/syncing_two_linear_slide_motors_using_a_pidf_controller/syncing_two_linear_slide_motors_using_a_pidf_controller.html
        int targetPosition = 0;
        MultipleTelemetry telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), this.telemetry);

        DcMotorEx verticalSlideMotorBottom = hardwareMap.get(DcMotorEx.class, "spoolRight");
        verticalSlideMotorBottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        verticalSlideMotorBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        DcMotorEx verticalSlideMotorTop = hardwareMap.get(DcMotorEx.class, "spoolLeft");
        verticalSlideMotorTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        verticalSlideMotorTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        resetRuntime();

        while (opModeIsActive()) {
            if (gamepad1.a) targetPosition = 3000;
            if (gamepad1.b) targetPosition = 1000;

                        /*
            Calculates PID based only on one encoder.
            This can also be the average position of the two linear slides, but we haven't noticed much difference
            */
            double power = examplePIDF.calculate(verticalSlideMotorTop.getCurrentPosition(), targetPosition);

            // see how both motors are getting the same power
            verticalSlideMotorBottom.setPower(-power);
            verticalSlideMotorTop.setPower(power);

            telemetry.addData("target position: ", targetPosition);
            telemetry.addData("top motor position: ", verticalSlideMotorTop.getCurrentPosition());
            telemetry.addData("bottom motor position: ", verticalSlideMotorBottom.getCurrentPosition());
            telemetry.update();
        }

    }
}
