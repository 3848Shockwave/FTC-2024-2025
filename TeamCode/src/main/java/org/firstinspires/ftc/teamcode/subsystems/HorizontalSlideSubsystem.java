package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.constants.Constants;

import static org.firstinspires.ftc.teamcode.constants.ServoMaxes.AXON_SERVO_MAX_DEGREE;

public class HorizontalSlideSubsystem extends SubsystemBase {
    // first let's do horizontal
    private Telemetry telemetry;


    // horizontal components
    public ServoEx horizontalSlideServoL, horizontalSlideServoR;



    public HorizontalSlideSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
//        horizontalClawGripServo = robot.horizontalClawGripServo;
        this.telemetry = telemetry;
        // horizontal

        // slides
        horizontalSlideServoL = new SimpleServo(hardwareMap, "horzExtL", 0, AXON_SERVO_MAX_DEGREE);
        horizontalSlideServoR = new SimpleServo(hardwareMap, "horzExtR", 0, AXON_SERVO_MAX_DEGREE);
        horizontalSlideServoR.setInverted(true);

    }

    // constantly updating
    @Override
    public void periodic() {

    }

    public void setHorizontalSlidePosition(double degrees) {
        horizontalSlideServoL.turnToAngle(degrees);
        horizontalSlideServoR.turnToAngle(degrees);
    }


}


