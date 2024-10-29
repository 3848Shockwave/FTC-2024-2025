package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.LinkedList;
import java.util.Scanner;

public class MeepMeepTesting {


    public enum COLOR {
        RED, BLUE;
    }

    public static COLOR currentColor;

    public static Pose2d bucketStartPose;
    public static Pose2d coloredSampleStartPose;
    public static Pose2d submersiblePickUpPose;
    public static Pose2d dropSamplePose;

    public static MeepMeep meepMeep;

    public static int redAngleAdjustment;
    public static int redPoseAdjustment;

    static {
        currentColor = COLOR.BLUE;
        redAngleAdjustment = 0;
        redPoseAdjustment = 1;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int endHeading = 0;
        String endHeadingInput;
        LinkedList<RoadRunnerBotEntity> bots = new LinkedList<>();

        // switch to red prompt
        System.out.print("Switch to red? (Y/N): ");
        String switchToRed = scanner.nextLine();
        if (switchToRed.equalsIgnoreCase("y")) {
            currentColor = COLOR.RED;
        }

        if (currentColor == COLOR.RED) {
            redAngleAdjustment = 180;
            redPoseAdjustment = -1;
        }

        bucketStartPose = new Pose2d(
                35 * redPoseAdjustment,
                62 * redPoseAdjustment,
                Math.toRadians(-90 + redAngleAdjustment)
        );
        coloredSampleStartPose = new Pose2d(
                -35 * redPoseAdjustment,
                62 * redPoseAdjustment,
                Math.toRadians(-90 + redAngleAdjustment)
        );
        submersiblePickUpPose = new Pose2d(
                27 * redPoseAdjustment,
                0 * redPoseAdjustment,
                Math.toRadians(180 + redAngleAdjustment)
        );
        dropSamplePose = new Pose2d(
                50 * redPoseAdjustment,
                50 * redPoseAdjustment,
                Math.toRadians(180 + 45 + redAngleAdjustment)
        );

        meepMeep = new MeepMeep(600);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f);

        while (true) {

            try {
                System.out.print("enter new end heading(deg): ");
                endHeadingInput = scanner.nextLine();
                endHeading = (endHeadingInput.isEmpty()) ? endHeading : Integer.parseInt(endHeadingInput);
            } catch (Exception e) {
                System.out.println("exception occurred. try again");
            }

            RoadRunnerBotEntity newBot = new DefaultBotBuilder(meepMeep)
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .followTrajectorySequence(TrajectorySequences::coloredStraysTS);

            bots.add(newBot);
            meepMeep.addEntity(newBot);

            meepMeep.start();


            System.out.println("next cycle");
        }
    }


}