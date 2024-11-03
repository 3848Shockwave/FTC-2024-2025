package org.firstinspires.ftc.teamcode;


import android.media.SoundPool;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;

import java.util.ArrayList;

@TeleOp(name="MainDrive", group="Linear Opmode")

public class MainDrive extends LinearOpMode {
    ArrayList<String> DcMotorNames = new ArrayList<String>();
    ArrayList<String> ServoNames = new ArrayList<String>();


    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorNames.add("frontRight");
        DcMotorNames.add("frontLeft");
        DcMotorNames.add("backRight");
        DcMotorNames.add("backLeft");
        Hardware hw = new Hardware(DcMotorNames,ServoNames,hardwareMap);
        Robot robot = new Robot(true, new GamepadConfig(gamepad1, gamepad2),hw );
        robot.setup();
        waitForStart();
        while (opModeIsActive()) {
            robot.setPowers(robot.driveCalc());


        }
    }
}
