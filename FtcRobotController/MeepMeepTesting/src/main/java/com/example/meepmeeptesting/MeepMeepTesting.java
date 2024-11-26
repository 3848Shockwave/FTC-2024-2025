package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                // start position of robot: new Pose2d(...)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder(new Pose2d(58, -58, Math.toRadians(150)))
                        // chain methods such as "splineTo" and "strafe" and "lineTo" to move the robot to locations
                        // locations are represented by Vector2d's and Pose2d's
                        .splineToLinearHeading(new Pose2d(-50, -50,Math.toRadians(230)), Math.toRadians(230))//to the bracket
                        .waitSeconds(1)
                        .strafeTo(new Vector2d(15, -48))
                        .splineToLinearHeading(new Pose2d(38, 0, Math.toRadians(270)), Math.toRadians(90))//go to the line
                        .splineToLinearHeading(new Pose2d(44, -27,Math.toRadians(270)), Math.toRadians(180))//to the Specimen
                        .splineToLinearHeading(new Pose2d(-60, -60, Math.toRadians(180)), Math.toRadians(180))//go to the score area
                        .lineToLinearHeading(new Pose2d(33, -33, Math.toRadians(90)))//back to the line
                        .splineToLinearHeading(new Pose2d(55, 0, Math.toRadians(270)), Math.toRadians(270))//back to the line
                        .splineToLinearHeading(new Pose2d(60, -40, Math.toRadians(200)), Math.toRadians(270))//back to the line
                        .lineToLinearHeading(new Pose2d(-60, -60, Math.toRadians(180)))//to the score area
                        .lineToLinearHeading(new Pose2d(50, -40, Math.toRadians(180)))//back to the line
                        .splineToLinearHeading(new Pose2d(62, -62, Math.toRadians(130)), Math.toRadians(270))//back to the line
                        .build());
// use SplinetoLinearHeading or splinetoSplineHeading instead of SplineTo, the first radius is the heading of the robot,
// the second is the heading of the end line
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}