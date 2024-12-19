package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {

    public static boolean IS_FIELD_CENTRIC = true;


    // horizontal
    // claw
    public static double HORIZONTAL_CLAW_GRIP_OPEN_POSITION = 75;
    public static double HORIZONTAL_CLAW_GRIP_CLOSED_POSITION = 130;
    // slide
    public static double HORIZONTAL_SLIDE_TRANSFER_POSITION = 63;
    public static double HORIZONTAL_SLIDE_MAX_EXTENSION = 120;
    public static double HORIZONTAL_SLIDE_MIDDLE_EXTENSION = 90;
    public static double HORIZONTAL_SLIDE_MIN_EXTENSION = 52;
    public static double HORIZONTAL_SLIDE_SPEED_MANUAL = 1;

    public static double HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION = 82;
    public static int HORIZONTAL_CLAW_ROLL_PARALLEL_POSITION = 23;
    public static int HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION = 83;
    //    // claw roll speed for manually aligning it with the samples
//    public static double HORIZONTAL_CLAW_ROLL_SPEED = 1;
//    //
    public static double HORIZONTAL_CLAW_PITCH_INTAKE_POSITION = 0;
    public static double HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION = 93;
    public static double HORIZONTAL_WRIST_PITCH_INTAKE_POSITION = 180;
    public static double HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION = 60;
    public static double HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION = 100;

    // hovers
    public static double HORIZONTAL_WRIST_PITCH_HOVER_POSITION = 170;
    public static double HORIZONTAL_CLAW_PITCH_HOVER_POSITION = 0;

    // vertical
    //claw
    public static double VERTICAL_CLAW_GRIP_OPEN_POSITION = 75;
    public static double VERTICAL_CLAW_GRIP_CLOSED_POSITION = 130;

    public static int VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION = 0;
    public static int VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION = 4250;
    public static double VERTICAL_SLIDE_MOTOR_SPEED_FAST = 1;
    public static double VERTICAL_SLIDE_MOTOR_SPEED_SLOW = 0.5;
    public static double VERTICAL_CLAW_ROLL_TRANSFER_POSITION = 90;
    public static double VERTICAL_CLAW_ROLL_DEPOSIT_POSITION = 90;
    public static double VERTICAL_CLAW_PITCH_TRANSFER_POSITION = 37;
    public static double VERTICAL_CLAW_PITCH_DEPOSIT_POSITION = 40;
    public static double VERTICAL_WRIST_PITCH_TRANSFER_POSITION = 180;
    public static double VERTICAL_WRIST_PITCH_DEPOSIT_POSITION = 10;

    public static int MOTOR_POSITION_TOLERANCE = 50;
    public static double MOTOR_POSITION_COEFFICIENT = 0.1;

    public static double TRIGGER_DEADZONE = 0.1;


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
