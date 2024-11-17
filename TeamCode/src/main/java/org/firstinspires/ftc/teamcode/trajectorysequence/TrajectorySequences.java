package org.firstinspires.ftc.teamcode.trajectorysequence;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceBuilder;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequenceRunner;
public class TrajectorySequences {
    // reference: https://github.com/technototes/IntoTheDeep2024/blob/main/MeepMeepTesting/src/main/java/com/example/meepmeeptesting/ViggoTesting.java#L19

    // import "drive" as an argument to return the desired Trajectory Sequence
    public static int redAngleAdjustment;
    public static int redPoseAdjustment;
    public static Pose2d bucketStartPose;
    public static Pose2d coloredSampleStartPose;
    public static Pose2d submersiblePickUpPose;
    public static Pose2d dropSamplePose;
    public static double waitForSpecimenTime = 0.0;
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
                35 * redPoseAdjustment,
                62 * redPoseAdjustment,
                Math.toRadians(-90 + redAngleAdjustment)
        );
        coloredSampleStartPose = new Pose2d(
                -35 * redPoseAdjustment,
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
    public static TrajectorySequence pushSamplesTS(SampleMecanumDrive drive) {

        return drive.trajectorySequenceBuilder(coloredSampleStartPose)
                .waitSeconds(waitForSpecimenTime)
                // LOOP 1

                // drop specimen
                .lineToSplineHeading(
                        new Pose2d(
                                0 * redPoseAdjustment,
                                37 * redPoseAdjustment,
                                Math.toRadians(-90 + redAngleAdjustment)
                        )
                )

                // go to push right-most sample
                .setTangent(Math.toRadians(180 + redAngleAdjustment))
                // straight line
                .lineTo(new Vector2d(-15 * redPoseAdjustment, 37 * redPoseAdjustment))
                .splineToSplineHeading(
                        new Pose2d(
                                -32 * redPoseAdjustment,
                                25 * redPoseAdjustment,
                                Math.toRadians(180 + redAngleAdjustment)
                        ),
                        Math.toRadians(-90 + redAngleAdjustment)
                )
                .splineToLinearHeading(
                        new Pose2d(
                                -45 * redPoseAdjustment,
                                9 * redPoseAdjustment,
                                Math.toRadians(180 + redAngleAdjustment)
                        ),
                        Math.toRadians(180 + redAngleAdjustment)
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
                .setTangent(-45)
                .splineToSplineHeading(
                        new Pose2d(
                                0 * redPoseAdjustment,
                                45 * redPoseAdjustment,
                                Math.toRadians(-180 + redAngleAdjustment)
                        ),
                        Math.toRadians(0 + redAngleAdjustment)
                )
                // go to the middle zone for a level 1 hang (i forgot what it's called)
                .splineToSplineHeading(
                        new Pose2d(
                                35 * redPoseAdjustment,
                                25 * redPoseAdjustment,
                                Math.toRadians(90 + redAngleAdjustment)
                        ),
                        Math.toRadians(-90 + redAngleAdjustment)
                )
                .splineToSplineHeading(
                        new Pose2d(
                                23 * redPoseAdjustment,
                                10 * redPoseAdjustment,
                                Math.toRadians(0 + redAngleAdjustment)
                        ),
                        Math.toRadians(180 + redAngleAdjustment)
                )
                .addDisplacementMarker(() -> {

                })
                .build();
    }

    public static TrajectorySequence neutralStraysTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)
                .waitSeconds(waitForSpecimenTime)

                // drop specimen
                .lineToSplineHeading(
                        new Pose2d(
                                0 * redPoseAdjustment,
                                37 * redPoseAdjustment,
                                Math.toRadians(-90 + redAngleAdjustment)
                        )
                )

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

    public static TrajectorySequence submersibleCycleTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)
                .waitSeconds(waitForSpecimenTime)

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
                .splineToLinearHeading(submersiblePickUpPose,
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

