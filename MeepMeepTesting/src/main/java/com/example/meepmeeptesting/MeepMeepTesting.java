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

        Scanner scanner = new Scanner(System.in);
//        endHeading = 0;
        String endHeadingInput;


        meepMeep = new MeepMeep(600);

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f);

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

        // to continue off a previous command, you do:
        // TAB tab = previousTAB.fresh(). [insert trajectories here] .endTrajectory();
        TrajectoryActionBuilder goToRightSampleTAB = goToHangSpecimenTAB
                .fresh()
                // sample 1
                .strafeToLinearHeading(
                        new Vector2d(
                                -28,
                                45
                        ),
                        Math.atan2(
                                rightColoredSampleVector.x - (-28),
                                rightColoredSampleVector.y - (45)
                        ) - Math.toRadians(5)
                )
                .endTrajectory();

        TrajectoryActionBuilder turn90TAB_0 = goToRightSampleTAB
                .fresh()
                .turn(Math.toRadians(-90))
                .endTrajectory();

        TrajectoryActionBuilder goToMiddleSampleTAB = turn90TAB_0
                .fresh()
                // sample 1
                .strafeToLinearHeading(
                        new Vector2d(
                                -40,
                                45
                        ),
                        Math.atan2(
                                middleColoredSampleVector.x - (-40),
                                middleColoredSampleVector.y - (45)
                        ) - Math.toRadians(0)
                )
                .endTrajectory();

        TrajectoryActionBuilder turn90TAB_1 = goToMiddleSampleTAB
                .fresh()
                .turn(Math.toRadians(-90))
                .endTrajectory();

        TrajectoryActionBuilder goToLeftSampleTAB = turn90TAB_1
                .fresh()
                // sample 1
                .strafeToLinearHeading(
                        new Vector2d(
                                -50,
                                45
                        ),
                        Math.atan2(
                                leftColoredSampleVector.x - (-50),
                                leftColoredSampleVector.y - (45)
                        ) - Math.toRadians(0)
                )
                .endTrajectory();

        TrajectoryActionBuilder turn90TAB_2 = goToLeftSampleTAB
                .fresh()
                .turn(Math.toRadians(-90))
                .endTrajectory();

        TrajectoryActionBuilder parkPTS_TAB = turn90TAB_2
                .fresh()
                .strafeToLinearHeading(new Vector2d(-57, 60), Math.toRadians(90))
                .endTrajectory();

        // push samples
        TrajectoryActionBuilder pushSamplesTAB = goToHangSpecimenTAB
                .fresh()
                // right sample
                .strafeTo(
                        new Vector2d(-37, 37)
                )
                .strafeToConstantHeading(
                        new Vector2d(-37, 15)
                )
                .splineToConstantHeading(
                        new Vector2d(-40, 10),
                        Math.toRadians(-180)
                )
                .splineToConstantHeading(
                        new Vector2d(-45, 15),
                        Math.toRadians(90)
                )
                .lineToYConstantHeading(55)

                // middle sample
                .strafeToConstantHeading(
                        new Vector2d(-37 - 9, 15)
                )
                .splineToConstantHeading(
                        new Vector2d(-40 - 9, 10),
                        Math.toRadians(-180)
                )
                .splineToConstantHeading(
                        new Vector2d(-45 - 9, 15),
                        Math.toRadians(90)
                )
                .lineToYConstantHeading(55)
                // left sample
                .strafeToConstantHeading(
                        new Vector2d(-37 - 18, 15)
                )
                .splineToConstantHeading(
                        new Vector2d(-40 - 18, 10),
                        Math.toRadians(-180)
                )
                .splineToConstantHeading(
                        new Vector2d(-45 - 16, 15),
                        Math.toRadians(90)
                )
                .lineToYConstantHeading(55)
                .endTrajectory();

        // hang specimens
        TrajectoryActionBuilder pickUpSpecimenTAB_0 = pushSamplesTAB
                .fresh()
                .strafeToLinearHeading(new Vector2d(-57, 44), Math.toRadians(90))
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_0 = pickUpSpecimenTAB_0
                .fresh()
                .strafeToLinearHeading(hangSpecimenPose.component1(), hangSpecimenPose.component2())
                .endTrajectory();

        TrajectoryActionBuilder pickUpSpecimenTAB_1 = hangSpecimenTAB_0
                .fresh()
                .strafeToLinearHeading(new Vector2d(-57, 44), Math.toRadians(90))
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_1 = pickUpSpecimenTAB_1
                .fresh()
                .strafeToLinearHeading(hangSpecimenPose.component1(), hangSpecimenPose.component2())
                .endTrajectory();

        TrajectoryActionBuilder pickUpSpecimenTAB_2 = hangSpecimenTAB_1
                .fresh()
                .strafeToLinearHeading(new Vector2d(-57, 44), Math.toRadians(90))
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_2 = pickUpSpecimenTAB_2
                .fresh()
                .strafeToLinearHeading(hangSpecimenPose.component1(), hangSpecimenPose.component2())
                .endTrajectory();

        TrajectoryActionBuilder parkPSHS_TAB = hangSpecimenTAB_2
                .fresh()
                .strafeToLinearHeading(new Vector2d(-57, 60), Math.toRadians(90))
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

                // repeat x2
                pickUpSpecimenTAB_1.build(),
                hangSpecimenTAB_1.build(),
                pickUpSpecimenTAB_2.build(),
                hangSpecimenTAB_2.build(),

                // park
                parkPSHS_TAB.build()
        );

        SequentialAction pickUpAndTransferSamplesSA = new SequentialAction(
                goToHangSpecimenTAB.build(),
                goToRightSampleTAB.build(),
                turn90TAB_0.build(),
                goToMiddleSampleTAB.build(),
                turn90TAB_1.build(),
                goToLeftSampleTAB.build(),
                turn90TAB_2.build(),
                parkPTS_TAB.build()
        );

//        currentSequentialAction = pickUpAndTransferSamplesSA;
        currentSequentialAction = pushSamplesAndHangSpecimensSA;

        drive.runAction(currentSequentialAction);

        meepMeep.addEntity(drive);

        meepMeep.start();


    }

    public static Pose2d bucketStartPose = new Pose2d(
            11.5,
            62,
            Math.toRadians(-90)
    );
    public static Pose2d coloredSampleStartPose = new Pose2d(
            -11.5,
            62,
            Math.toRadians(90)
    );
    public static Pose2d submersiblePickUpPose = new Pose2d(
            27,
            0,
            Math.toRadians(180)
    );
    public static Pose2d dropSamplePose = new Pose2d(
            50,
            50,
            Math.toRadians(180 + 45)
    );

    public static Pose2d hangSpecimenPose = new Pose2d(
            0,
            37,
            Math.toRadians(90)
    );


    public static Vector2d rightColoredSampleVector = new Vector2d(-48, 27);
    public static Vector2d middleColoredSampleVector = new Vector2d(-58, 27);
    public static Vector2d leftColoredSampleVector = new Vector2d(-68, 27);
    public static Vector2d placedSpecimenVector = new Vector2d(-47, 58);
    public static Pose2d pickUpSpecimenPose = new Pose2d(
            -37, 41,
            Math.atan2(
                    -(placedSpecimenVector.x - (-37)),
                    -(placedSpecimenVector.y - (41))
            ) - Math.toRadians(20)
    );


//    public static TrajectoryActionBuilder moveAndHangSpecimensTA(RoadRunnerBotEntity drive) {
//        return drive.getDrive().actionBuilder(coloredSampleStartPose)
////                // PICK UP SPECIMEN
////                .lineToLinearHeading(pickUpSpecimenPose)
////                .addDisplacementMarker(() -> {
////                    // pick up specimen
////                })
////
////                // hang specimen
////                .lineToSplineHeading(hangSpecimenPose)
////                .addDisplacementMarker(() -> {
////                    // hang specimen
////                })
////
////                // PICK UP SPECIMEN 2
////                .lineToLinearHeading(pickUpSpecimenPose)
////                .addDisplacementMarker(() -> {
////                    // pick up specimen
////                })
////
////                // hang specimen
////                .lineToSplineHeading(hangSpecimenPose)
////                .addDisplacementMarker(() -> {
////                    // hang specimen
////                })
////
////                // PICK UP SPECIMEN 3
////                .lineToLinearHeading(pickUpSpecimenPose)
////                .addDisplacementMarker(() -> {
////                    // pick up specimen
////                })
////
////                // hang specimen
////                .lineToSplineHeading(hangSpecimenPose)
////                .addDisplacementMarker(() -> {
////                    // hang specimen
////                })
////
////                // TODO: park
////
//    }


}