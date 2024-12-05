package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {

    public static int TEST_INT0 = 3000;
    public static int TEST_INT1 = 2000;
    public static double TEST_DOUBLE = 1;
    public static boolean IS_FIELD_CENTRIC = true;

    // horizontal or vertical doesn't matter for claw grip
    public static double CLAW_GRIP_OPEN_POSITION = 75;
    public static double CLAW_GRIP_CLOSED_POSITION = 130;

    // horizontal
    public static double HORIZONTAL_SLIDE_INTAKE_POSITION = 0;
    public static double HORIZONTAL_SLIDE_TRANSFER_POSITION = 0;
    public static double HORIZONTAL_CLAW_ROLL_INTAKE_POSITION = 90;
    public static double HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION = 0;
    public static double HORIZONTAL_CLAW_PITCH_INTAKE_POSITION = 68;
    public static double HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION = 35;
    public static double HORIZONTAL_WRIST_PITCH_INTAKE_POSITION = 180;
    public static double HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION = 35;

    // vertical
    public static int VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION = 0;
    public static int VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION = 0;
    public static double VERTICAL_CLAW_ROLL_TRANSFER_POSITION = 0;
    public static double VERTICAL_CLAW_ROLL_DEPOSIT_POSITION = 0;
    public static double VERTICAL_CLAW_PITCH_TRANSFER_POSITION = 0;
    public static double VERTICAL_CLAW_PITCH_DEPOSIT_POSITION = 0;
    public static double VERTICAL_WRIST_PITCH_TRANSFER_POSITION = 135;
    public static double VERTICAL_WRIST_PITCH_DEPOSIT_POSITION = 0;

//    public static double Kp = 1.8; // proportional gain
//    public static double Ki = 0; // integral gain
//    public static double Kd = 0.031; // derivative gain
//
//    public static double UP = 1;
//    public static double DOWN = 0;
//    public static double PWM_LOW = 1700;
//    public static double PWM_HIGH = 2000;
//    public static double ANGLE_SPEED = 0.001;


}
