package opencvcamera;

import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CameraFrame extends JFrame {

    private VideoCapture capture;
    private JLabel cameraScreen;

    private final int CAMERA_WIDTH = 640;
    private final int CAMERA_HEIGHT = 480;

    private Mat output = new Mat();
    private Mat hsvMat = new Mat();
    private Mat redThresholdMat = new Mat();
    private Mat blueThresholdMat = new Mat();
    private Mat yellowThresholdMat = new Mat();

    private Mat currentMat;

    private final Scalar defaultColor = new Scalar(255, 0, 0);

    private final Scalar redColor = new Scalar(150, 150, 255);
    private final Scalar blueColor = new Scalar(255, 150, 150);
    private final Scalar yellowColor = new Scalar(150, 255, 255);

    private JLabel predictedSideLabel;

    private JPanel cameraPanel;
    private JPanel sliderPanel;
    private JPanel buttonPanel;
    private JPanel predictedSidePanel;

    private ValueSlider lowerRedThresholdSlider;
    private ValueSlider upperRedThresholdSlider;
    private ValueSlider lowerBlueThresholdSlider;
    private ValueSlider upperBlueThresholdSlider;
    private ValueSlider lowerYellowThresholdSlider;
    private ValueSlider upperYellowThresholdSlider;

    private ValueSlider areaThresholdSlider;

    // change these to doubles
    private final int DEFAULT_LOWER_YELLOW_HUE_THRESHOLD = 16;
    private final int DEFAULT_UPPER_YELLOW_HUE_THRESHOLD = 40;
    private final int DEFAULT_LOWER_BLUE_HUE_THRESHOLD = 106;
    private final int DEFAULT_UPPER_BLUE_HUE_THRESHOLD = 119;
    private final int DEFAULT_LOWER_RED_HUE_THRESHOLD = 117;
    private final int DEFAULT_UPPER_RED_HUE_THRESHOLD = 125;

    private final int DEFAULT_YELLOW_VS_THRESHOLD = 140;
    private final int DEFAULT_BLUE_VS_THRESHOLD = 88;
    private final int DEFAULT_RED_VS_THRESHOLD = 138;

    private Scalar lowerYellowThreshold = new Scalar(DEFAULT_LOWER_YELLOW_HUE_THRESHOLD, DEFAULT_YELLOW_VS_THRESHOLD, DEFAULT_YELLOW_VS_THRESHOLD);
    private Scalar upperYellowThreshold = new Scalar(DEFAULT_UPPER_YELLOW_HUE_THRESHOLD, 255, 255);
    private Scalar lowerBlueThreshold = new Scalar(DEFAULT_LOWER_BLUE_HUE_THRESHOLD, DEFAULT_BLUE_VS_THRESHOLD, DEFAULT_BLUE_VS_THRESHOLD);
    private Scalar upperBlueThreshold = new Scalar(DEFAULT_UPPER_BLUE_HUE_THRESHOLD, 255, 255);
    private Scalar lowerRedThreshold = new Scalar(DEFAULT_LOWER_RED_HUE_THRESHOLD, DEFAULT_RED_VS_THRESHOLD, DEFAULT_RED_VS_THRESHOLD);
    private Scalar upperRedThreshold = new Scalar(DEFAULT_UPPER_RED_HUE_THRESHOLD, 255, 255);

    private final ArrayList<DetectedSample> detectedSamples = new ArrayList<>();


    private final DecimalFormat df = new DecimalFormat("#.##");


    public CameraFrame() {
        super("webcam");
        setLayout(new BorderLayout());

        currentMat = redThresholdMat;

        sliderPanel = new JPanel(new FlowLayout());
        buttonPanel = new JPanel(new FlowLayout());

        // add camera screen
        final int bufferSize = 10;
        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, CAMERA_WIDTH + bufferSize, CAMERA_HEIGHT + bufferSize);
        cameraPanel = new JPanel(new FlowLayout());
        cameraPanel.setPreferredSize(new Dimension(CAMERA_WIDTH + bufferSize, CAMERA_HEIGHT + bufferSize));
        cameraPanel.add(cameraScreen);
        add(cameraPanel, BorderLayout.CENTER);

        addSliders();
        addButtons();

        // add predicted side label
        predictedSideLabel = new JLabel();
        predictedSideLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        predictedSideLabel.setBounds(0, 0, 500, 50);

        predictedSidePanel = new JPanel(new FlowLayout());
        predictedSidePanel.setPreferredSize(new Dimension(200, 1));
        predictedSidePanel.add(predictedSideLabel);
        add(predictedSidePanel, BorderLayout.WEST);


//        setSize(width, height);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);

    }

    private void addButtons() {
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setPreferredSize(new Dimension(1, 100));

        JButton redButton = new JButton("red");
        redButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMat = redThresholdMat;
            }
        });
        JButton blueButton = new JButton("blue");
        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMat = blueThresholdMat;
            }
        });
        JButton yellowButton = new JButton("yellow");
        yellowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMat = yellowThresholdMat;
            }
        });
        JButton normalButton = new JButton("normal");
        normalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMat = output;
            }
        });

        buttonPanel.add(redButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(yellowButton);
        buttonPanel.add(normalButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addSliders() {
        int width = 400;
        int height = 20;
        sliderPanel.setPreferredSize(new Dimension(width, 1));
        // add sliders
        lowerRedThresholdSlider = new ValueSlider(0, 255, DEFAULT_LOWER_RED_HUE_THRESHOLD, width, "lower red hue threshold", sliderPanel);
        upperRedThresholdSlider = new ValueSlider(0, 255, DEFAULT_UPPER_RED_HUE_THRESHOLD, width, "upper red hue threshold", sliderPanel);

        lowerBlueThresholdSlider = new ValueSlider(0, 255, DEFAULT_LOWER_BLUE_HUE_THRESHOLD, width, "lower blue hue threshold", sliderPanel);
        upperBlueThresholdSlider = new ValueSlider(0, 255, DEFAULT_UPPER_BLUE_HUE_THRESHOLD, width, "upper blue hue threshold", sliderPanel);

        lowerYellowThresholdSlider = new ValueSlider(0, 255, DEFAULT_LOWER_YELLOW_HUE_THRESHOLD, width, "lower yellow hue threshold", sliderPanel);
        upperYellowThresholdSlider = new ValueSlider(0, 255, DEFAULT_UPPER_YELLOW_HUE_THRESHOLD, width, "upper yellow hue threshold", sliderPanel);

        areaThresholdSlider = new ValueSlider(0, 200, 0, width, "rectangle area threshold", sliderPanel);

        add(sliderPanel, BorderLayout.EAST);
    }

    public Mat processFrame(Mat input) {
        // clear the detected samples so there are no duplicates
        detectedSamples.clear();
        // basics
        // https://www.youtube.com/watch?v=547ZUZiYfQE

        // 34 min intro
        // https://www.youtube.com/watch?v=ZAQHwe4mX4g

//        //
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_BGR2HSV);

        // filter yellow to yellowThresholdMat
        lowerYellowThreshold.val[0] = lowerYellowThresholdSlider.getValue();
        upperYellowThreshold.val[0] = upperYellowThresholdSlider.getValue();

        lowerBlueThreshold.val[0] = lowerBlueThresholdSlider.getValue();
        upperBlueThreshold.val[0] = upperBlueThresholdSlider.getValue();

        lowerRedThreshold.val[0] = lowerRedThresholdSlider.getValue();
        upperRedThreshold.val[0] = upperRedThresholdSlider.getValue();

        filterColor(hsvMat, yellowThresholdMat, lowerYellowThreshold, upperYellowThreshold);

        // filter blue to blueThresholdMat
        filterColor(hsvMat, blueThresholdMat, lowerBlueThreshold, upperBlueThreshold);

        // shift hsv mat hue to make things slightly easier
        Imgproc.cvtColor(input, hsvMat, Imgproc.COLOR_RGB2HSV);
        // filter red to redThresholdMat
        filterColor(hsvMat, redThresholdMat, lowerRedThreshold, upperRedThreshold);

        input.copyTo(output);
        analyzeAndDrawContours(blueThresholdMat, output, blueColor, "blue");
        analyzeAndDrawContours(yellowThresholdMat, output, yellowColor, "yellow");
        analyzeAndDrawContours(redThresholdMat, output, redColor, "red");
        // print slider values
//        System.out.print("\rslider 0: " + lowerRedThresholdSlider.getValue() + ", slider 1: " + upperRedThresholdSlider.getValue() + ", slider 2: " + yellowThresholdSlider.getValue() + ", slider 3: " + slider3.getValue());

        return currentMat;

        // https://www.youtube.com/watch?v=P1NaUqC8dSg
//

//        // Ruckus video:
//        // https://www.youtube.com/watch?v=ZAQHwe4mX4g
    }

    private void filterColor(Mat input, Mat output, Scalar lowerThreshold, Scalar upperThreshold) {
        // apply morphology
        Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Core.inRange(input, lowerThreshold, upperThreshold, output);
        Imgproc.morphologyEx(output, output, Imgproc.MORPH_OPEN, structuringElement);
        Imgproc.morphologyEx(output, output, Imgproc.MORPH_OPEN, structuringElement);

    }


    private void analyzeAndDrawContours(Mat input, Mat output, Scalar rectColor, String color) {

        // get the contours
        var contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(input, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
//        Imgproc.drawContours(output, contours, -1, new Scalar(255, 255, 255));

        // get the largest rotated rectangle from the contours
        RotatedRect largestRotatedRect = getLargestSampleAsRotatedRect(contours);


        if (largestRotatedRect != null) {
            // add the sample to the list
            DetectedSample sample = new DetectedSample();
            sample.setAngle(getRotatedRectAngle(largestRotatedRect));
            sample.setColor(color);

            detectedSamples.add(sample);

            // draw the rectangle and rectangle info
            drawRotatedRect(largestRotatedRect, output, rectColor);

        }

    }

    private RotatedRect getLargestSampleAsRotatedRect(ArrayList<MatOfPoint> contours) {

        RotatedRect largestRotatedRect = null;
        double largestAreaPercent = 0;

        for (MatOfPoint contour : contours) {
//            Rect rect = Imgproc.boundingRect(contour);
            double contourArea = Imgproc.contourArea(contour);
            double areaPercent = (contourArea) / (CAMERA_HEIGHT * CAMERA_WIDTH);

            if (areaPercent > (areaThresholdSlider.getValue() / 10_000d) && areaPercent > largestAreaPercent) {
                largestAreaPercent = areaPercent;
                MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
                RotatedRect rotatedRect = Imgproc.minAreaRect(contour2f);
                largestRotatedRect = rotatedRect;
            }
        }
        return largestRotatedRect;
    }

    private void drawRotatedRect(RotatedRect rotatedRect, Mat output, Scalar rectColor) {
        // draw rectangle
        // vertex 0 is the point with the lowest x (left-most vertex)
        // vertices 1, 2, and 3 are all decided clockwise from vertex 0
////        show vertex indices
//        for (int i = 0; i < vertices.length; i++) {
//            Imgproc.putText(output, String.valueOf(i), vertices[i], Imgproc.FONT_HERSHEY_COMPLEX, 0.5, new Scalar(0, 0, 0), 1);
//        }
        Point[] vertices = new Point[4];
        rotatedRect.points(vertices);
        for (int j = 0; j < 4; j++) {
            Imgproc.line(output, vertices[j], vertices[(j + 1) % 4], rectColor, 2);
        }

        // width is the length of the side made by vertices 0 and 3
        double angle = getRotatedRectAngle(rotatedRect);

        // draw info
        String text = "";
        text = df.format(angle);
        // draw the text
//        Imgproc.putText(output, df.format(rotatedRect.size.width), new Point(0, 10), Imgproc.FONT_HERSHEY_COMPLEX, 0.5, new Scalar(0, 0, 0), 1);
        Imgproc.putText(output, text, new Point(rotatedRect.center.x, rotatedRect.center.y - 20), Imgproc.FONT_HERSHEY_COMPLEX, 0.5, new Scalar(0, 0, 0), 1);
        Imgproc.circle(output, rotatedRect.center, 2, rectColor, 2 * 2);

    }

    private double getRotatedRectAngle(RotatedRect rotatedRect) {
        // if the rectangle's height is bigger than it's width
        double angle = rotatedRect.angle;
        if (rotatedRect.size.width < rotatedRect.size.height) {
            angle += 90;
        }
        angle = -(angle - 180);

        return angle;

    }

    public void startCamera() {
        capture = new VideoCapture(0);
        Mat input = new Mat();
        Mat screenOutput;
        byte[] imageData;
        ImageIcon icon;

        while (true) {

            // read image to matrix
            capture.read(input);

            screenOutput = processFrame(input);

            // convert matrix to byte
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", screenOutput, buf);
            imageData = buf.toArray();

            // add to JLabel
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
        }
    }

}


