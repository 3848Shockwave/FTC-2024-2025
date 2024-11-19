package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.main.HorizontalArm;
import org.firstinspires.ftc.teamcode.main.VerticalArm;

public class IntakeSubsystem extends SubsystemBase {
    // first let's do horizontal
    private Telemetry telemetry;
    public State currentState;

    private Robot robot;

    public enum Position {
        INTAKE,
        TRANSFER,
        OUTTAKE
    }

    public enum State {
        INTAKING,
        TRANSFERRING,
        OUTTAKING

    }

    public IntakeSubsystem(Robot robot, Telemetry telemetry) {
//        horizontalClawGripServo = robot.horizontalClawGripServo;
        this.robot = robot;
        this.telemetry = telemetry;
    }

    // constantly updating
    @Override
    public void periodic() {
        telemetry.addData("claw grippy (periodic)", robot.horizontalClawGripServo.getPosition());
        telemetry.update();
    }

    // add a bajillion more methods here for the commands class
    public void setClawPosition(double position) {
        switch (currentState) {
            case INTAKING:
                robot.horizontalClawGripServo.setPosition(position);
                break;
            case OUTTAKING:
                robot.verticalClawGripServo.setPosition(position);
                break;
        }
    }
}


