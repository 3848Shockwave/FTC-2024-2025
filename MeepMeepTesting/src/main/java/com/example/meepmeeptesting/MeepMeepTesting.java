package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.*;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.lang.Math;
import java.util.Scanner;

public class MeepMeepTesting {

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
        TrajectoryActionBuilder goToHangSpecimenTAB = drive.getDrive().actionBuilder(coloredSampleStartPose)
                .strafeToLinearHeading(
                        hangSpecimenPose.component1(),
                        hangSpecimenPose.component2()
                )
                .endTrajectory();

        // specimen


        // to continue off a previous command, you do:
        // TAB tab = previousTAB.fresh(). [insert trajectories here] .endTrajectory();

        // push samples
        TrajectoryActionBuilder pushSamplesTAB = goToHangSpecimenTAB
                .fresh()
                // right sample
                .setTangent(Math.toRadians(0))
                .strafeToConstantHeading(
                        new Vector2d(-30, 39)
                )
                .splineToConstantHeading(
                        new Vector2d(-36, 35),
                        Math.toRadians(-90)
                )
                // down
                .strafeToConstantHeading(
                        new Vector2d(-36, 17)
                )
                .splineToConstantHeading(
                        new Vector2d(-47, 19),
                        Math.toRadians(90)

                )
                // up
                .strafeToConstantHeading(
                        new Vector2d(-47, 51)
                )

                // middle sample
                // down
                .strafeToConstantHeading(
                        new Vector2d(-47, 17)
                )
                .splineToConstantHeading(
                        new Vector2d(-57, 19),
                        Math.toRadians(90)

                )
                // up
                .strafeToConstantHeading(
                        new Vector2d(-57, 51)
                )
                // left sample
                // down
                .strafeToConstantHeading(
                        new Vector2d(-57, 15)
                )
                .splineToConstantHeading(
                        new Vector2d(-61, 17),
                        Math.toRadians(90)

                )
                .waitSeconds(0)
                // up
                .strafeToConstantHeading(
                        new Vector2d(-61, 51)
                )
                .endTrajectory();

        // hang specimens
        TrajectoryActionBuilder pickUpSpecimenTAB_0 = pushSamplesTAB
                .fresh()
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 6
                        ),
                        Math.toRadians(90)
                )
                .waitSeconds(1.5)
                .strafeToConstantHeading(
                        pickUpSpecimenPose.component1()
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_0 = pickUpSpecimenTAB_0
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 3,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-50)
                )
                .endTrajectory();

        TrajectoryActionBuilder pickUpSpecimenTAB_1 = hangSpecimenTAB_0
                .fresh()
                .setTangent(Math.toRadians(-90))
                .strafeToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 6
                        )
                )
                .waitSeconds(1.5)
                .strafeToConstantHeading(
                        pickUpSpecimenPose.component1()
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_1 = pickUpSpecimenTAB_1
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 5,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-50)
                )
                .endTrajectory();

        TrajectoryActionBuilder pickUpSpecimenTAB_2 = hangSpecimenTAB_1
                .fresh()
                .setTangent(Math.toRadians(-90))
                .strafeToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 6
                        )
                )
                .waitSeconds(1.5)
                .strafeToConstantHeading(
                        pickUpSpecimenPose.component1()
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_2 = pickUpSpecimenTAB_2
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 7,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-50)
                )
                .endTrajectory();

        TrajectoryActionBuilder parkPSHS_TAB = hangSpecimenTAB_2
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                -58,
                                55
                        ),
                        Math.toRadians(-90)
                )
                .endTrajectory();

        TrajectoryActionBuilder parkPSHS_Backup_TAB = hangSpecimenTAB_0
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                -58,
                                55
                        ),
                        Math.toRadians(-90)
                )
                .endTrajectory();


        // TODO: after these TABs should be the TABS to pick up specimens and hang them, but i'm not sure we're advanced enough to actually execute those
        // TODO: park

        // TODO: in the actual code you will not be using drive.runAction(). instead, this is what it will look like:
        // schedule(new ActionCommand(goToHangSpecimenTab.build(), new HashSet<>()));
        // schedule(new HangSpecimenCommand(...));

        SequentialAction pushSamplesAndHangSpecimensSA = new SequentialAction(
                // transfer preloaded specimen
                goToHangSpecimenTAB.build(),
                // hang specimen
                pushSamplesTAB.build(),

                // intake to pickup position
                pickUpSpecimenTAB_0.build(),
                // pick up specimen
                // transfer specimen
                hangSpecimenTAB_0.build(),
                // hang specimen
                parkPSHS_Backup_TAB.build()
//
//                // repeat x2
//                pickUpSpecimenTAB_1.build(),
//                hangSpecimenTAB_1.build(),
//                pickUpSpecimenTAB_2.build(),
//                hangSpecimenTAB_2.build(),
//
//                // park
//                parkPSHS_TAB.build()
        );

        currentSequentialAction = pushSamplesAndHangSpecimensSA;

        drive.runAction(currentSequentialAction);

        meepMeep.addEntity(drive);

        meepMeep.start();


    }

}