package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;


/*
/**
 * WHEN COPYING THIS CLASS FOR ROADRUNNER IMPLEMENTATION, MAKE SURE TO CHANGE EVERYWHERE YOU SEE:
 * "DriveShim"
 * TO THE NAME OF YOUR DRIVE CLASS, E.G. "SampleMechanumDrive"

 * ALL OF THIS CODE ASSUMES THE ROBOT IS BLUE, but since the field is symmetric it should work for red as well
 */
public class TrajectorySequences {
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
            Math.toRadians(-90)
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


    public static TrajectorySequence pushSamplesTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(coloredSampleStartPose)
                // LOOP 1

//                // drop specimen
//                .lineToSplineHeading(
//                        new Pose2d(
//                                0 * redPoseAdjustment,
//                                37 * redPoseAdjustment,
//                                Math.toRadians(-90)
//                        )
//                )
                .lineTo(new Vector2d(
                                -35,
                                45
                        )
                )
                .lineTo(new Vector2d(
                                -35,
                                9
                        )
                )
//                // lil spline
                .lineToLinearHeading(
                        new Pose2d(
                                -45,
                                9,
                                Math.toRadians(180)
                        )
                )
                // push sample 1 toward observation zone
                .lineToConstantHeading(
                        new Vector2d(
                                -45,
                                58
                        )
                )
                // go to sample 2
                .lineToConstantHeading(
                        new Vector2d(
                                -45,
                                15
                        )
                )
                // lil spline
                .splineToLinearHeading(
                        new Pose2d(
                                -55,
                                9,
                                Math.toRadians(180)
                        ),
                        Math.toRadians(180)
                )
                // push sample 2 toward observation zone
                .lineToConstantHeading(
                        new Vector2d(
                                -55,
                                58
                        )
                )
                // go to sample 3
                .lineToConstantHeading(
                        new Vector2d(
                                -55,
                                15
                        )
                )
                // lil spline
                .splineToLinearHeading(
                        new Pose2d(
                                -61,
                                9,
                                Math.toRadians(180)
                        ),
                        Math.toRadians(180)
                )
                // push sample 3 toward observation zone
                .lineToConstantHeading(
                        new Vector2d(
                                -61,
                                58
                        )
                )
//                .setTangent(-45)
//                .splineToSplineHeading(
//                        new Pose2d(
//                                0 ,
//                                45 ,
//                                Math.toRadians(-180 + redAngleAdjustment)
//                        ),
//                        Math.toRadians(0 + redAngleAdjustment)
//                )
//                // go to the middle zone for a level 1 hang (i forgot what it's called)
//                .splineToSplineHeading(
//                        new Pose2d(
//                                35 ,
//                                25 ,
//                                Math.toRadians(90 + redAngleAdjustment)
//                        ),
//                        Math.toRadians(-90 + redAngleAdjustment)
//                )
//                .splineToSplineHeading(
//                        new Pose2d(
//                                23 ,
//                                10 ,
//                                Math.toRadians(0 + redAngleAdjustment)
//                        ),
//                        Math.toRadians(180 + redAngleAdjustment)
//                )
//                .addDisplacementMarker(() -> {
//
//                })
                .build();
    }

    public static TrajectorySequence neutralStraysTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)

                // drop specimen
                .lineToSplineHeading(
                        new Pose2d(
                                0,
                                37,
                                Math.toRadians(-90)
                        )
                )

                .addDisplacementMarker(() -> {
                    // pick up sample
                })

                // LOOP 1
                // go to pickup
                .lineToSplineHeading(new Pose2d(
                        48,
                        39,
                        Math.toRadians(-90)
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
                        58,
                        43,
                        Math.toRadians(-90)
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
                        52,
                        27,
                        Math.toRadians(0)
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

    public static TrajectorySequence submersibleCycleTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)

                // drop specimen
                .lineToSplineHeading(
                        new Pose2d(
                                0,
                                37,
                                Math.toRadians(-90)
                        )
                )

                .addDisplacementMarker(() -> {
                    // pick up sample
                })

                // go to dropoff
                .setTangent(Math.toRadians(90))
                .lineToLinearHeading(
                        dropSamplePose
                )
                .addDisplacementMarker(() -> {
                    // drop off sample
                })

                // LOOP 1
                // go back to pickup
                .setTangent(Math.toRadians(180 + 20))
                .splineToLinearHeading(
                        submersiblePickUpPose,
                        Math.toRadians(-110)
                )
                .addDisplacementMarker(() -> {
                    // pick up sample
                })
                // go back dropoff
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(
                        dropSamplePose,
                        Math.toRadians(30)
                )
                .addDisplacementMarker(() -> {
                    // drop off sample
                })

                // LOOP 2
                // go back to pickup
                .setTangent(Math.toRadians(180 + 20))
                .splineToLinearHeading(submersiblePickUpPose,
                        Math.toRadians(-110)
                )
                .addDisplacementMarker(() -> {
                    // pick up sample
                })
                // go back dropoff
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(
                        dropSamplePose,
                        Math.toRadians(30)
                )
                .addDisplacementMarker(() -> {
                    // drop off sample
                })
                .build();
    }
}
