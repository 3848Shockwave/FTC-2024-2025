package org.firstinspires.ftc.teamcode;


import android.media.SoundPool;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.external.android.AndroidSoundPool;

import java.util.ArrayList;

@TeleOp(name="MainDrive", group="Linear Opmode")

public class MainDrive extends LinearOpMode {
    ArrayList<String> DcMotorNames = new ArrayList<String>();
    ArrayList<String> ServoNames = new ArrayList<String>();


    @Override
    public void runOpMode() throws InterruptedException {
        //add all names of stuff
        DcMotorNames.add("frontRight");
        DcMotorNames.add("frontLeft");
        DcMotorNames.add("backRight");
        DcMotorNames.add("backLeft");
        DcMotorNames.add("spoolLeft");
        DcMotorNames.add("spoolRight");
        ServoNames.add("vertClawGrip");
        ServoNames.add("vertClawPiv");
        ServoNames.add("vertClawRot");
        ServoNames.add("vertArmRotL");
        ServoNames.add("vertArmRotR");
        ServoNames.add("horzClawGrip");
        ServoNames.add("horzClawPiv");
        ServoNames.add("horzClawRot");
        ServoNames.add("horzArmRotL");
        ServoNames.add("horzArmRotR");
        ServoNames.add("horzExtL");
        ServoNames.add("horzExtR");
        Hardware hw = new Hardware(DcMotorNames,ServoNames,hardwareMap);
        Robot robot = new Robot(true, new GamepadConfig(gamepad1, gamepad2),hw );
        robot.setup();
        //Oh great Vessel of Honour,
        //May your servo-motors be guarded,
        //
        //Against malfunction,
        //
        //As your spirit is guarded from impurity.
        //
        //We beseech the Machine God to watch over you.
        //
        //Let flow the sacred oils,
        //
        //And let not the sorrows of the Seven Perplexities
        //
        //trouble thine pistons.
        //
        //Let flow the blessed unguents,
        //
        //And may thine circuitry remain divinely blessed.
        waitForStart();


        while (opModeIsActive()) {

            robot.setPowers(robot.driveCalc());
            robot.cope();
            telemetry.addData("encoders",robot.hardware.getDcMotor("spoolLeft").getCurrentPosition());
            telemetry.update();
        }
    }
}
