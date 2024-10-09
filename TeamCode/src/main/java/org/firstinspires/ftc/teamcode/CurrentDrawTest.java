/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.ValueProvider;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvWebcam;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When a selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@TeleOp(name="CurrentDraw", group="Linear OpMode")

public class CurrentDrawTest extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx motorTest = null;
    private ServoImplEx servo = null;
    private ColorSensor color = null;
    private DistanceSensor distSens;

    FtcDashboard dashboard = FtcDashboard.getInstance();
    Telemetry dashboardTelemetry = dashboard.getTelemetry();
    double pidP = 0.0;
    double pidI = 0.0;
    double pidD = 0.0;
    double pidF = 0.0;
    ValueProvider vpP = new ValueProvider() {

        @Override
        public Object get() {
            return pidP;
        }

        @Override
        public void set(Object value) {
            pidP = (double)value;
        }
    };
    ValueProvider vpI = new ValueProvider() {

        @Override
        public Object get() {
            return pidI;
        }

        @Override
        public void set(Object value) {
            pidI = (double)value;
        }
    };ValueProvider vpD = new ValueProvider() {

        @Override
        public Object get() {
            return pidD;
        }

        @Override
        public void set(Object value) {
            pidD = (double)value;
        }
    };
    ValueProvider vpF = new ValueProvider() {

        @Override
        public Object get() {
            return pidF;
        }

        @Override
        public void set(Object value) {
            pidF = (double)value;
        }
    };



    @Override
    public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvWebcam camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

// Open camera device
        camera.openCameraDevice();

// Start camera streaming

        camera.startStreaming(320, 240);  // Adjust resolution as needed
        FtcDashboard.getInstance().startCameraStream(camera, 0);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        motorTest = hardwareMap.get(DcMotorEx.class, "motor");
        servo = hardwareMap.get(ServoImplEx.class, "servo");
        distSens = hardwareMap.get(DistanceSensor.class, "distSens");
        color = hardwareMap.get(ColorSensor.class, "color");
        servo.setPwmEnable();

      //  servo.setPosition(0.0);
       // sleep(1000);
        //servo.setPosition(0.0);

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        motorTest.setDirection(DcMotorEx.Direction.REVERSE);
        PIDFCoefficients pidf = new PIDFCoefficients(pidP,pidI,pidD,pidF);
        servo.setPwmRange(new PwmControl.PwmRange(1400,1900));

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            dashboard.addConfigVariable("PID","(P)", vpP);
            dashboard.addConfigVariable("PID","(I)", vpI);
            dashboard.addConfigVariable("PID","(D)", vpD);
            dashboard.addConfigVariable("PID","(F)", vpF);
            motorTest.setPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION,pidf );


            if(gamepad1.a==true){
                servo.setPosition(1.0);

            }
            if(gamepad1.b==true){
                servo.setPosition(0.0);

            }
            // Setup a variable for each drive wheel to save power level for telemetry
            double power;


            // Choose to drive using either Tank Mode, or POV Mode
            // Comment out the method that's not used.  The default below is POV.

            // POV Mode uses left stick to go forward, and right stick to turn.
            // - This uses basic math to combine motions and is easier to drive straight.
            power = 1.0;

            // Tank Mode uses one stick to control each wheel.
            // - This requires no math, but it is hard to drive forward slowly and keep straight.
            // leftPower  = -gamepad1.left_stick_y ;
            // rightPower = -gamepad1.right_stick_y ;
            // Send calculated power to wheels
           // motorTest.setPower(power);
            telemetry.addData("CurrentStats","Current Draw: "+motorTest.getCurrent(CurrentUnit.AMPS));
            dashboardTelemetry.addData("Current",motorTest.getCurrent(CurrentUnit.MILLIAMPS) );
            telemetry.addData("CurrentDist",distSens.getDistance(DistanceUnit.MM));
            dashboardTelemetry.addData("Dist",distSens.getDistance(DistanceUnit.MM) );
            telemetry.addData("Red", color.red());
            telemetry.addData("Green", color.green());
            telemetry.addData("Blue", color.blue());
            telemetry.addData("Current Velocity: ", motorTest.getVelocity());
            dashboardTelemetry.addData("Current Velocity: ", motorTest.getVelocity());
            telemetry.addData("Current PIDF: ", motorTest.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
            dashboardTelemetry.addData("Current PIDF: ", motorTest.getPIDFCoefficients(DcMotor.RunMode.RUN_TO_POSITION));
            telemetry.addData("Port: ", motorTest.getPortNumber());
            dashboardTelemetry.addData("Port: ", motorTest.getPortNumber());
            telemetry.addData("ServoRange: ", servo.getPwmRange());
            dashboardTelemetry.addData("ServoRange: ", "up" +servo.getPwmRange().usPulseUpper +"low"+servo.getPwmRange().usPulseLower);
            dashboardTelemetry.setMsTransmissionInterval(100);
            dashboardTelemetry.update();
            // Show the elapsed game time and wheel power.

            telemetry.update();
        }
    }
}
