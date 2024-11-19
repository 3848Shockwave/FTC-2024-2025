package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    private static Robot robot = new Robot();
    public Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    // horizontal components
    public ServoEx horizontalSlideServoL, horizontalSlideServoR;
    public ServoEx horizontalClawGripServo;
    public ServoEx horizontalClawRollServo;
    public ServoEx horizontalClawPitchServo;
    public ServoEx horizontalWristPitchServoL, horizontalWristPitchServoR;

    // vertical components
    public Motor verticalSlideMotorTop, verticalSlideMotorBottom;
    public ServoEx verticalClawGripServo;
    public ServoEx verticalClawRollServo;
    public ServoEx verticalClawPitchServo;
    public ServoEx verticalWristPitchServoL, verticalWristPitchServoR;
    public RevIMU imu;


    public void init(HardwareMap hardwareMap) {

        imu = new RevIMU(hardwareMap, "imu");

        frontLeftMotor = new Motor(hardwareMap, "frontLeft");
        frontRightMotor = new Motor(hardwareMap, "frontRight");
        backLeftMotor = new Motor(hardwareMap, "backLeft");
        backRightMotor = new Motor(hardwareMap, "backRight");

        // horizontal
        // arm
        horizontalClawGripServo = new SimpleServo(hardwareMap, "horzClawGrip", 0, 180);
        horizontalClawRollServo = new SimpleServo(hardwareMap, "horzClawPiv", 0, 180);
        horizontalClawPitchServo = new SimpleServo(hardwareMap, "horzClawRot", 0, 180);
        horizontalWristPitchServoL = new SimpleServo(hardwareMap, "horzArmRotL", 0, 180);
        horizontalWristPitchServoR = new SimpleServo(hardwareMap, "horzArmRotR", 0, 180);

        // slides
        horizontalSlideServoL = new SimpleServo(hardwareMap, "horzExtL", 0, 180);
        horizontalSlideServoR = new SimpleServo(hardwareMap, "horzExtL", 0, 180);

        // vertical
        // arm
        verticalClawGripServo = new SimpleServo(hardwareMap, "vertClawGrip", 0, 180);
        verticalClawRollServo = new SimpleServo(hardwareMap, "vertClawPiv", 0, 180);
        verticalClawPitchServo = new SimpleServo(hardwareMap, "vertClawRot", 0, 180);
        verticalWristPitchServoL = new SimpleServo(hardwareMap, "vertArmRotL", 0, 180);
        verticalWristPitchServoR = new SimpleServo(hardwareMap, "vertArmRotR", 0, 180);

        // vertical slide motors
        verticalSlideMotorBottom = new Motor(hardwareMap, "spoolRight");
        verticalSlideMotorBottom.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorBottom.setPositionCoefficient(0.1);
        verticalSlideMotorBottom.setPositionTolerance(10);
        verticalSlideMotorBottom.setTargetPosition(5000);
        verticalSlideMotorBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        verticalSlideMotorTop = new Motor(hardwareMap, "spoolLeft");
        verticalSlideMotorTop.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorTop.setPositionCoefficient(0.1);
        verticalSlideMotorTop.setPositionTolerance(10);
        verticalSlideMotorTop.setTargetPosition(5000);
        verticalSlideMotorTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

    }

    public static Robot getRobot() {
        if (robot == null) {
            robot = new Robot();
        }
        return robot;
    }

}
