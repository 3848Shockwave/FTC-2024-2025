package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Constants;

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
    public ServoEx verticalClawGripServo;
    public ServoEx verticalClawRollServo;
    public ServoEx verticalClawPitchServo;
    public ServoEx verticalWristPitchServoL, verticalWristPitchServoR;

    public int initialTopMotorPosition;
    public int initialBottomMotorPosition;


    public enum IntakeState {
        INTAKE,
        TRANSFER,
        DEPOSIT

    }

    public IntakeState currentIntakeState = IntakeState.INTAKE;

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

        // vertical
        // arm
        verticalClawGripServo = new SimpleServo(hardwareMap, "vertClawGrip", 0, 180);
        verticalClawRollServo = new SimpleServo(hardwareMap, "vertClawPiv", 0, 180);
        verticalClawPitchServo = new SimpleServo(hardwareMap, "vertClawRot", 0, 180);
        verticalWristPitchServoL = new SimpleServo(hardwareMap, "vertArmRotL", 0, 180);
        verticalWristPitchServoR = new SimpleServo(hardwareMap, "vertArmRotR", 0, 180);

        // vertical slide motors
        verticalSlideMotorBottom = new MotorEx(hardwareMap, "spoolRight", Motor.GoBILDA.RPM_435);
        verticalSlideMotorBottom.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorBottom.setPositionCoefficient(0.1);
        verticalSlideMotorBottom.setPositionTolerance(10);
//        verticalSlideMotorBottom.setTargetPosition(5000);
//        verticalSlideMotorBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        verticalSlideMotorTop = new MotorEx(hardwareMap, "spoolLeft", Motor.GoBILDA.RPM_435);
        verticalSlideMotorTop.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorTop.setPositionCoefficient(0.1);
        verticalSlideMotorTop.setPositionTolerance(10);
//        verticalSlideMotorTop.setTargetPosition(5000);
        verticalSlideMotorTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        initialTopMotorPosition = verticalSlideMotorTop.getCurrentPosition();
        initialBottomMotorPosition = verticalSlideMotorBottom.getCurrentPosition();

    }

    // constantly updating
    @Override
    public void periodic() {

        telemetry.addData("claw grippy (periodic)", verticalClawGripServo.getPosition());
        telemetry.addData("top motor position: ", verticalSlideMotorTop.getCurrentPosition());
        telemetry.addData("bottom motor position: ", verticalSlideMotorBottom.getCurrentPosition());
    }

    public void setVerticalSlideMotorsTargetPosition(int targetPosition) {
        verticalSlideMotorTop.setTargetPosition(targetPosition);
        verticalSlideMotorBottom.setTargetPosition(-targetPosition);
    }

    public void setCurrentState(IntakeState intakeState) {
        currentIntakeState = intakeState;
    }


    // add a bajillion more methods here for the commands class
    public void closeClawManual(IntakeState intakeState) {
        if (intakeState == IntakeState.INTAKE) {
            closeHorizontalClaw();
        } else if (intakeState == IntakeState.DEPOSIT) {
            closeVerticalClaw();
        }
    }

    public void openClawManual(IntakeState intakeState) {
        if (intakeState == IntakeState.INTAKE) {
            openHorizontalClaw();
        } else if (intakeState == IntakeState.DEPOSIT) {
            openVerticalClaw();
        }
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
        verticalWristPitchServoL.turnToAngle(180 - degrees);
        verticalWristPitchServoR.turnToAngle(degrees);
    }


    public void setVerticalClawRollPosition(double degrees) {
        verticalClawRollServo.turnToAngle(degrees);
    }

    public void setVerticalClawPitchPosition(double degrees) {
        verticalClawPitchServo.turnToAngle(degrees);
    }
}


