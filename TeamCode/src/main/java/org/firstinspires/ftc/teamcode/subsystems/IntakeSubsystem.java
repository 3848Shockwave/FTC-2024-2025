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

    // vertical components
    public MotorEx verticalSlideMotorTop, verticalSlideMotorBottom;
    private static int verticalSlideMotorsTargetPosition = 0;


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

        // slides
        horizontalSlideServoL = new SimpleServo(hardwareMap, "horzExtL", 0, 180);
        horizontalSlideServoR = new SimpleServo(hardwareMap, "horzExtR", 0, 180);
        horizontalSlideServoL.setInverted(true);
        horizontalSlideServoR.setInverted(true);

        // vertical

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

    }

    // constantly updating
    @Override
    public void periodic() {

        // always give motors power
        verticalSlideMotorTop.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);
        verticalSlideMotorBottom.set(Constants.VERTICAL_SLIDE_MOTOR_SPEED_FAST);


        telemetry.addData("top motor position ", verticalSlideMotorTop.getCurrentPosition());
        telemetry.addData("bottom motor position ", verticalSlideMotorBottom.getCurrentPosition());
//
//        telemetry.addData("top motor at position", verticalSlideMotorTop.atTargetPosition());
//        telemetry.addData("bottom motor at position", verticalSlideMotorBottom.atTargetPosition());
//
//        telemetry.addData("target position ", getVerticalSlideMotorsTargetPosition());
//
        telemetry.addData("top motor current", verticalSlideMotorTop.motorEx.getCurrent(CurrentUnit.AMPS));
        telemetry.addData("bottom motor current", verticalSlideMotorBottom.motorEx.getCurrent(CurrentUnit.AMPS));
//
        telemetry.addData("Top motor power", verticalSlideMotorTop.get());
        telemetry.addData("Bottom motor negative power", -verticalSlideMotorBottom.get());

    }

    public void setVerticalSlideMotorsTargetPosition(int targetPosition) {
        verticalSlideMotorsTargetPosition = targetPosition;
        verticalSlideMotorTop.setTargetPosition(targetPosition);
        verticalSlideMotorBottom.setTargetPosition(-targetPosition);
    }

    public int getVerticalSlideMotorsTargetPosition() {
        return verticalSlideMotorsTargetPosition;
    }


    public void setHorizontalSlidePosition(double degrees) {
        horizontalSlideServoL.turnToAngle(180 - degrees);
        horizontalSlideServoR.turnToAngle(degrees);
    }


}


