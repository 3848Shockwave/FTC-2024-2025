package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.rowlandhall.meepmeep.roadrunner.DriveShim;
import org.rowlandhall.meepmeep.roadrunner.trajectorysequence.TrajectorySequence;
// import the poses


/**
 * WHEN COPYING THIS CLASS FOR ROADRUNNER IMPLEMENTATION, MAKE SURE TO CHANGE EVERYWHERE YOU SEE:
 * "DriveShim"
 * TO THE NAME OF YOUR DRIVE CLASS, E.G. "SampleMechanumDrive"
 */
public class TrajectorySequences {
    // reference: https://github.com/technototes/IntoTheDeep2024/blob/main/MeepMeepTesting/src/main/java/com/example/meepmeeptesting/ViggoTesting.java#L19

    // import "drive" as an argument to return the desired Trajectory Sequence
    public static int redAngleAdjustment;
    public static int redPoseAdjustment;
    public static Pose2d bucketStartPose;
    public static Pose2d coloredSampleStartPose;
    public static Pose2d submersiblePickUpPose;
    public static Pose2d dropSamplePose;

    public enum COLOR {
        RED, BLUE;
    }

    public static COLOR currentColor;

    static {
        currentColor = COLOR.BLUE;
        redAngleAdjustment = 0;
        redPoseAdjustment = 1;

        if (currentColor == COLOR.RED) {
            redAngleAdjustment = 180;
            redPoseAdjustment = -1;
        }

        bucketStartPose = new Pose2d(
                11.5 * redPoseAdjustment,
                62 * redPoseAdjustment,
                Math.toRadians(-90 + redAngleAdjustment)
        );
        coloredSampleStartPose = new Pose2d(
                -11.5 * redPoseAdjustment,
                62 * redPoseAdjustment,
                Math.toRadians(-90 + redAngleAdjustment)
        );
        submersiblePickUpPose = new Pose2d(
                27 * redPoseAdjustment,
                0 * redPoseAdjustment,
                Math.toRadians(180 + redAngleAdjustment)
        );
        dropSamplePose = new Pose2d(
                50 * redPoseAdjustment,
                50 * redPoseAdjustment,
                Math.toRadians(180 + 45 + redAngleAdjustment)
        );


    }

    public static Vector2d rightColoredSampleVector2d = new Vector2d(-48 * redPoseAdjustment, 27 * redPoseAdjustment);
    public static Vector2d middleColoredSampleVector2d = new Vector2d(-58 * redPoseAdjustment, 27 * redPoseAdjustment);
    public static Vector2d leftColoredSampleVector2d = new Vector2d(-68 * redPoseAdjustment, 27 * redPoseAdjustment);
    public static Vector2d placedSpecimenVector2d = new Vector2d(47 * redPoseAdjustment, -57 * redPoseAdjustment);

    public static TrajectorySequence completeSpecimenTS(DriveShim drive) {
        return drive.trajectorySequenceBuilder(coloredSampleStartPose)
//                // drop specimen
                .lineToSplineHeading(
                        new Pose2d(
                                0 * redPoseAdjustment,
                                37 * redPoseAdjustment,
                                Math.toRadians(-90 + redAngleAdjustment)
                        )
                )
                .addDisplacementMarker(() -> {
                    // drop specimen

                })
                .waitSeconds(0.5)

                // sample 1
                .lineToLinearHeading(
                        new Pose2d(
                                -28 * redPoseAdjustment,
                                45 * redPoseAdjustment,
                                Math.atan2(
                                        rightColoredSampleVector2d.getX() - (-28 * redPoseAdjustment),
                                        rightColoredSampleVector2d.getY() - (45 * redPoseAdjustment)
                                )
                        )
                )
                .addDisplacementMarker(() -> {
                    // pick up specimen

                })
                .waitSeconds(0.5)

                .turn(
                        Math.toRadians(-90)
                )
                .addDisplacementMarker(() -> {
                    // drop specimen

                })

                .waitSeconds(0.5)

                // sample 2
                .lineToLinearHeading(
                        new Pose2d(
                                -40 * redPoseAdjustment,
                                45 * redPoseAdjustment,
                                Math.atan2(
                                        middleColoredSampleVector2d.getX() - (-40 * redPoseAdjustment),
                                        middleColoredSampleVector2d.getY() - (45 * redPoseAdjustment)
                                )
                        )
                )
                .addDisplacementMarker(() -> {
                    // pick up specimen

                })
                .waitSeconds(0.5)

                .turn(
                        Math.toRadians(-90)
                )
                .addDisplacementMarker(() -> {
                    // drop specimen

                })
                .waitSeconds(0.5)

                // sample 3
                .lineToLinearHeading(
                        new Pose2d(
                                -50 * redPoseAdjustment,
                                45 * redPoseAdjustment,
                                Math.atan2(
                                        leftColoredSampleVector2d.getX() - (-50 * redPoseAdjustment),
                                        leftColoredSampleVector2d.getY() - (45 * redPoseAdjustment)
                                )
                        )
                )
                .addDisplacementMarker(() -> {
                    // pick up specimen

                })

                .waitSeconds(0.5)
                .turn(
                        Math.toRadians(-90)
                )

                .addDisplacementMarker(() -> {
                    // drop specimen

                })
                .waitSeconds(0.5)

                // PICK UP SPECIMEN
                .lineToLinearHeading(
                        new Pose2d(
                                -37 * redPoseAdjustment,
                                41 * redPoseAdjustment,
                                Math.atan2(
                                        placedSpecimenVector2d.getX() - (-37 * redPoseAdjustment),
                                        placedSpecimenVector2d.getY() - (41 * redPoseAdjustment)
                                )
                        )
                )
                .waitSeconds(0.5)

                .build();
    }

    public static TrajectorySequence pushSamplesTS(DriveShim drive) {
        return drive.trajectorySequenceBuilder(coloredSampleStartPose)
                // LOOP 1

//                // drop specimen
//                .lineToSplineHeading(
//                        new Pose2d(
//                                0 * redPoseAdjustment,
//                                37 * redPoseAdjustment,
//                                Math.toRadians(-90 + redAngleAdjustment)
//                        )
//                )
                .lineTo(new Vector2d(
                                -35 * redPoseAdjustment,
                                45 * redPoseAdjustment
                        )
                )
                .lineTo(new Vector2d(
                                -35 * redPoseAdjustment,
                                9 * redPoseAdjustment
                        )
                )
//                // lil spline
                .lineToLinearHeading(
                        new Pose2d(
                                -45 * redPoseAdjustment,
                                9 * redPoseAdjustment,
                                Math.toRadians(180 + redAngleAdjustment)
                        )
                )
                // push sample 1 toward observation zone
                .lineToConstantHeading(
                        new Vector2d(
                                -45 * redPoseAdjustment,
                                58 * redPoseAdjustment
                        )
                )
                // go to sample 2
                .lineToConstantHeading(
                        new Vector2d(
                                -45 * redPoseAdjustment,
                                15 * redPoseAdjustment
                        )
                )
                // lil spline
                .splineToLinearHeading(
                        new Pose2d(
                                -55 * redPoseAdjustment,
                                9 * redPoseAdjustment,
                                Math.toRadians(180 + redAngleAdjustment)
                        ),
                        Math.toRadians(180 + redAngleAdjustment)
                )
                // push sample 2 toward observation zone
                .lineToConstantHeading(
                        new Vector2d(
                                -55 * redPoseAdjustment,
                                58 * redPoseAdjustment
                        )
                )
                // go to sample 3
                .lineToConstantHeading(
                        new Vector2d(
                                -55 * redPoseAdjustment,
                                15 * redPoseAdjustment
                        )
                )
                // lil spline
                .splineToLinearHeading(
                        new Pose2d(
                                -61 * redPoseAdjustment,
                                9 * redPoseAdjustment,
                                Math.toRadians(180 + redAngleAdjustment)
                        ),
                        Math.toRadians(180 + redAngleAdjustment)
                )
                // push sample 3 toward observation zone
                .lineToConstantHeading(
                        new Vector2d(
                                -61 * redPoseAdjustment,
                                58 * redPoseAdjustment
                        )
                )
//                .setTangent(-45)
//                .splineToSplineHeading(
//                        new Pose2d(
//                                0 * redPoseAdjustment,
//                                45 * redPoseAdjustment,
//                                Math.toRadians(-180 + redAngleAdjustment)
//                        ),
//                        Math.toRadians(0 + redAngleAdjustment)
//                )
//                // go to the middle zone for a level 1 hang (i forgot what it's called)
//                .splineToSplineHeading(
//                        new Pose2d(
//                                35 * redPoseAdjustment,
//                                25 * redPoseAdjustment,
//                                Math.toRadians(90 + redAngleAdjustment)
//                        ),
//                        Math.toRadians(-90 + redAngleAdjustment)
//                )
//                .splineToSplineHeading(
//                        new Pose2d(
//                                23 * redPoseAdjustment,
//                                10 * redPoseAdjustment,
//                                Math.toRadians(0 + redAngleAdjustment)
//                        ),
//                        Math.toRadians(180 + redAngleAdjustment)
//                )
//                .addDisplacementMarker(() -> {
//
//                })
                .build();
    }

    public static TrajectorySequence neutralStraysTS(DriveShim drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)

                // drop specimen
                .lineToSplineHeading(
                        new Pose2d(
                                0 * redPoseAdjustment,
                                37 * redPoseAdjustment,
                                Math.toRadians(-90 + redAngleAdjustment)
                        )
                )

                .addDisplacementMarker(() -> {
                    // pick up sample
                })

                // LOOP 1
                // go to pickup
                .lineToSplineHeading(new Pose2d(
                        48 * redPoseAdjustment,
                        39 * redPoseAdjustment,
                        Math.toRadians(-90 + redAngleAdjustment)
                ))
                .addDisplacementMarker(() -> {
                    // pick up sample
                })

                // go to dropoff
                .lineToSplineHeading(dropSamplePose)
                .addDisplacementMarker(() -> {
                    // drop off sample
                })

                // LOOP 2
                .lineToLinearHeading(new Pose2d(
                        58 * redPoseAdjustment,
                        43 * redPoseAdjustment,
                        Math.toRadians(-90 + redAngleAdjustment)
                ))
                .addDisplacementMarker(() -> {
                    // pick up sample
                })
                .lineToSplineHeading(dropSamplePose)
                .addDisplacementMarker(() -> {
                    // drop off sample
                })

                // LOOP 3
                .lineToLinearHeading(new Pose2d(
                        52 * redPoseAdjustment,
                        27 * redPoseAdjustment,
                        Math.toRadians(0 + redAngleAdjustment)
                ))
                .addDisplacementMarker(() -> {
                    // pick up sample
                })
                .lineToSplineHeading(dropSamplePose)
                .addDisplacementMarker(() -> {
                    // drop off sample
                })
                .build();
    }

    public static TrajectorySequence submersibleCycleTS(DriveShim drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)

                // drop specimen
                .lineToSplineHeading(
                        new Pose2d(
                                0 * redPoseAdjustment,
                                37 * redPoseAdjustment,
                                Math.toRadians(-90 + redAngleAdjustment)
                        )
                )

                .addDisplacementMarker(() -> {
                    // pick up sample
                })

                // go to dropoff
                .setTangent(Math.toRadians(90 + redAngleAdjustment))
                .lineToLinearHeading(
                        dropSamplePose
                )
                .addDisplacementMarker(() -> {
                    // drop off sample
                })

                // LOOP 1
                // go back to pickup
                .setTangent(Math.toRadians(180 + 20 + redAngleAdjustment))
                .splineToLinearHeading(
                        submersiblePickUpPose,
                        Math.toRadians(-110 + redAngleAdjustment)
                )
                .addDisplacementMarker(() -> {
                    // pick up sample
                })
                // go back dropoff
                .setTangent(Math.toRadians(90 + redAngleAdjustment))
                .splineToLinearHeading(
                        dropSamplePose,
                        Math.toRadians(30 + redAngleAdjustment)
                )
                .addDisplacementMarker(() -> {
                    // drop off sample
                })

                // LOOP 2
                // go back to pickup
                .setTangent(Math.toRadians(180 + 20 + redAngleAdjustment))
                .splineToLinearHeading(
                        submersiblePickUpPose,
                        Math.toRadians(-110 + redAngleAdjustment)
                )
                .addDisplacementMarker(() -> {
                    // pick up sample
                })
                // go back dropoff
                .setTangent(Math.toRadians(90 + redAngleAdjustment))
                .splineToLinearHeading(
                        dropSamplePose,
                        Math.toRadians(30 + redAngleAdjustment)
                )
                .addDisplacementMarker(() -> {
                    // drop off sample
                })
                .build();
    }
}
