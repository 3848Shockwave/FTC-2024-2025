package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.ActionCommand;
import org.firstinspires.ftc.teamcode.commands.PickUpSampleCommandSequence;
import org.firstinspires.ftc.teamcode.commands.SpecimenDropCommandSequence;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.RunVerticalSlideCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.lang.Math;
import java.util.HashSet;

@Autonomous(name = "COMMAND AUTONOMOUS (use this please now!)")
@Config
// TODO: change to LinearOpMode if we have to
public class CommandAutonomous extends OpMode {


    IntakeSubsystem intakeSubsystem;
    Telemetry currentTelemetry;

    public static int CURRENT_TRAJECTORY_SEQUENCE = 3;


    @Override
    public void init() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
//        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        Pose2d startPose = coloredSampleStartPose;
        MecanumDrive drive = new MecanumDrive(hardwareMap, coloredSampleStartPose);

        CommandScheduler.getInstance().registerSubsystem(intakeSubsystem);

        // initially set horizontal arm position to hover
        CommandScheduler.getInstance().schedule(
                new InstantCommand(() -> {

                    intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_EXTENSION);
                    intakeSubsystem.closeVerticalClaw();

                }),
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL),
                new RunVerticalSlideCommand(intakeSubsystem, currentTelemetry),
                new RunCommand(() -> {

                    telemetry.addLine("is running");

                    currentTelemetry.update();
                })
        );


        // TABS go here
        TrajectoryActionBuilder goToHangSpecimenTAB = drive.actionBuilder(coloredSampleStartPose)
                .strafeToLinearHeading(
                        hangSpecimenPose.component1(),
                        hangSpecimenPose.component2()
                )
                .endTrajectory();

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

        CommandScheduler.getInstance().schedule(new SequentialCommandGroup(
                new ActionCommand(goToHangSpecimenTAB.build(), new HashSet<>()),
                new SpecimenDropCommandSequence(intakeSubsystem),
                new ActionCommand(goToRightSampleTAB.build(), new HashSet<>()),
                new PickUpSampleCommandSequence(intakeSubsystem),
                new ActionCommand(turn90_0TAB.build(), new HashSet<>())
        ));


//        if (isStarted()) {
//            switch (CURRENT_TRAJECTORY_SEQUENCE) {
//                    case 0:
//                        CommandScheduler.getInstance().schedule(new RunTrajectorySequenceCommand(intakeSubsystem, pushSamplesTS(drive), drive));
//                        break;
//                    case 1:
//                        CommandScheduler.getInstance().schedule(new RunTrajectorySequenceCommand(intakeSubsystem, neutralStraysTS(drive), drive));
//                        break;
//                    case 2:
//                        CommandScheduler.getInstance().schedule(new RunTrajectorySequenceCommand(intakeSubsystem, submersibleCycleTS(drive), drive));
//                        break;
//                    case 3:
//                        CommandScheduler.getInstance().schedule(new RunTrajectorySequenceCommand(intakeSubsystem, moveAndHangSpecimensTS(drive), drive));
//                        break;
//
//            }
//        }


    }

    @Override
    public void loop() {

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
                    -(placedSpecimenVector.x - (-37)),
                    -(placedSpecimenVector.y - (41))
            ) - Math.toRadians(20)
    );


//    public TrajectorySequence moveAndHangSpecimensTS(MecanumDrive drive) {
//        return drive.traj(coloredSampleStartPose)
//                // put vertical arm to specimen dropoff
//                .addDisplacementMarker(() -> {
//                    schedule(
//                            new VerticalArmToSpecimenDropoffCommandSequence(intakeSubsystem, SpecimenTransferCommandSequence.WAIT4)
//                    );
//
//                })
//                .waitSeconds(3)
//
//                // go to specimen dropoff pose
//                .lineToSplineHeading(hangSpecimenPose)
//                .waitSeconds(1)
//
//                // hang specimen
//                .addDisplacementMarker(() -> {
//                    schedule(
//                            new SpecimenDropCommandSequence(intakeSubsystem)
//                    );
//
//                })
//                .waitSeconds(1)
//
//                // sample 1
//                .lineToLinearHeading(
//                        new Pose2d(
//                                -28,
//                                45,
//                                Math.atan2(
//                                        rightColoredSampleVector.getX() - (-28),
//                                        rightColoredSampleVector.getY() - (45)
//                                )
//                        )
//                )
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//                    schedule(
//
//                            // extend slides
//                            new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_EXTENSION)),
//                            new WaitCommand(1000),
//                            // hover over sample
//                            new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
//                            new WaitCommand(1000),
//                            // drop to touch sample
//                            new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
//
//                            // close horizontal arm claw to pick up the sample
//                            new InstantCommand(intakeSubsystem::closeHorizontalClaw),
//                            // (wait until ^ done)
//                            new WaitCommand(CLOSE_CLAW_WAIT),
//                            // set horizontal arm to hover-over-sample position
//                            new InstantCommand(() -> intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION))
//
//                    );
//
//                })
//                .waitSeconds(0.5)
//
//                .turn(
//                        Math.toRadians(-90)
//                )
//                // drop specimen
//                .addDisplacementMarker(() -> {
//                    new InstantCommand(() -> intakeSubsystem.openHorizontalClaw());
//
//                })
//
//                .waitSeconds(0.5)
//
//                // sample 2
//                .lineToLinearHeading(
//                        new Pose2d(
//                                -40,
//                                45,
//                                Math.atan2(
//                                        middleColoredSampleVector.getX() - (-40),
//                                        middleColoredSampleVector.getY() - (45)
//                                )
//                        )
//                )
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//
//                })
//                .waitSeconds(0.5)
//
//                .turn(
//                        Math.toRadians(-90)
//                )
//                .addDisplacementMarker(() -> {
//                    // drop specimen
//
//                })
//                .waitSeconds(0.5)
//
//                // sample 3
//                .lineToLinearHeading(
//                        new Pose2d(
//                                -50,
//                                45,
//                                Math.atan2(
//                                        leftColoredSampleVector.getX() - (-50),
//                                        leftColoredSampleVector.getY() - (45)
//                                )
//                        )
//                )
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//
//                })
//
//                .waitSeconds(0.5)
//                .turn(
//                        Math.toRadians(-90)
//                )
//
//                .addDisplacementMarker(() -> {
//                    // drop specimen
//
//                })
//                .waitSeconds(0.5)
//
//                // PICK UP SPECIMEN
//                .lineToLinearHeading(pickUpSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//                })
//                .waitSeconds(0.5)
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//                .waitSeconds(3)
//
//                // PICK UP SPECIMEN 2
//                .lineToLinearHeading(pickUpSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//                })
//                .waitSeconds(0.5)
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//                .waitSeconds(3)
//
//                // PICK UP SPECIMEN 3
//                .lineToLinearHeading(pickUpSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // pick up specimen
//                })
//                .waitSeconds(0.5)
//
//                // hang specimen
//                .lineToSplineHeading(hangSpecimenPose)
//                .addDisplacementMarker(() -> {
//                    // hang specimen
//                })
//                .waitSeconds(3)
//
//                // TODO: park
//
//                .build();
//    }

}
