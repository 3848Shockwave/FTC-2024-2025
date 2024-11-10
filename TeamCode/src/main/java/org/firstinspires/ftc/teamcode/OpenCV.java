package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.*;

@TeleOp(name = "OpenCV Test", group = "OpenCV")
public class OpenCV extends LinearOpMode {

    OpenCvCamera camera;
    MyPipeline pipeline;

    public static final int CAMERA_WIDTH = 1280;
    public static final int CAMERA_HEIGHT = 720;

    // in mm
    public static final int FOCAL_LENGTH = 4;


    @Override
    public void runOpMode() throws InterruptedException {

        /*
         * Instantiate an OpenCvCamera object for the camera we'll be using.
         * In this sample, we're using a webcam. Note that you will need to
         * make sure you have added the webcam to your configuration file and
         * adjusted the name here to match what you named it in said config file.
         *
         * We pass it the view that we wish to use for camera monitor (on
         * the RC phone). If no camera monitor is desired, use the alternate
         * single-parameter constructor instead (commented out below)
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createInternalCamera2(OpenCvInternalCamera2.CameraDirection.BACK, cameraMonitorViewId);
//        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);
        pipeline = new MyPipeline();
        camera.setPipeline(pipeline);
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                // Usually this is where you'll want to start streaming from the camera (see section 4)
//                camera.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
                camera.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
//                camera.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
            }

            @Override
            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });


//        System.out.println();
        telemetry.addData("Info", hardwareMap.allDeviceMappings);

        waitForStart();
        // https://www.youtube.com/watch?v=vJhmmyvNqC0

        while (!isStarted() && !isStopRequested()) {
            telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }
    }

    class MyPipeline extends OpenCvPipeline {


        // Notice this is declared as an instance variable (and re-used), not a local variable
        Mat YCrCb = new Mat();
        Mat leftCrop = new Mat();
        Mat rightCrop = new Mat();
        // two rectangles separating the screen in halves
        int rectWidth = (OpenCV.CAMERA_WIDTH / 2) - 1;
        int rectHeight = OpenCV.CAMERA_HEIGHT - 1;
        Rect leftRect = new Rect(1, 1, rectWidth, rectHeight);
        Rect rightRect = new Rect(OpenCV.CAMERA_WIDTH / 2, 1, rectWidth, rectHeight);
        Scalar rectColor = new Scalar(255, 0, 0);
        Mat output = new Mat();

        @Override
        public Mat processFrame(Mat input)
        {
            // basics
            // https://www.youtube.com/watch?v=547ZUZiYfQE

            // 34 min intro
            // https://www.youtube.com/watch?v=ZAQHwe4mX4g

            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            telemetry.addLine("pipeline running");

            // display rectangles
            input.copyTo(output);
            Imgproc.rectangle(output, leftRect, rectColor, 2);
            Imgproc.rectangle(output, rightRect, rectColor, 2);

            // create crops from rects
            leftCrop = YCrCb.submat(leftRect);
            rightCrop = YCrCb.submat(rightRect);

            // extract red channel from both
            Core.extractChannel(leftCrop, leftCrop, 2);
            Core.extractChannel(rightCrop, rightCrop, 2);

            // get the average red for both sides
            Scalar leftAverageRed = Core.mean(leftCrop);
            Scalar rightAverageRed = Core.mean(rightCrop);

            telemetry.addData("average red left: ", leftAverageRed);
            telemetry.addData("average red right: ", rightAverageRed);

            String predictedSide = (leftAverageRed.val[0] >= rightAverageRed.val[0]) ? "left" : "right";

//            telemetry.addData("predicted side: ", )

            return output;
        }
    }
}
