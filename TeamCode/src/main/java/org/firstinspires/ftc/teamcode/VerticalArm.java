package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;

@Config
public class VerticalArm extends Arm {
    private DcMotorEx slideMotorL;
    private DcMotorEx slideMotorR;

    public VerticalArm(HardwareMap hardwareMap) {
        slideMotorL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideMotorR = hardwareMap.get(DcMotorEx.class, "slideR");
    }

    public void setSlidePower(double power) {
        slideMotorL.setPower(-power);
        slideMotorR.setPower(power);
    }

}
