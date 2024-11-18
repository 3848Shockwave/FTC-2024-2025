package org.firstinspires.ftc.teamcode.main;

import com.qualcomm.robotcore.hardware.ServoImplEx;

public class Arm {
    protected ServoImplEx clawRollServo;
    protected ServoImplEx clawPitchServo;
    protected ServoImplEx clawGripServo;
    protected ServoImplEx wristPitchServoL;
    protected ServoImplEx wristPitchServoR;

    public void setWristPitchPosition(double position) {
        wristPitchServoL.setPosition(-position);
        wristPitchServoR.setPosition(position);
    }
}

