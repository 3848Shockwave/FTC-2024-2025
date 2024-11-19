package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {
    public static double Kp = 1.8; // proportional gain
    public static double Ki = 0; // integral gain
    public static double Kd = 0.031; // derivative gain

    public static double UP = 1;
    public static double DOWN = 0;
    public static double PWM_LOW = 1700;
    public static double PWM_HIGH = 2000;
    public static double ANGLE_SPEED = 0.001;




    public static double clawGripClosedPosition = 0;
    public static double clawGripOpenPosition = 1;
}
