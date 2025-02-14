package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.*;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.lang.Math;

public class SpecimenAuto {

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
                37.5,
                Math.toRadians(90)
        );

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
                        new Vector2d(

                                hangSpecimenPose.component1().x,
                                hangSpecimenPose.component1().y - 1
                        ),
                        hangSpecimenPose.component2()
                )
                .endTrajectory();

        // specimen
        // to continue off a previous command, you do:
        // TAB tab = previousTAB.fresh(). [insert trajectories here] .endTrajectory();

        TrajectoryActionBuilder rightSampleTAB = goToHangSpecimenTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(-29, 46),
                        Math.toRadians(180 + 50)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropRightSampleTAB = rightSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(135)
                )
                .endTrajectory();
        TrajectoryActionBuilder middleSampleTAB = dropRightSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(-39, 46),
                        Math.toRadians(180 + 50)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropMiddleSampleTAB = middleSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(135)
                )
                .endTrajectory();
        TrajectoryActionBuilder leftSampleTAB = dropMiddleSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(-49, 46),
                        Math.toRadians(180 + 50)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropLeftSampleTAB = leftSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(135)
                )
                .endTrajectory();


        // hang specimens
        TrajectoryActionBuilder pickUpSpecimenTAB = dropLeftSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        pickUpSpecimenPose.component1(),
                        Math.toRadians(90)
                )
                .endTrajectory();

        // move 2 in forward
        TrajectoryActionBuilder slightlyForwardTAB = pickUpSpecimenTAB
                .fresh()
                .strafeToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y + 1
                        )
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB = slightlyForwardTAB
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 3,
                                hangSpecimenPose.component1().y - 2
                        ),
                        Math.toRadians(-50)
                )
                .endTrajectory();


        TrajectoryActionBuilder parkTAB = hangSpecimenTAB
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

                rightSampleTAB.build(),
                dropRightSampleTAB.build(),
                middleSampleTAB.build(),
                dropMiddleSampleTAB.build(),
                leftSampleTAB.build(),
                dropLeftSampleTAB.build(),


                // intake to pickup position
                pickUpSpecimenTAB.build(),
                slightlyForwardTAB.build(),
                // pick up specimen
                // transfer specimen
                hangSpecimenTAB.build(),
                // hang specimen
                parkTAB.build()
//
//                // repeat x2
//                pickUpSpecimenTAB1.build(),
//                hangSpecimenTAB1.build(),
//                pickUpSpecimenTAB2.build(),
//                hangSpecimenTAB2.build(),
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