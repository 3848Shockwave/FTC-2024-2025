package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadConfig {
    Gamepad gamepad1;

    Gamepad gamepad2;
    public GamepadConfig(Gamepad gp1, Gamepad gp2) {
        this.gamepad1 = gp1;
        this.gamepad2 = gp2;

    }
    public Gamepad getGamepad1() {
        return gamepad1;
    }
    public Gamepad getGamepad2() {
        return gamepad2;
    }
    public double getGamepad1LeftStickY() {
     return -gamepad1.left_stick_y;
    }
    public double getGamepad1LeftStickX() {
     return gamepad1.left_stick_x;
    }
    public double getGamepad1RightStickY() {
     return -gamepad1.right_stick_y;
    }
    public double getGamepad1RightStickX() {
     return gamepad1.right_stick_x;
    }
    public double getGamepad1LeftTrigger() {
     return gamepad1.left_trigger;
    }
    public double getGamepad1RightTrigger() {
     return gamepad1.right_trigger;
    }
    public boolean getGamepad1A() {
     return gamepad1.a;
    }
    public boolean getGamepad1B() {
     return gamepad1.b;
    }
    public boolean getGamepad1X() {
     return gamepad1.x;
    }
    public boolean getGamepad1Y() {
     return gamepad1.y;
    }
    public boolean getGamepad1DpadUp() {
     return gamepad1.dpad_up;
    }
    public boolean getGamepad1DpadDown() {
     return gamepad1.dpad_down;
    }
    public boolean getGamepad1DpadLeft() {
     return gamepad1.dpad_left;
    }
    public boolean getGamepad1DpadRight() {
     return gamepad1.dpad_right;
    }
    public boolean getGamepad1LeftBumper() {
     return gamepad1.left_bumper;
    }
    public boolean getGamepad1RightBumper() {
     return gamepad1.right_bumper;
    }
    public boolean getGamepad1LeftStickButton() {
     return gamepad1.left_stick_button;
    }
    public boolean getGamepad1RightStickButton() {
     return gamepad1.right_stick_button;
    }
    public double getGamepad2LeftStickY() {
     return -gamepad2.left_stick_y;
    }
    public double getGamepad2LeftStickX() {
     return gamepad2.left_stick_x;
    }
    public double getGamepad2RightStickY() {
     return -gamepad2.right_stick_y;
    }
    public double getGamepad2RightStickX() {
     return gamepad2.right_stick_x;
    }
    public double getGamepad2LeftTrigger() {
     return gamepad2.left_trigger;
    }
    public double getGamepad2RightTrigger() {
     return gamepad2.right_trigger;
    }
    public boolean getGamepad2A() {
     return gamepad2.a;
    }
    public boolean getGamepad2B() {
     return gamepad2.b;
    }
    public boolean getGamepad2X() {
     return gamepad2.x;
    }
    public boolean getGamepad2Y() {
     return gamepad2.y;
    }
    public boolean getGamepad2DpadUp() {
     return gamepad2.dpad_up;
    }
    public boolean getGamepad2DpadDown() {
     return gamepad2.dpad_down;
    }
    public boolean getGamepad2DpadLeft() {
     return gamepad2.dpad_left;
    }
    public boolean getGamepad2DpadRight() {
     return gamepad2.dpad_right;
    }
    public boolean getGamepad2LeftBumper() {
     return gamepad2.left_bumper;
    }
    public boolean getGamepad2RightBumper() {
     return gamepad2.right_bumper;
    }
    public boolean getGamepad2LeftStickButton() {
     return gamepad2.left_stick_button;
    }
    public boolean getGamepad2RightStickButton() {
     return gamepad2.right_stick_button;
    }
    public void setGamepad1(Gamepad gp1) {
        this.gamepad1 = gp1;
    }
    public void setGamepad2(Gamepad gp2) {
        this.gamepad2 = gp2;
    }

}
