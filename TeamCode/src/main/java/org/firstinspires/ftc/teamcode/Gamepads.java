package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;

public class Gamepads {
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    public Gamepad currentGamepad1;
    public Gamepad currentGamepad2;
    public Gamepad previousGamepad1;
    public Gamepad previousGamepad2;

    public Gamepads(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        // gamepad docs: https://gm0.org/en/latest/docs/software/tutorials/gamepad.html
        // By setting these values to new Gamepad(), they will default to all
        // boolean values as false and all float values as 0
        this.currentGamepad1 = new Gamepad();
        this.currentGamepad2 = new Gamepad();

        this.previousGamepad1 = new Gamepad();
        this.previousGamepad2 = new Gamepad();
    }

    public void update() {
        // Store the gamepad values from the previous loop iteration in
        // previousGamepad1/2 to be used in this loop iteration.
        // This is equivalent to doing this at the end of the previous
        // loop iteration, as it will run in the same order except for
        // the first/last iteration of the loop.
        previousGamepad1.copy(currentGamepad1);
        previousGamepad2.copy(currentGamepad2);

        // Store the gamepad values from this loop iteration in
        // currentGamepad1/2 to be used for the entirety of this loop iteration.
        // This prevents the gamepad values from changing between being
        // used and stored in previousGamepad1/2.
        currentGamepad1.copy(gamepad1);
        currentGamepad2.copy(gamepad2);
    }
}
