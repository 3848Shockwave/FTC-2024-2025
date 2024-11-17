package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class Arm {

    private DcMotorEx motor0;
    private DcMotorEx motor1;
    public ServoImplEx clawServo;
    // angle in radians, horizontal is 0
    private double angle;

    public Arm(HardwareMap hardwareMap, Gamepads gamepads) {

        this.motor0 = hardwareMap.get(DcMotorEx.class, "motor0");
        this.motor1 = hardwareMap.get(DcMotorEx.class, "motor1");
        this.clawServo = hardwareMap.get(ServoImplEx.class, "servo");
    }

    public void update() {
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAngleRadians() {
        return angle;
    }


}
