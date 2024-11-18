package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

import static org.firstinspires.ftc.teamcode.main.MainDrive.*;


public class Robot {


    // void function that returns nothing
    private Runnable currentDriveMode;

    private double y; // Remember, Y stick value is reversed
    private double x; // Counteract imperfect strafing
    private double rx;
    private double botHeading;
    private double rotX;
    private double rotY;
    private double frontLeftPower;
    private double backLeftPower;
    private double frontRightPower;
    private double backRightPower;

    public DcMotorEx frontLeftMotor;
    public DcMotorEx frontRightMotor;
    public DcMotorEx backLeftMotor;
    public DcMotorEx backRightMotor;

    public IMU imu;

    private HorizontalArm horizontalArm;
    private VerticalArm verticalArm;
    private Gamepads gamepads;

    public Robot(HardwareMap hardwareMap, Gamepads gamepads) {

        this.gamepads = gamepads;

        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRight");

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.

        imu = hardwareMap.get(IMU.class, "imu");
        // this is the issue. the Rev Hub is not facing a normal direction
//        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);
        imu.resetYaw();

        // set motor braking
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // no encoders for pid and such
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // stop motors so they don't move
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);

        // reverse right motor direction bc its flipped
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        //
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        verticalArm = new VerticalArm(hardwareMap);
        horizontalArm = new HorizontalArm(hardwareMap);


        // https://www.youtube.com/watch?v=QEZO5e2zUcY

        // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric


        setDriveMode(this::fieldCentricDrive);
//        setDriveMode(this::normalDrive);


    }


    public void update() {
        // https://stackoverflow.com/questions/29945627/java-8-lambda-void-argument
        // might not work
//        updateMovement();

        // PID TURN
        if (gamepads.currentGamepad1.dpad_down) {
            referenceAngle -= Constants.ANGLE_SPEED;
        } else if (gamepads.currentGamepad1.dpad_up) {
            referenceAngle += Constants.ANGLE_SPEED;
        }

        // TRANSFER SEQUENCE
        if (gamepads.currentGamepad2.a && !gamepads.previousGamepad2.a) {
            performTransferSequence();
        }


//        double power = PIDControl(referenceAngle, imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle);
//        turn(power);
    }

    private void performTransferSequence() {
        ElapsedTime timer = new ElapsedTime();
        timer.reset();


        // make horizontal arm go up

        // make vertical arm go down
        // close vertical claw
        // open horizontal claw
        // make vertical arm go up
    }

    private void updateMovement() {
        currentDriveMode.run();
    }


    private void fieldCentricDrive() {
        y = -gamepads.currentGamepad1.left_stick_y; // Remember, Y stick value is reversed
        x = gamepads.currentGamepad1.left_stick_x; // Counteract imperfect strafing
        rx = gamepads.currentGamepad1.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepads.currentGamepad1.options) {
            imu.resetYaw();
        }
        globalTelemetry.addData("Current IMU Angle", imu.getRobotOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle);

        // get bot heading relative to control hub IMU
        botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        rotX = rotX * 1.1; // counteract imperfect strafing

        rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

        frontLeftPower = (rotY + rotX + rx) / denominator;
        backLeftPower = (rotY - rotX + rx) / denominator;
        frontRightPower = (rotY - rotX - rx) / denominator;
        backRightPower = (rotY + rotX - rx) / denominator;

        // Send calculated power to wheels
        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower(backLeftPower);
        backRightMotor.setPower(backRightPower);

        // Show the elapsed game time and wheel power.
        globalTelemetry.addData("Status", "Run Time: " + runtime.toString());
        globalTelemetry.addData("bot heading: ", botHeading);
        globalTelemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        globalTelemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        globalTelemetry.update();
    }

    private void normalDrive() {
        y = -gamepads.currentGamepad1.left_stick_y; // Remember, Y stick value is reversed
        x = gamepads.currentGamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        rx = gamepads.currentGamepad1.right_stick_x;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }

    public void turn(double power) {
        // negative because the front left and back left motors are in reverse
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
    }

    private void setDriveMode(Runnable runnable) {
        currentDriveMode = runnable;
    }

    // PID tutorial video: https://www.youtube.com/watch?v=TvyiyHF2tEM
    double integralSum = 0;
    private double Kp = Constants.Kp;
    private double Ki = Constants.Ki;
    private double Kd = Constants.Kd;
    private final ElapsedTime timer = new ElapsedTime();
    private double lastError = 0;
    // the angle that the robot is supposed to stay at
    public double referenceAngle = Math.toRadians(0);

    public double PIDControl(double reference, double state) {
        double error = angleWrap(reference - state);
        globalTelemetry.addData("Error: ", error);
        integralSum += error * timer.seconds();
        double derivative = (error - lastError) / (timer.seconds());
        lastError = error;
        timer.reset();
        double output = (error * Kp) + (derivative * Kd) + (integralSum * Ki);
        return output;
    }

    public double angleWrap(double radians) {
        while (radians > Math.PI) {
            radians -= 2 * Math.PI;
        }
        while (radians < -Math.PI) {
            radians += 2 * Math.PI;
        }
        return radians;
    }
}
