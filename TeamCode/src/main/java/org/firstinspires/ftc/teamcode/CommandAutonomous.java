package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.RunCommand;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.RunTrajectorySequenceCommand;
import org.firstinspires.ftc.teamcode.commands.SpecimenDropCommandSequence;
import org.firstinspires.ftc.teamcode.commands.SpecimenTransferCommandSequence;
import org.firstinspires.ftc.teamcode.commands.VerticalArmToSpecimenDropoffCommandSequence;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.RunVerticalSlideCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.constants.SpecimenConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import static org.firstinspires.ftc.teamcode.commands.SampleTransferCommandSequence.CLOSE_CLAW_WAIT;

//@Photon
@Autonomous(name = "COMMAND AUTONOMOUS (use this please now!)")
@Config
public class CommandAutonomous extends CommandOpMode {


    IntakeSubsystem intakeSubsystem;
//    DriveSubsystem driveSubsystem;

    Telemetry currentTelemetry;

    public static int CURRENT_TRAJECTORY_SEQUENCE = 3;


    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
//        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = coloredSampleStartPose;
        drive.setPoseEstimate(startPose);

        register(intakeSubsystem);


        // initially set horizontal arm position to hover
        schedule(new InstantCommand(() -> {

            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_EXTENSION);
//            intakeSubsystem.setVerticalWristPitchPosition(SpecimenConstants.VERTICAL_WRIST_PITCH_SPECIMEN_DROPOFF_POSITION);
//            intakeSubsystem.setVerticalClawPitchPosition(SpecimenConstants.VERTICAL_CLAW_PITCH_SPECIMEN_DROPOFF_POSITION);
//            intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_DEPOSIT_POSITION);
            intakeSubsystem.closeVerticalClaw();

        }), new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL));

        schedule(
                new RunVerticalSlideCommand(intakeSubsystem, currentTelemetry)
        );

        // update telemetry
        schedule(
                new RunCommand(() -> {

                    telemetry.addLine("is running");

                    currentTelemetry.update();
                })
        );

        waitForStart();

        if (isStarted()) {
                switch (CURRENT_TRAJECTORY_SEQUENCE) {
                    case 0:
                        schedule(new RunTrajectorySequenceCommand(intakeSubsystem, pushSamplesTS(drive), drive));
                        break;
                    case 1:
                        schedule(new RunTrajectorySequenceCommand(intakeSubsystem, neutralStraysTS(drive), drive));
                        break;
                    case 2:
                        schedule(new RunTrajectorySequenceCommand(intakeSubsystem, submersibleCycleTS(drive), drive));
                        break;
                    case 3:
                        schedule(new RunTrajectorySequenceCommand(intakeSubsystem, moveAndHangSpecimensTS(drive), drive));
                        break;

                }
        }


    }


    // TRAJECTORY SEQUENCE STUFF
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
                    -(placedSpecimenVector.getX() - (-37)),
                    -(placedSpecimenVector.getY() - (41))
            ) - Math.toRadians(20)
    );


    public TrajectorySequence moveAndHangSpecimensTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(coloredSampleStartPose)
                // put vertical arm to specimen dropoff
                .addDisplacementMarker(() -> {
                    schedule(
                            new VerticalArmToSpecimenDropoffCommandSequence(intakeSubsystem, SpecimenTransferCommandSequence.WAIT4)
                    );

                })
                .waitSeconds(3)

                // go to specimen dropoff pose
                .lineToSplineHeading(hangSpecimenPose)
                .waitSeconds(1)

                // hang specimen
                .addDisplacementMarker(() -> {
                    schedule(
                            new SpecimenDropCommandSequence(intakeSubsystem)
                    );

                })
                .waitSeconds(1)

                // sample 1
                .lineToLinearHeading(
                        new Pose2d(
                                -28,
                                45,
                                Math.atan2(
                                        rightColoredSampleVector.getX() - (-28),
                                        rightColoredSampleVector.getY() - (45)
                                )
                        )
                )
                .addDisplacementMarker(() -> {
                    // pick up specimen
                    schedule(

                            // extend slides
                            new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_EXTENSION)),
                            new WaitCommand(1000),
                            // hover over sample
                            new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
                            new WaitCommand(1000),
                            // drop to touch sample
                            new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),

                            // close horizontal arm claw to pick up the sample
                            new InstantCommand(intakeSubsystem::closeHorizontalClaw),
                            // (wait until ^ done)
                            new WaitCommand(CLOSE_CLAW_WAIT),
                            // set horizontal arm to hover-over-sample position
                            new InstantCommand(() -> intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION))

                    );

                })
                .waitSeconds(0.5)

                .turn(
                        Math.toRadians(-90)
                )
                // drop specimen
                .addDisplacementMarker(() -> {
                    new InstantCommand(() -> intakeSubsystem.openHorizontalClaw());

                })

                .waitSeconds(0.5)

                // sample 2
                .lineToLinearHeading(
                        new Pose2d(
                                -40,
                                45,
                                Math.atan2(
                                        middleColoredSampleVector.getX() - (-40),
                                        middleColoredSampleVector.getY() - (45)
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
                                -50,
                                45,
                                Math.atan2(
                                        leftColoredSampleVector.getX() - (-50),
                                        leftColoredSampleVector.getY() - (45)
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
                .lineToLinearHeading(pickUpSpecimenPose)
                .addDisplacementMarker(() -> {
                    // pick up specimen
                })
                .waitSeconds(0.5)

                // hang specimen
                .lineToSplineHeading(hangSpecimenPose)
                .addDisplacementMarker(() -> {
                    // hang specimen
                })
                .waitSeconds(3)

                // PICK UP SPECIMEN 2
                .lineToLinearHeading(pickUpSpecimenPose)
                .addDisplacementMarker(() -> {
                    // pick up specimen
                })
                .waitSeconds(0.5)

                // hang specimen
                .lineToSplineHeading(hangSpecimenPose)
                .addDisplacementMarker(() -> {
                    // hang specimen
                })
                .waitSeconds(3)

                // PICK UP SPECIMEN 3
                .lineToLinearHeading(pickUpSpecimenPose)
                .addDisplacementMarker(() -> {
                    // pick up specimen
                })
                .waitSeconds(0.5)

                // hang specimen
                .lineToSplineHeading(hangSpecimenPose)
                .addDisplacementMarker(() -> {
                    // hang specimen
                })
                .waitSeconds(3)

                // TODO: park

                .build();
    }

    public TrajectorySequence pushSamplesTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(coloredSampleStartPose)
                // LOOP 1

                // hang specimen
                .lineToSplineHeading(hangSpecimenPose)
                .addDisplacementMarker(() -> {
                    // hang specimen
                })
                .waitSeconds(3)

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

                // TODO: park

//                .setTangent(-45)
//                .splineToSplineHeading(
//                        new Pose2d(
//                                0,
//                                45,
//                                Math.toRadians(-180)
//                        ),
//                        Math.toRadians(0)
//                )
//                // go to the middle zone for a level 1 hang (i forgot what it's called)
//                .splineToSplineHeading(
//                        new Pose2d(
//                                35,
//                                25,
//                                Math.toRadians(90)
//                        ),
//                        Math.toRadians(-90)
//                )
//                .splineToSplineHeading(
//                        new Pose2d(
//                                23,
//                                10,
//                                Math.toRadians(0)
//                        ),
//                        Math.toRadians(180)
//                )
//                .addDisplacementMarker(() -> {
//
//                })
                .build();
    }

    public TrajectorySequence neutralStraysTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)

                // hang specimen
                .lineToSplineHeading(hangSpecimenPose)
                .addDisplacementMarker(() -> {
                    // hang specimen
                })
                .waitSeconds(3)

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

                // TODO: park

                .build();
    }

    public TrajectorySequence submersibleCycleTS(SampleMecanumDrive drive) {
        return drive.trajectorySequenceBuilder(bucketStartPose)

                // hang specimen
                .lineToSplineHeading(hangSpecimenPose)
                .addDisplacementMarker(() -> {
                    // hang specimen
                })
                .waitSeconds(3)

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
                .build();
    }
}
