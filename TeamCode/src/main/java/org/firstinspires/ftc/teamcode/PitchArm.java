package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class PitchArm {

    //    private DcMotorEx slideMotor0;
//    private DcMotorEx slideMotor1;
    private ServoImplEx clawRotationServo;
    private ServoImplEx clawOuttakeServo;
    private ServoImplEx clawPitchServo;
    private ServoImplEx armPitchServo0;
    private ServoImplEx armPitchServo1;
    private Gamepads gamepads;

    public PitchArm(HardwareMap hardwareMap, Gamepads gamepads) {

//        this.slideMotor0 = hardwareMap.get(DcMotorEx.class, "slide0");
//        this.slideMotor1 = hardwareMap.get(DcMotorEx.class, "slide1");
        this.gamepads = gamepads;
        this.clawRotationServo = hardwareMap.get(ServoImplEx.class, "rotateClaw");
        this.clawOuttakeServo = hardwareMap.get(ServoImplEx.class, "clawOuttake");
        this.clawPitchServo = hardwareMap.get(ServoImplEx.class, "clawPitch");
        this.armPitchServo0 = hardwareMap.get(ServoImplEx.class, "clawPitch0");
        this.armPitchServo1 = hardwareMap.get(ServoImplEx.class, "clawPitch1");

        clawRotationServo.setPwmEnable();
        clawRotationServo.setPwmRange(new PwmControl.PwmRange(1000, 2000));

        clawOuttakeServo.setPwmEnable();
        clawOuttakeServo.setPwmRange(new PwmControl.PwmRange(1000, 2000));

        clawPitchServo.setPwmEnable();
        clawPitchServo.setPwmRange(new PwmControl.PwmRange(1000, 2000));

        armPitchServo0.setPwmEnable();
        armPitchServo0.setPwmRange(new PwmControl.PwmRange(1000, 2000));
        armPitchServo1.setPwmEnable();
        armPitchServo1.setPwmRange(new PwmControl.PwmRange(1000, 2000));
    }

    public void update() {
        updateArmPitch();
        updateClawOuttake();
        updateClawPitch();
        updateClawRotation();
    }

    private void updateArmPitch() {
        if (gamepads.currentGamepad2.right_trigger > 0) {
            armPitchServo0.setPosition(1);
            armPitchServo1.setPosition(1);
        } else {
            armPitchServo0.setPosition(0);
            armPitchServo1.setPosition(0);
        }
    }

    boolean clawOuttakeToggle = false;

    private void updateClawOuttake() {
        if (gamepads.currentGamepad2.a && !gamepads.previousGamepad2.a) {
            clawOuttakeToggle = !clawOuttakeToggle;

        }
        if (clawOuttakeToggle) {
            clawOuttakeServo.setPosition(1);
        } else {
            clawOuttakeServo.setPosition(0);
        }
    }

    boolean clawPitchToggle;
    private void updateClawPitch() {
        if (gamepads.currentGamepad2.b && !gamepads.previousGamepad2.b) {
            clawPitchToggle = !clawPitchToggle;

        }
        if (clawOuttakeToggle) {
            clawPitchServo.setPosition(1);
        } else {
            clawPitchServo.setPosition(0);
        }
    }

    boolean clawRotationToggle;
    private void updateClawRotation() {
        if (gamepads.currentGamepad2.x && !gamepads.previousGamepad2.x) {
            clawRotationToggle = !clawRotationToggle;

        }
        if (clawOuttakeToggle) {
            clawRotationServo.setPosition(1);
        } else {
            clawRotationServo.setPosition(0);
        }

    }


}
