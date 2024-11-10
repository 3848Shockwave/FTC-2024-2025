package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static org.firstinspires.ftc.teamcode.MainDrive.*;


public class Robot {

//    private enum DriveMode {
//        FIELD_CENTRIC, NORMAL
//    }

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

    private Claw claw;

    public Robot() {

        // https://www.youtube.com/watch?v=QEZO5e2zUcY

        // https://gm0.org/en/latest/docs/software/tutorials/mecanum-drive.html#field-centric
//        driveMode = DriveMode.FIELD_CENTRIC;

        setDriveMode(this::updateMovementFieldCentric);

        Hardware.imu.resetYaw();

    }

    public void update() {
        // https://stackoverflow.com/questions/29945627/java-8-lambda-void-argument
        // might not work
        currentDriveMode.run();
//        updateMovementFieldCentric();
        updateClaw();
    }

    private void updateClaw() {
        claw.update();
    }

    private void updateMovementNormal() {

    }


    private void updateMovementFieldCentric() {
        y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
        x = gamepad1.left_stick_x; // Counteract imperfect strafing
        rx = gamepad1.right_stick_x;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.
        if (gamepad1.options) {
            Hardware.imu.resetYaw();
        }


        // get bot heading relative to control hub IMU
        botHeading = Hardware.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

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
        Hardware.frontLeftMotor.setPower(frontLeftPower);
        Hardware.frontRightMotor.setPower(frontRightPower);
        Hardware.backLeftMotor.setPower(backLeftPower);
        Hardware.backRightMotor.setPower(backRightPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("bot heading: ", botHeading);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", frontLeftPower, frontRightPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", backLeftPower, backRightPower);
        telemetry.update();
    }

    private void setDriveMode(Runnable runnable) {
        currentDriveMode = runnable;
    }
}
