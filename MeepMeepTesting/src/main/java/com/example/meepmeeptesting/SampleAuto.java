package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.*;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.lang.Math;

public class SampleAuto {

    public static MeepMeep meepMeep;

    public static Action currentSequentialAction;


    public static void main(String[] args) {

        meepMeep = new MeepMeep(600);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f);

        // copy from here
        Pose2d bucketStartPose = new Pose2d(
                11.5,
                62,
                Math.toRadians(-90)
        );
        Pose2d coloredSampleStartPose = new Pose2d(
                -11.5,
                62,
                Math.toRadians(90)
        );
        Pose2d submersiblePickUpPose = new Pose2d(
                27,
                0,
                Math.toRadians(180)
        );
        Pose2d dropSamplePose = new Pose2d(
                50,
                50,
                Math.toRadians(180 + 45)
        );

        Pose2d hangSpecimenPose = new Pose2d(
                0,
                38.5,
                Math.toRadians(90)
        );


        Vector2d rightColoredSampleVector = new Vector2d(-48, 27);
        Vector2d middleColoredSampleVector = new Vector2d(-58, 27);
        Vector2d leftColoredSampleVector = new Vector2d(-68, 27);
        Vector2d placedSpecimenVector = new Vector2d(-47, 58);
        Pose2d pickUpSpecimenPose = new Pose2d(
                -46,
                46,
                Math.atan2(
                        -(placedSpecimenVector.x - (-37)),
                        -(placedSpecimenVector.y - (41))
                ) - Math.toRadians(20)
        );


        RoadRunnerBotEntity drive = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 10.8)
                .build();

        // and here starts the TrajectoryActionBuilders.....
        // complete roadrunner TrajectoryBuilder reference: https://cookbook.dairy.foundation/roadrunner_10/complete_trajectorybuilder_reference.html
        // sample actual roadrunner opMode: https://rr.brott.dev/docs/v1-0/guides/centerstage-auto/

        // meepmeep installation and sample file: https://github.com/acmerobotics/MeepMeep
        // TODO: head over to https://rr.brott.dev/docs/v1-0/tuning/ if you want to tune our bot for roadrunner!

        TrajectoryActionBuilder dropSampleTAB = drive.getDrive().actionBuilder(bucketStartPose)
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2()
                )
                .endTrajectory();

        TrajectoryActionBuilder leftSampleTAB = dropSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(47, 60),
                        Math.toRadians(-90)

                )
                .endTrajectory();
        TrajectoryActionBuilder dropSample0TAB = leftSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2()
                )
                .endTrajectory();
        TrajectoryActionBuilder middleSampleTAB = dropSample0TAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(50, 60),
                        Math.toRadians(-90)

                )
                .endTrajectory();
        TrajectoryActionBuilder dropSample1TAB = middleSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2()
                )
                .endTrajectory();
        TrajectoryActionBuilder rightSampleTAB = dropSample1TAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(47, 26),
                        Math.toRadians(0)

                )
                .endTrajectory();
        TrajectoryActionBuilder dropSample2TAB = rightSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2()
                )
                .endTrajectory();
        TrajectoryActionBuilder parkTAB = dropSample2TAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                -58,
                                55
                        ),
                        Math.toRadians(-90)
                )
                .endTrajectory();

        SequentialAction sampleAuto = new SequentialAction(
                dropSampleTAB.build(),
                leftSampleTAB.build(),
                dropSample0TAB.build(),
                middleSampleTAB.build(),
                dropSample1TAB.build(),
                rightSampleTAB.build(),
                dropSample2TAB.build(),
                parkTAB.build()
        );

//        currentSequentialAction = pushSamplesAndHangSpecimensSA;
        currentSequentialAction = sampleAuto;

        drive.runAction(currentSequentialAction);

        meepMeep.addEntity(drive);

        meepMeep.start();


    }
}
