package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.*;

// may or may not use, as it might not actually be needed
public class Hardware {
//    private HardwareMap hardwareMap;

    public static DcMotorEx frontLeftMotor;
    public static DcMotorEx frontRightMotor;
    public static DcMotorEx backLeftMotor;
    public static DcMotorEx backRightMotor;
    public static IMU imu;
    public static ServoImplEx clawServo;

    public static void init(HardwareMap hardwareMap) {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "frontLeft");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "frontRight");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "backLeft");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "backRight");

        imu = hardwareMap.get(IMU.class, "imu");

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

        clawServo = hardwareMap.get(ServoImplEx.class, "servo");


        // this is the issue. the Rev Hub is not facing a normal direction
//        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));
        imu.initialize(parameters);


    }
}