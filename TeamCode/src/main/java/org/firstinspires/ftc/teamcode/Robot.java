package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {

    private static Robot robot;
    public Motor frontLeftMotor, frontRightMotor, backLeftMotor, backRightMotor;

    public Motor verticalSlideMotorTop, verticalSlideMotorBottom;
    public ServoEx horizontalSlideServoL;
    public ServoEx horizontalSlideServoR;
    public ServoEx horizontalClawGripServo;
    public ServoEx horizontalClawRollServo;
    public ServoEx horizontalClawPitchServo;
    public ServoEx horizontalWristPitchServoL;
    public ServoEx horizontalWristPitchServoR;

    public ServoEx verticalClawGripServo;
    public void init(HardwareMap hardwareMap) {
        horizontalClawGripServo = new SimpleServo(hardwareMap, "horzClawGrip", 0, 180);

        frontLeftMotor = new Motor(hardwareMap, "frontLeft");
        frontRightMotor = new Motor(hardwareMap, "frontRight");
        backLeftMotor = new Motor(hardwareMap, "backLeft");
        backRightMotor = new Motor(hardwareMap, "backRight");

        horizontalClawRollServo = new SimpleServo(hardwareMap, "horzClawPiv", 0, 180);
        horizontalClawPitchServo = new SimpleServo(hardwareMap, "horzClawRot", 0, 180);
        horizontalWristPitchServoL = new SimpleServo(hardwareMap, "horzArmRotL", 0, 180);
        horizontalWristPitchServoR = new SimpleServo(hardwareMap, "horzArmRotR", 0, 180);

        horizontalSlideServoL = new SimpleServo(hardwareMap, "horzExtL", 0, 180);
        horizontalSlideServoR = new SimpleServo(hardwareMap, "horzExtL", 0, 180);

        verticalClawGripServo = new SimpleServo(hardwareMap, "vertClawGrip", 0, 180);

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
