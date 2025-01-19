package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;

public class IntakeSubsystem extends SubsystemBase {
    // first let's do horizontal
    private Telemetry telemetry;


    // horizontal components
    public ServoEx horizontalSlideServoL, horizontalSlideServoR;
    public ServoEx horizontalClawGripServo;
    public ServoEx horizontalClawRollServo;
    public ServoEx horizontalClawPitchServo;
    public ServoEx horizontalWristPitchServoL, horizontalWristPitchServoR;

    // vertical components
    public MotorEx verticalSlideMotorTop, verticalSlideMotorBottom;
    private static int verticalSlideMotorsTargetPosition = 0;
    public ServoEx verticalClawGripServo;
    public ServoEx verticalClawRollServo;
    public ServoEx verticalClawPitchServo;
    public ServoImplEx verticalWristPitchServoL, verticalWristPitchServoR;

    public int initialTopMotorPosition, initialBottomMotorPosition;


    public enum IntakeState {
        INTAKE,
        TRANSFER,
        DEPOSIT,
        HOVER_OVER_SAMPLE,
        VERTICAL


    }

    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
//        horizontalClawGripServo = robot.horizontalClawGripServo;
        this.telemetry = telemetry;
        // horizontal
        // arm
        horizontalClawGripServo = new SimpleServo(hardwareMap, "horzClawGrip", 0, 180);
        horizontalClawRollServo = new SimpleServo(hardwareMap, "horzClawPiv", 0, 180);
        horizontalClawPitchServo = new SimpleServo(hardwareMap, "horzClawRot", 0, 180);
        horizontalWristPitchServoL = new SimpleServo(hardwareMap, "horzArmRotL", 0, 180);
        horizontalWristPitchServoR = new SimpleServo(hardwareMap, "horzArmRotR", 0, 180);

        // slides
        horizontalSlideServoL = new SimpleServo(hardwareMap, "horzExtL", 0, 180);
        horizontalSlideServoR = new SimpleServo(hardwareMap, "horzExtR", 0, 180);
        horizontalSlideServoL.setInverted(true);
        horizontalSlideServoR.setInverted(true);

        // vertical
        // arm
        verticalClawGripServo = new SimpleServo(hardwareMap, "vertClawGrip", 0, 180);
        verticalClawRollServo = new SimpleServo(hardwareMap, "vertClawPiv", 0, 180);
        verticalClawPitchServo = new SimpleServo(hardwareMap, "vertClawRot", 0, 180);

        verticalWristPitchServoL = hardwareMap.get(ServoImplEx.class, "vertArmRotL");
        verticalWristPitchServoR = hardwareMap.get(ServoImplEx.class, "vertArmRotR");
        verticalWristPitchServoL.setPwmEnable();
        verticalWristPitchServoL.setPwmRange(new PwmControl.PwmRange(500, 3000));
        verticalWristPitchServoR.setPwmEnable();
        verticalWristPitchServoR.setPwmRange(new PwmControl.PwmRange(500, 3000));

        // vertical slide motors
        verticalSlideMotorBottom = new MotorEx(hardwareMap, "spoolRight", Motor.GoBILDA.RPM_1150);
        verticalSlideMotorBottom.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorBottom.setPositionCoefficient(Constants.VERTICAL_SLIDE_MOTOR_POSITION_COEFFICIENT);
        verticalSlideMotorBottom.setPositionTolerance(Constants.VERTICAL_SLIDE_MOTOR_POSITION_TOLERANCE);
//        verticalSlideMotorBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        verticalSlideMotorTop = new MotorEx(hardwareMap, "spoolLeft", Motor.GoBILDA.RPM_1150);
        verticalSlideMotorTop.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorTop.setPositionCoefficient(Constants.VERTICAL_SLIDE_MOTOR_POSITION_COEFFICIENT);
        verticalSlideMotorTop.setPositionTolerance(Constants.VERTICAL_SLIDE_MOTOR_POSITION_TOLERANCE);
//        verticalSlideMotorTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);


        initialTopMotorPosition = verticalSlideMotorTop.getCurrentPosition();
        initialBottomMotorPosition = verticalSlideMotorBottom.getCurrentPosition();

    }

    // constantly updating
    @Override
    public void periodic() {

        // always give motors power
        verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
        verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);

        telemetry.addData("top motor position: ", verticalSlideMotorTop.getCurrentPosition());
        telemetry.addData("top motor at position:", verticalSlideMotorTop.atTargetPosition());

        telemetry.addData("bottom motor position: ", verticalSlideMotorBottom.getCurrentPosition());
        telemetry.addData("bottom motor at position:", verticalSlideMotorBottom.atTargetPosition());
        telemetry.addData("target position: ", getVerticalSlideMotorsTargetPosition());

        telemetry.addData("top motor current", verticalSlideMotorTop.motorEx.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("bottom motor current", verticalSlideMotorBottom.motorEx.getCurrent(CurrentUnit.AMPS));
//        telemetry.addData("horizontal wrist pitch left servo position:", horizontalWristPitchServoL.getPosition());
//        telemetry.addData("horizontal wrist pitch left servo position:", horizontalWristPitchServoL.getPosition());
    }

    public void setVerticalSlideMotorsTargetPosition(int targetPosition) {
        verticalSlideMotorsTargetPosition = targetPosition;
        verticalSlideMotorTop.setTargetPosition(targetPosition);
        verticalSlideMotorBottom.setTargetPosition(-targetPosition);
    }

    public int getVerticalSlideMotorsTargetPosition() {
        return verticalSlideMotorsTargetPosition;
    }


    public void openVerticalClaw() {
        verticalClawGripServo.turnToAngle(Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION, AngleUnit.DEGREES);
    }

    public void closeVerticalClaw() {
        verticalClawGripServo.turnToAngle(Constants.VERTICAL_CLAW_GRIP_CLOSED_POSITION, AngleUnit.DEGREES);
    }

    public void openHorizontalClaw() {
        horizontalClawGripServo.turnToAngle(Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION, AngleUnit.DEGREES);
    }

    public void closeHorizontalClaw() {
        horizontalClawGripServo.turnToAngle(Constants.HORIZONTAL_CLAW_GRIP_CLOSED_POSITION, AngleUnit.DEGREES);
    }


    public void setHorizontalWristPitchPosition(double degrees) {
        horizontalWristPitchServoL.turnToAngle(180 - degrees);
        horizontalWristPitchServoR.turnToAngle(degrees);
    }

    public void setHorizontalSlidePosition(double degrees) {
        horizontalSlideServoL.turnToAngle(180 - degrees);
        horizontalSlideServoR.turnToAngle(degrees);
    }


    public void setHorizontalClawRollPosition(double degrees) {
        horizontalClawRollServo.turnToAngle(degrees);
    }

    public void setHorizontalClawPitchPosition(double degrees) {
        horizontalClawPitchServo.turnToAngle(degrees);
    }

    public void setVerticalWristPitchPosition(double degrees) {
        double position = degrees / 180;
//        verticalWristPitchServoL.turnToAngle(180 - degrees);
//        verticalWristPitchServoR.turnToAngle(degrees);
        verticalWristPitchServoL.setPosition(1 - position);
        verticalWristPitchServoR.setPosition(position);
    }


    public void setVerticalClawRollPosition(double degrees) {
        verticalClawRollServo.turnToAngle(degrees);
    }

    public void setVerticalClawPitchPosition(double degrees) {
        verticalClawPitchServo.turnToAngle(degrees);
    }
}


