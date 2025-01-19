package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
@TeleOp(name = "Test op mode")
public class TestOpMode extends LinearOpMode {

    // PID(F) declaration
    // kp = 0, ki = 0, kd = 0, kf = 0;
    public static double KP = 0.003;
    public static double KI = 0;
    public static double KD = 0;
    public static double KF = 0;
    public static int bottomPosition = 0;
    public static int topPosition = 2000;
    private PIDFController examplePIDF = new PIDFController(KP, KI, KD, KF);

    @Override
    public void runOpMode() throws InterruptedException {
        // https://cookbook.dairy.foundation/pidf_controllers/syncing_two_linear_slide_motors_using_a_pidf_controller/syncing_two_linear_slide_motors_using_a_pidf_controller.html
        int targetPosition = 0;
        MultipleTelemetry telemetry = new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), this.telemetry);

        IntakeSubsystem intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);

        DcMotorEx verticalSlideMotorBottom = hardwareMap.get(DcMotorEx.class, "spoolRight");
        verticalSlideMotorBottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        verticalSlideMotorBottom.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        DcMotorEx verticalSlideMotorTop = hardwareMap.get(DcMotorEx.class, "spoolLeft");
        verticalSlideMotorTop.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        verticalSlideMotorTop.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();
        resetRuntime();

        while (opModeIsActive()) {

            if (gamepad1.a) intakeSubsystem.horizontalClawPitchServo.turnToAngle(Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION);
            if (gamepad1.b) intakeSubsystem.horizontalClawPitchServo.turnToAngle(Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION);


//                        /*
//            Calculates PID based only on one encoder.
//            This can also be the average position of the two linear slides, but we haven't noticed much difference
//            */
//            double power = examplePIDF.calculate(verticalSlideMotorTop.getCurrentPosition(), targetPosition);
//
//            // see how both motors are getting the same power
//            verticalSlideMotorBottom.setPower(-power);
//            verticalSlideMotorTop.setPower(power);

            telemetry.addData("target position: ", targetPosition);
            telemetry.addData("top motor position: ", verticalSlideMotorTop.getCurrentPosition());
            telemetry.addData("bottom motor position: ", verticalSlideMotorBottom.getCurrentPosition());
            telemetry.addData("top motor current: ", verticalSlideMotorTop.getCurrent(CurrentUnit.AMPS));
            telemetry.addData("bottom motor current: ", verticalSlideMotorBottom.getCurrent(CurrentUnit.AMPS));
            telemetry.update();
        }

    }
}
