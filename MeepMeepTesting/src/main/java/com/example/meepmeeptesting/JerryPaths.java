package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.*;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.lang.Math;
import java.util.Scanner;

public class JerryPaths {

    public static MeepMeep meepMeep;

    public static Action currentTrajectoryAction;


    public static void main(String[] args) {

        meepMeep = new MeepMeep(600);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f);

        RoadRunnerBotEntity drive = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 10.8)
                .build();

        Pose2d coloredSampleStartPose = new Pose2d(
                -11.5,
                62,
                Math.toRadians(90)
        );

        Pose2d bucketStartPose = new Pose2d(
                11.5,
                62,
                Math.toRadians(-90)
        );

        drive.runAction(
              drive.getDrive().actionBuilder(bucketStartPose)
                      .setTangent(0)
                        .strafeTo(new Vector2d(15, 40))
                      .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
                      .strafeToLinearHeading(new Vector2d(45, 35), Math.toRadians(270))
                      .lineToXLinearHeading(56, Math.toRadians(45))
                      .strafeTo(new Vector2d(55, 55))
                      .lineToXLinearHeading(56, Math.toRadians(270))
                      .strafeTo(new Vector2d(56, 35))
                      .strafeTo(new Vector2d(45, 50))

                     .lineToYLinearHeading(47, Math.toRadians(45))
                     .strafeTo(new Vector2d(55, 55))
                      .strafeToLinearHeading(new Vector2d(55, 24), Math.toRadians(0))
                      .strafeToLinearHeading(new Vector2d(55, 55), Math.toRadians(45))
//                      .strafeTo(new Vector2d(60, 24))
//                      .strafeTo(new Vector2d(28, 24))
//                      .splineToLinearHeading(new Pose2d(60, 60, Math.toRadians(45)), Math.PI / 4)
                      .endTrajectory()

                      .build()
        );


        meepMeep.addEntity(drive);

        meepMeep.start();


    }




}