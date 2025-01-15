package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.*;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.lang.Math;
import java.util.Scanner;

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

        TrajectoryActionBuilder turn90_0TAB = goToRightSampleTAB
                .fresh()
                .turn(Math.toRadians(-90))
                .endTrajectory();

        TrajectoryActionBuilder goToMiddleSampleTAB = turn90_0TAB
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

        TrajectoryActionBuilder turn90_1TAB = goToMiddleSampleTAB
                .fresh()
                .turn(Math.toRadians(-90))
                .endTrajectory();

        TrajectoryActionBuilder goToLeftSampleTAB = turn90_1TAB
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

        TrajectoryActionBuilder turn90_2TAB = goToLeftSampleTAB
                .fresh()
                .turn(Math.toRadians(-90))
                .endTrajectory();

        // TODO: after these TABs should be the TABS to pick up specimens and hang them, but i'm not sure we're advanced enough to actually execute those
        // TODO: park

        // TODO: in the actual code you will not be using drive.runAction(), instead, this is what it will look like:
        // schedule(new ActionCommand(goToHangSpecimenTab.build(), new HashSet<>()));
        // schedule(new HangSpecimenCommand(...));
        drive.runAction(
                new SequentialAction(
                        goToHangSpecimenTAB.build(),
                        goToRightSampleTAB.build(),
                        turn90_0TAB.build(),
                        goToMiddleSampleTAB.build(),
                        turn90_1TAB.build(),
                        goToLeftSampleTAB.build(),
                        turn90_2TAB.build()
                )
        );


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