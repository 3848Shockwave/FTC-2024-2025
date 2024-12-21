package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.DriveShim;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.rowlandhall.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.function.Function;

public class MeepMeepTesting {




    public static MeepMeep meepMeep;

    public static Function<DriveShim, TrajectorySequence> currentTrajectorySequence;



    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
//        endHeading = 0;
        String endHeadingInput;
//        LinkedList<RoadRunnerBotEntity> bots = new LinkedList<>();

//        // switch to red prompt
//        System.out.print("Switch to red? (Y/N): ");
//        String switchToRed = scanner.nextLine();
//        if (switchToRed.equalsIgnoreCase("y")) {
//            currentColor = COLOR.RED;
//        }
//        System.out.println("current color: " + currentColor.toString());


        meepMeep = new MeepMeep(600);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f);

        // CHANGE THIS TO CHANGE THE CURRENT TRAJECTORY SEQUENCE
//        currentTrajectorySequence = TrajectorySequences::neutralStraysTS;
//        currentTrajectorySequence = TrajectorySequences::submersibleCycleTS;
        currentTrajectorySequence = TrajectorySequences::pushSamplesTS;

        RoadRunnerBotEntity currentBot = null;

        while (true) {


            RoadRunnerBotEntity newBot = new DefaultBotBuilder(meepMeep)
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
//                    .followTrajectorySequence(TrajectorySequences::coloredStraysTS);
                    .followTrajectorySequence(currentTrajectorySequence::apply);

//            bots.add(newBot);
            if (currentBot!=null) {
                meepMeep.removeEntity(currentBot);
            }
            meepMeep.addEntity(newBot);

            meepMeep.start();


//            try {
//                System.out.print("enter new end heading(deg): ");
                endHeadingInput = scanner.nextLine();
//                endHeading = (endHeadingInput.isEmpty()) ? endHeading : Integer.parseInt(endHeadingInput);
//            } catch (Exception e) {
//                System.out.println("exception occurred. try again");
//            }
            System.out.println();


            System.out.println("next cycle");
//            // temp
//            return;
//            //
            currentBot = newBot;
        }
    }


}