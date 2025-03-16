package org.firstinspires.ftc.teamcode.constants;

import com.acmerobotics.dashboard.config.Config;

@Config
public final class Constants {

    public static boolean IS_FIELD_CENTRIC = true;
    // horizontal
    // claw
    public static double HORIZONTAL_CLAW_GRIP_OPEN_POSITION = 70;
    public static double HORIZONTAL_CLAW_GRIP_CLOSED_POSITION = 170;
    // slide
    public static double HORIZONTAL_SLIDE_MAX_POSITION = 250;
    public static double HORIZONTAL_SLIDE_MIDDLE_POSITION = 210;
    public static double HORIZONTAL_SLIDE_MIN_POSITION = 165; // manual min set to 170
    public static double HORIZONTAL_SLIDE_TRANSFER_POSITION = 175;
    public static int HORIZONTAL_CLAW_ROLL_FLAT_POSITION = 105;
    public static int HORIZONTAL_CLAW_ROLL_UP_POSITION = 3;
    public static double HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION = HORIZONTAL_CLAW_ROLL_FLAT_POSITION;
    // claw roll speed for manually aligning it with the samples
    public static double HORIZONTAL_CLAW_ROLL_SPEED = 1;
    //
    public static double HORIZONTAL_CLAW_PITCH_INTAKE_POSITION = 250;
    public static double HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION = 124;
    //    public static double HORIZONTAL_CLAW_PITCH_VERTICAL_POSITION = 70;
    public static double HORIZONTAL_WRIST_PITCH_INTAKE_POSITION = 35;
    public static double HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION = 190;
    public static double HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION = 160;
    // hovers
    public static double HORIZONTAL_WRIST_PITCH_HOVER_POSITION = 55;
    public static double HORIZONTAL_WRIST_PITCH_PICKUP_POSITION = 135;
    public static double HORIZONTAL_CLAW_PITCH_HOVER_POSITION = 250;
    public static double HORIZONTAL_CLAW_PITCH_PICKUP_POSITION = 250;
    // SPECIMEN
    public static double HORIZONTAL_SLIDE_SPECIMEN_TRANSFER_POSITION = 155;
    public static double HORIZONTAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION = 130;
    public static double HORIZONTAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION = 190;
    // vertical
    //claw
    public static double VERTICAL_CLAW_GRIP_OPEN_POSITION = 70;
    public static double VERTICAL_CLAW_GRIP_CLOSED_POSITION = 170;
    public static int VERTICAL_SLIDE_MOTOR_TRANSFER_POSITION = 10;
    public static int VERTICAL_SLIDE_MOTOR_BOTTOM_BASKET_POSITION = 600;
    public static int VERTICAL_SLIDE_MOTOR_TOUCH_BAR_POSITION = 100;
    public static int VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION = 1520;
    public static double VERTICAL_SLIDE_MOTOR_SPEED_FAST = 1;
    public static int VERTICAL_SLIDE_MOTOR_POSITION_TOLERANCE = 50;
    public static double VERTICAL_SLIDE_MOTOR_POSITION_COEFFICIENT = 0.01;
    public static double VERTICAL_CLAW_ROLL_TRANSFER_POSITION = 52;
    public static double VERTICAL_CLAW_ROLL_DEPOSIT_POSITION = 150;
    public static double VERTICAL_CLAW_PITCH_TRANSFER_POSITION = 198;
    public static double VERTICAL_CLAW_PITCH_DEPOSIT_POSITION = 160;
    public static double VERTICAL_CLAW_PITCH_WALL_POSITION = 160;
    public static double VERTICAL_WRIST_PITCH_TRANSFER_POSITION = 0;
    public static double VERTICAL_WRIST_PITCH_DEPOSIT_POSITION = 180;
    public static double VERTICAL_WRIST_PITCH_WALL_POSITION = 300;
    // SPECIMEN
    public static double VERTICAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION = 34;
    public static double VERTICAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION = 0;
    public static double VERTICAL_CLAW_PITCH_SPECIMEN_DROPOFF_POSITION = 180;
    public static double VERTICAL_CLAW_ROLL_SPECIMEN_DROPOFF_POSITION = VERTICAL_CLAW_ROLL_TRANSFER_POSITION;
    public static double VERTICAL_WRIST_PITCH_SPECIMEN_DROPOFF_POSITION = 180;
    public static int VERTICAL_SLIDE_MOTOR_SPECIMEN_UP_POSITION = 460;
    public static int VERTICAL_SLIDE_MOTOR_SPECIMEN_ON_BAR_POSITION = 100;

}
