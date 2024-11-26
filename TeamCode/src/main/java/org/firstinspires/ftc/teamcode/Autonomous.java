package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

//@Autonomous(name = "Autonimous")
public class Autonomous extends BasicOpMode_Linear_TankDrive {

    private DcMotor leftDrive;
    private DcMotor rightDrive;
    private DcMotor arm;
    private Servo wrist;
    private CRServo grabber;



    ElapsedTime timer;

    @Override
    public void runOpMode() {
        leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right-drive");

        waitForStart();
        if (opModeIsActive()) {
            telemetry.update();

            // place to write your movement
            forward(1.0);
            turn(true,1.0);
            backward(1.0);
            turn(false,1.0);
            buffer(3.0);
            arm(true,2.0);
            arm(false, 2.0);
            wrist(true);
            wrist(false);
            grab(true,1.0);
            grab(false,3.0);
        }
    }




    public void forward(double time) {
        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        while (timer.seconds() <= time) {
            leftDrive.setPower(-1.0);
            rightDrive.setPower(1.0);
        }

        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
        leftDrive.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        rightDrive.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
    }




    // backward function :)
    public void backward(double time) {
        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        while (timer.seconds() <= time) {
            leftDrive.setPower(1.0);
            rightDrive.setPower(-1.0);
        }
        leftDrive.setPower(0.0);
        rightDrive.setPower(0.0);
        leftDrive.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        rightDrive.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
    }




    // **name thing better name in the future**
    // true = left, false = right
    public void turn(boolean direction, double time ) {
        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        if (direction) {
            while (timer.seconds() <= time) {
                rightDrive.setPower(-0.5);
                leftDrive.setPower(-0.5);
            }
        } else {
            /*
             * this time can be different depending on what motors
             * you're using or how much power is set to the motor
             */
            while (timer.seconds() <= time) {
                rightDrive.setPower(0.5);
                leftDrive.setPower(0.5);
            }
        }
        rightDrive.setPower(0.0);
        rightDrive.setPower(0.0);
        leftDrive.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
        rightDrive.setZeroPowerBehavior((DcMotor.ZeroPowerBehavior.BRAKE));
    }




    // method if you want the robot to wait in between actions
    public void buffer(double time) {
        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        while (timer.seconds() <= time) {
            rightDrive.setPower(0.0);
            leftDrive.setPower(0.0);
        }
    }




    //function to control the arm
    //true is moving forward, false is moving backward
    public void arm(boolean direction, double time){
        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        if (direction){
            while (timer.seconds() <= time ){
                arm.setPower(1.0);
            }
            arm.setPower(0.0);
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }else{
            while(timer.seconds() <= time){
                arm.setPower(-1.0);
            }
            arm.setPower(0.0);
            arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
    }




    //function to control the wrist
    //true to grab position, false to hang position
    double intakeAngle = Math.toRadians(30);
    double hangSpecimenAngle = Math.toRadians(90);
    double wristAngle;
    public void wrist(boolean position){

        ElapsedTime timer = new ElapsedTime();

        waitForStart();
        if (position){
            wristAngle = intakeAngle;
        }else{
            wristAngle = hangSpecimenAngle;
        }
    }




    //function to control the grabber
    double grabberCollect = 0.8;
    double grabberRelease = -0.4;
    public void grab(boolean thing, double time){

        ElapsedTime timer = new ElapsedTime();

        waitForStart();

        if(thing){
            while (timer.seconds() <= time ) {
                grabber.setPower(grabberCollect);
            }
            grabber.setPower(0.0);
        }else{
            while (timer.seconds() <= time ) {
                grabber.setPower(grabberRelease);
            }
            grabber.setPower(0.0);
        }
    }

}