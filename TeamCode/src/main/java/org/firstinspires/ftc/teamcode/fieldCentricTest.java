package org.firstinspires.ftc.teamcode;





import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;


@TeleOp(name="fieldCentric", group="Linear Opmode")

public class fieldCentricTest extends LinearOpMode {

    // Declare OpMode members.

    private DcMotor frontRight;
    private DcMotor frontLeft;
    private DcMotor backRight;
    private DcMotor backLeft;
    private DcMotor armMotor;
    private double frontLeftPower;
    private double backLeftPower;
    private double frontRightPower;
    private double backRightPower;
    private Servo wrist;
    private Servo claw;
    private IMU emu;

    private double y; // Remember, Y stick value is reversed
    private double x; // Counteract imperfect strafing
    private double rx;
    private double botHeading;
    private double rotX;
    private double rotY;
    private boolean armUp;
    private boolean armDown;
    private double wristPosition1;
    private double wristPosition2;
    private double wristPosition3;
    private double intake;
    private double release;


    @Override
    public void runOpMode() {

        emu = hardwareMap.get(IMU.class, "imu");
        emu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.LEFT, RevHubOrientationOnRobot.UsbFacingDirection.UP)));


        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        wrist = hardwareMap.get(Servo.class, "wrist");
        claw = hardwareMap.get(Servo.class, "claw");
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        backRight.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        emu.resetYaw();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            x = gamepad1.left_stick_x; // Counteract imperfect strafing
            rx = - gamepad1.right_stick_x;

            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.a) {
                emu.resetYaw();
            }

            // get bot heading relative to control hub IMU
            botHeading = emu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

            // Rotate the movement direction counter to the bot's rotation
            rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            rotX = rotX * 1.1; // counteract imperfect strafing

            rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

            frontLeftPower = (rotY + rotX + rx) / denominator;
            backLeftPower = (rotY - rotX + rx) / denominator;
            frontRightPower = (rotY - rotX - rx) / denominator;
            backRightPower = (rotY + rotX - rx) / denominator;


            //code for arm
            armUp = gamepad1.left_bumper;
            armDown = gamepad1.right_bumper;

            if (armUp) {
                armMotor.setPower(0.5);
            } else if (armDown) {
                armMotor.setPower(-0.5);
            } else {
                armMotor.setPower(0);
            }


            //code for wrist
            wristPosition1 = 0;
            wristPosition2 = 0.5;
            wristPosition3 = 1.0;

            if (gamepad1.y){
                wrist.setPosition(wristPosition1);
            } else if (gamepad1.b){
                wrist.setPosition(wristPosition2);
            } else if (gamepad1.x){
                wrist.setPosition(wristPosition3);
            }

            //code for claw
            intake = gamepad1.left_trigger;
            release = gamepad1.right_trigger;

            if (intake > 0){
                claw.setPosition(0.5);
            } else if (release > 0){
                claw.setPosition(0.0);
            }



            // Send calculated power to wheels
            frontLeft.setPower(frontLeftPower);
            frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);

            telemetry.addData("orient", emu.getRobotOrientationAsQuaternion());
            telemetry.update();


        }

    }
}
