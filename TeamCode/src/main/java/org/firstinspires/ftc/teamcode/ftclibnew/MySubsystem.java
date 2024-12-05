package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.arcrobotics.ftclib.hardware.SimpleServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MySubsystem extends SubsystemBase {
    ServoEx servo;

    public MySubsystem(HardwareMap hardwareMap) {
        servo = new SimpleServo(hardwareMap, "servo", 0, 180);

        // initialize hardware here
    }

    public void setServoPosition(double position) {
        servo.setPosition(position);
    }

    @Override
    public void periodic() {
        // update hardware here
    }
}
