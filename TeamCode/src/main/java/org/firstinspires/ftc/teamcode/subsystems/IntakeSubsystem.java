package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;

public class IntakeSubsystem extends SubsystemBase {
    // first let's do horizontal
    private Telemetry telemetry;


    // horizontal components
    public ServoEx horizontalSlideServoL, horizontalSlideServoR;
    public static double currentHorizontalSlidePosition = Constants.HORIZONTAL_SLIDE_MIN_EXTENSION;
    public ServoEx horizontalClawGripServo;
    public ServoEx horizontalClawRollServo;
    public ServoEx horizontalClawPitchServo;
    public ServoEx horizontalWristPitchServoL, horizontalWristPitchServoR;

    // vertical components
    public MotorEx verticalSlideMotorTop, verticalSlideMotorBottom;
    public ServoEx verticalClawGripServo;
    public ServoEx verticalClawRollServo;
    public ServoEx verticalClawPitchServo;
    //    public ServoEx verticalWristPitchServoL, verticalWristPitchServoR;
    public ServoImplEx verticalWristPitchServoL, verticalWristPitchServoR;

    public int initialTopMotorPosition, initialBottomMotorPosition;


    public enum IntakeState {
        INTAKE,
        TRANSFER,
        DEPOSIT,
        HOVER_OVER_SAMPLE,
        VERTICAL


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

//        verticalWristPitchServoL = new SimpleServo(hardwareMap, "vertArmRotL", 0, 180);
//        verticalWristPitchServoR = new SimpleServo(hardwareMap, "vertArmRotR", 0, 180);

        verticalWristPitchServoL = hardwareMap.get(ServoImplEx.class, "vertArmRotL");
        verticalWristPitchServoR = hardwareMap.get(ServoImplEx.class, "vertArmRotR");
        verticalWristPitchServoL.setPwmEnable();
        verticalWristPitchServoL.setPwmRange(new PwmControl.PwmRange(500, 3000));
        verticalWristPitchServoR.setPwmEnable();
        verticalWristPitchServoR.setPwmRange(new PwmControl.PwmRange(500, 3000));

        // vertical slide motors
        verticalSlideMotorBottom = new MotorEx(hardwareMap, "spoolRight", Motor.GoBILDA.RPM_435);
        verticalSlideMotorBottom.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorBottom.setPositionCoefficient(Constants.MOTOR_POSITION_COEFFICIENT);
        verticalSlideMotorBottom.setPositionTolerance(Constants.MOTOR_POSITION_TOLERANCE);
//        verticalSlideMotorBottom.setTargetPosition(5000);
//        verticalSlideMotorBottom.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        verticalSlideMotorTop = new MotorEx(hardwareMap, "spoolLeft", Motor.GoBILDA.RPM_435);
        verticalSlideMotorTop.setRunMode(Motor.RunMode.PositionControl);
        verticalSlideMotorTop.setPositionCoefficient(Constants.MOTOR_POSITION_COEFFICIENT);
        verticalSlideMotorTop.setPositionTolerance(Constants.MOTOR_POSITION_TOLERANCE);
//        verticalSlideMotorTop.setTargetPosition(5000);
        verticalSlideMotorTop.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);

        initialTopMotorPosition = verticalSlideMotorTop.getCurrentPosition();
        initialBottomMotorPosition = verticalSlideMotorBottom.getCurrentPosition();

    }

    // constantly updating
    @Override
    public void periodic() {

//        telemetry.addData("top motor position: ", verticalSlideMotorTop.getCurrentPosition());
//        telemetry.addData("bottom motor position: ", verticalSlideMotorBottom.getCurrentPosition());
//        telemetry.addData("horizontal wrist pitch left servo position:", horizontalWristPitchServoL.getPosition());
//        telemetry.addData("horizontal wrist pitch left servo position:", horizontalWristPitchServoL.getPosition());
//        telemetry.addData("current horizontal slide left servo position: ", horizontalSlideServoL.getAngle());
//        telemetry.addData("current horizontal slide right servo position: ", horizontalSlideServoR.getAngle());
//        telemetry.addData("current horizontal slide position variable value: ", currentHorizontalSlidePosition);
    }

    public void setVerticalSlideMotorsTargetPosition(int targetPosition) {
        verticalSlideMotorTop.setTargetPosition(targetPosition);
        verticalSlideMotorBottom.setTargetPosition(-targetPosition);
    }

    public void setVerticalSlideMotorsVelocity(double velocity) {
        verticalSlideMotorTop.setVelocity(velocity);
        verticalSlideMotorBottom.setVelocity(-velocity);
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
        currentHorizontalSlidePosition = degrees;
    }

    public void moveHorizontalSlide(double speed) {
        currentHorizontalSlidePosition += speed;

        // clamp the position to 0 and 180
        if (currentHorizontalSlidePosition > Constants.HORIZONTAL_SLIDE_MAX_EXTENSION) {
            currentHorizontalSlidePosition = Constants.HORIZONTAL_SLIDE_MAX_EXTENSION;
        } else if (currentHorizontalSlidePosition < Constants.HORIZONTAL_SLIDE_MIN_EXTENSION) {
            currentHorizontalSlidePosition = Constants.HORIZONTAL_SLIDE_MIN_EXTENSION;
        }

        horizontalSlideServoL.turnToAngle(180 - currentHorizontalSlidePosition);
        horizontalSlideServoR.turnToAngle(currentHorizontalSlidePosition);
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


