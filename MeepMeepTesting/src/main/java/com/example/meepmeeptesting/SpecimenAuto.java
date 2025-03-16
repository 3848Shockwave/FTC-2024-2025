package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.*;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.lang.Math;

public class SpecimenAuto {

    public static MeepMeep meepMeep;

    public static Action currentSequentialAction;

    public static double RIGHT_X = -37;
    public static double Y = 29;
    public static double RIGHT_SAMPLE_HEADING = 200;
    public static double RIGHT_TURN_HEADING = 100;
    public static double MIDDLE_X = -47;
    public static double MIDDLE_SAMPLE_HEADING = 200;
    public static double MIDDLE_TURN_HEADING = 100;
    public static double LEFT_X = -57;
    public static double LEFT_SAMPLE_HEADING = 200;
    public static double VEL_CONSTRAINT = 30;

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
                -46.3,
                55,
                Math.toRadians(-90)
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
                .setTangent(Math.toRadians(180 - 20))
                .splineToLinearHeading(
                        new Pose2d(
                                RIGHT_X,
                                Y,
                                Math.toRadians(RIGHT_SAMPLE_HEADING)),
                        Math.toRadians(180 + 40),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropRightSampleTAB = rightSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(RIGHT_TURN_HEADING)
                )
                .endTrajectory();
        TrajectoryActionBuilder middleSampleTAB = dropRightSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(MIDDLE_X, Y),
                        Math.toRadians(MIDDLE_SAMPLE_HEADING),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropMiddleSampleTAB = middleSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 5
                        ),
                        Math.toRadians(90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();

        // hang specimens
        TrajectoryActionBuilder pickUpFromWallPoseTAB0 = dropMiddleSampleTAB
                .fresh()
                .turnTo(Math.toRadians(-90))
                .strafeToLinearHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y
                        ),
                        Math.toRadians(-90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder hangSpecimenTAB0 = pickUpFromWallPoseTAB0
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(
                        new Pose2d(
                                hangSpecimenPose.component1().x - 3,
                                hangSpecimenPose.component1().y,
                                hangSpecimenPose.component2().toDouble()
                        ),
                        Math.toRadians(-50),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();

        // hang specimens
        TrajectoryActionBuilder pickUpFromWallPoseTAB1 = hangSpecimenTAB0
                .fresh()
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(
                        new Pose2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 5,
                                pickUpSpecimenPose.heading.toDouble()
                        ),
                        Math.toRadians(90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder hangSpecimenTAB1 = pickUpFromWallPoseTAB1
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(
                        new Pose2d(
                                hangSpecimenPose.component1().x - 6,
                                hangSpecimenPose.component1().y,
                                hangSpecimenPose.component2().toDouble()
                        ),
                        Math.toRadians(-50),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();
        // hang specimens
        TrajectoryActionBuilder pickUpFromWallPoseTAB2 = hangSpecimenTAB1
                .fresh()
                .setTangent(Math.toRadians(160))
                .splineToLinearHeading(
                        pickUpSpecimenPose,
                        Math.toRadians(90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder hangSpecimenTAB2 = pickUpFromWallPoseTAB2
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(
                        new Pose2d(
                                hangSpecimenPose.component1().x - 9,
                                hangSpecimenPose.component1().y,
                                hangSpecimenPose.component2().toDouble()
                        ),
                        Math.toRadians(-50),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder parkTAB = hangSpecimenTAB2
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                -40,
                                60
                        ),
                        Math.toRadians(-90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
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


                // intake to pickup position
                pickUpFromWallPoseTAB0.build(),
                // pick up specimen
                // transfer specimen
                hangSpecimenTAB0.build(),

                pickUpFromWallPoseTAB1.build(),
                hangSpecimenTAB1.build(),

                pickUpFromWallPoseTAB2.build(),
                hangSpecimenTAB2.build(),

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