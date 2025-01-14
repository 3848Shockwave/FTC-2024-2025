package com.example.meepmeeptesting;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.util.Scanner;
import java.util.function.Function;

public class MeepMeepTesting {

    public static MeepMeep meepMeep;

    public static Action currentTrajectoryAction;


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//        endHeading = 0;
        String endHeadingInput;


        meepMeep = new MeepMeep(600);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f);

        // CHANGE THIS TO CHANGE THE CURRENT TRAJECTORY SEQUENCE
//        currentTrajectorySequence = TrajectorySequences::pushSamplesTS;
//        currentTrajectorySequence = TrajectorySequences::neutralStraysTS;
//        currentTrajectorySequence = TrajectorySequences::submersibleCycleTS;

        RoadRunnerBotEntity currentBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 10.8)
                .build();

        currentTrajectoryAction = TrajectoryActions.moveAndHangSpecimensTA(currentBot);


        currentBot.runAction(currentTrajectoryAction);

////            bots.add(newBot);
//        if (currentBot != null) {
//            meepMeep.removeEntity(currentBot);
//        }
        meepMeep.addEntity(currentBot);

        meepMeep.start();


    }


}