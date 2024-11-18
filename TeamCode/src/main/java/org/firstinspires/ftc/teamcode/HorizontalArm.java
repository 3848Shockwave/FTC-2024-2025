package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@Config
public class HorizontalArm extends Arm{
    private ServoImplEx slideServoL;
    private ServoImplEx slideServoR;
    public static double clawRollIntakePosition = 0;
    public static double clawRollTransferPosition = 0;
    public static double clawPitchIntakePosition = 0;
    public static double clawPitchTransferPosition = 0;
    public static double wristPitchIntakePosition = 0;
    public static double wristPitchTransferPosition = 0;

    public HorizontalArm(HardwareMap hardwareMap) {
        clawRollServo = hardwareMap.get(ServoImplEx.class, "clawRoll");
        clawPitchServo = hardwareMap.get(ServoImplEx.class, "clawPitch");
        clawGripServo = hardwareMap.get(ServoImplEx.class, "clawGrip");
        wristPitchServoL = hardwareMap.get(ServoImplEx.class, "wristPitchL");
        wristPitchServoR = hardwareMap.get(ServoImplEx.class, "wristPitchR");
    }

    public void goToIntake() {
        clawRollServo.setPosition(clawRollIntakePosition);
        clawPitchServo.setPosition(clawPitchIntakePosition);
//      check this V
        wristPitchServoL.setPosition(-wristPitchIntakePosition);
        wristPitchServoR.setPosition(wristPitchIntakePosition);
    }

    public void goToTransfer() {
        clawRollServo.setPosition(clawRollTransferPosition);
        clawPitchServo.setPosition(clawPitchTransferPosition);
//      check this V
        wristPitchServoL.setPosition(-wristPitchTransferPosition);
        wristPitchServoR.setPosition(wristPitchTransferPosition);
    }

}
