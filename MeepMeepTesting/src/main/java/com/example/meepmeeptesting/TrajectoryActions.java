package com.example.meepmeeptesting;

// import the poses


import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * WHEN COPYING THIS CLASS FOR ROADRUNNER IMPLEMENTATION, MAKE SURE TO CHANGE EVERYWHERE YOU SEE:
 * "DriveShim"
 * TO THE NAME OF YOUR DRIVE CLASS, E.G. "SampleMechanumDrive"
 */
public class TrajectoryActions {
    // reference: https://github.com/technototes/IntoTheDeep2024/blob/main/MeepMeepTesting/src/main/java/com/example/meepmeeptesting/ViggoTesting.java#L19

    // import "drive" as an argument to return the desired Trajectory Sequence


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

    public static TrajectoryActionBuilder goToHangSpecimenPose(RoadRunnerBotEntity drive) {
        return drive.getDrive().actionBuilder(coloredSampleStartPose)
                .strafeToLinearHeading(
                        hangSpecimenPose.component1(),
                        hangSpecimenPose.component2()
                )
                .endTrajectory();
    }

    public static TrajectoryActionBuilder goToRightSample(RoadRunnerBotEntity drive) {
        return goToHangSpecimenPose(drive)
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
    }

    public static TrajectoryActionBuilder turn(RoadRunnerBotEntity drive, TrajectoryActionBuilder previousTAB, double degrees) {
        return previousTAB
                .fresh()
                .turn(Math.toRadians(degrees))
                .endTrajectory();
    }

    public static TrajectoryActionBuilder goToMiddleSample(RoadRunnerBotEntity drive) {
        return goToHangSpecimenPose(drive)
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
    }

    public static TrajectoryActionBuilder moveAndHangSpecimensTA(RoadRunnerBotEntity drive) {
        return drive.getDrive().actionBuilder(coloredSampleStartPose)

                // sample 3
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(
                        new Pose2d(
                                -50,
                                45,
                                Math.atan2(
                                        leftColoredSampleVector.x - (-50),
                                        leftColoredSampleVector.y - (45)
                                ) - Math.toRadians(5)
                        ),
                        Math.toRadians(180)
                )
                // pick up specimen

                .turn(
                        Math.toRadians(-90)
                );
        // drop specimen

//                // PICK UP SPECIMEN
//                .lineToLinearHeading(pickUpSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//                })
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//
//                // PICK UP SPECIMEN 2
//                .lineToLinearHeading(pickUpSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//                })
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//
//                // PICK UP SPECIMEN 3
//                .lineToLinearHeading(pickUpSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//                })
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//
//                // TODO: park
//
    }

//    public static Action pushSamplesTS(RoadRunnerBotEntity drive) {
//        return drive.getDrive().actionBuilder(coloredSampleStartPose)
//                // LOOP 1
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//                .waitSeconds(3)
//
//                .lineTo(new Vector2d(
//                                -35,
//                                45
//                        )
//                )
//                .lineTo(new Vector2d(
//                                -35,
//                                9
//                        )
//                )
////                // lil spline
//                .lineToLinearHeading(
//                        new Pose2d(
//                                -45,
//                                9,
//                                Math.toRadians(180)
//                        )
//                )
//                // push sample 1 toward observation zone
//                .lineToConstantHeading(
//                        new Vector2d(
//                                -45,
//                                58
//                        )
//                )
//                // go to sample 2
//                .lineToConstantHeading(
//                        new Vector2d(
//                                -45,
//                                15
//                        )
//                )
//                // lil spline
//                .splineToLinearHeading(
//                        new Pose2d(
//                                -55,
//                                9,
//                                Math.toRadians(180)
//                        ),
//                        Math.toRadians(180)
//                )
//                // push sample 2 toward observation zone
//                .lineToConstantHeading(
//                        new Vector2d(
//                                -55,
//                                58
//                        )
//                )
//                // go to sample 3
//                .lineToConstantHeading(
//                        new Vector2d(
//                                -55,
//                                15
//                        )
//                )
//                // lil spline
//                .splineToLinearHeading(
//                        new Pose2d(
//                                -61,
//                                9,
//                                Math.toRadians(180)
//                        ),
//                        Math.toRadians(180)
//                )
//                // push sample 3 toward observation zone
//                .lineToConstantHeading(
//                        new Vector2d(
//                                -61,
//                                58
//                        )
//                )
//
//                // TODO: park
//
////                .setTangent(-45)
////                .splineToSplineHeading(
////                        new Pose2d(
////                                0,
////                                45,
////                                Math.toRadians(-180)
////                        ),
////                        Math.toRadians(0)
////                )
////                // go to the middle zone for a level 1 hang (i forgot what it's called)
////                .splineToSplineHeading(
////                        new Pose2d(
////                                35,
////                                25,
////                                Math.toRadians(90)
////                        ),
////                        Math.toRadians(-90)
////                )
////                .splineToSplineHeading(
////                        new Pose2d(
////                                23,
////                                10,
////                                Math.toRadians(0)
////                        ),
////                        Math.toRadians(180)
////                )
////                .addDisplacementMarker(() -> {
////
////                })
//                .build();
//    }
//
//    public static Action neutralStraysTS(RoadRunnerBotEntity drive) {
//        return drive.trajectorySequenceBuilder(bucketStartPose)
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//                .waitSeconds(3)
//
//                .addDisplacementMarker(() -> {
//                    // pick up sample
//                })
//
//                // LOOP 1
//                // go to pickup
//                .lineToSplineHeading(new Pose2d(
//                        48,
//                        39,
//                        Math.toRadians(-90)
//                ))
//                .addDisplacementMarker(() -> {
//                    // pick up sample
//                })
//
//                // go to dropoff
//                .lineToSplineHeading(dropSamplePose)
//                .addDisplacementMarker(() -> {
//                    // drop off sample
//                })
//
//                // LOOP 2
//                .lineToLinearHeading(new Pose2d(
//                        58,
//                        43,
//                        Math.toRadians(-90)
//                ))
//                .addDisplacementMarker(() -> {
//                    // pick up sample
//                })
//                .lineToSplineHeading(dropSamplePose)
//                .addDisplacementMarker(() -> {
//                    // drop off sample
//                })
//
//                // LOOP 3
//                .lineToLinearHeading(new Pose2d(
//                        52,
//                        27,
//                        Math.toRadians(0)
//                ))
//                .addDisplacementMarker(() -> {
//                    // pick up sample
//                })
//                .lineToSplineHeading(dropSamplePose)
//                .addDisplacementMarker(() -> {
//                    // drop off sample
//                })
//
//                // TODO: park
//
//                .build();
//    }
//
//    public static Action submersibleCycleTS(RoadRunnerBotEntity drive) {
//        return drive.trajectorySequenceBuilder(bucketStartPose)
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//                .waitSeconds(3)
//
//                .addDisplacementMarker(() -> {
//                    // pick up sample
//                })
//
//                // go to dropoff
//                .setTangent(Math.toRadians(90))
//                .lineToLinearHeading(
//                        dropSamplePose
//                )
//                .addDisplacementMarker(() -> {
//                    // drop off sample
//                })
//
//                // LOOP 1
//                // go back to pickup
//                .setTangent(Math.toRadians(180 + 20))
//                .splineToLinearHeading(
//                        submersiblePickUpPose,
//                        Math.toRadians(-110)
//                )
//                .addDisplacementMarker(() -> {
//                    // pick up sample
//                })
//                // go back dropoff
//                .setTangent(Math.toRadians(90))
//                .splineToLinearHeading(
//                        dropSamplePose,
//                        Math.toRadians(30)
//                )
//                .addDisplacementMarker(() -> {
//                    // drop off sample
//                })
//
//                // LOOP 2
//                // go back to pickup
//                .setTangent(Math.toRadians(180 + 20))
//                .splineToLinearHeading(
//                        submersiblePickUpPose,
//                        Math.toRadians(-110)
//                )
//                .addDisplacementMarker(() -> {
//                    // pick up sample
//                })
//                // go back dropoff
//                .setTangent(Math.toRadians(90))
//                .splineToLinearHeading(
//                        dropSamplePose,
//                        Math.toRadians(30)
//                )
//                .addDisplacementMarker(() -> {
//                    // drop off sample
//                })
//                .build();
//    }
}
