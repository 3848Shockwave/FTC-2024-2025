package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.*;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.jetbrains.annotations.NotNull;

import java.lang.Math;
import java.util.HashSet;

import static org.firstinspires.ftc.teamcode.commands.SpecimenTransferCommandSequence.WAIT4;

// TODO: change to LinearOpMode if we have to
@Config
@Autonomous(name = "COMMAND AUTONOMOUS (use this please now!)")
public class CommandAutonomous extends CommandOpMode {


    IntakeSubsystem intakeSubsystem;
    Telemetry currentTelemetry;

    @Override
    public void initialize() {

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        intakeSubsystem = new IntakeSubsystem(hardwareMap, currentTelemetry);


        CommandScheduler.getInstance().registerSubsystem(intakeSubsystem);

        // TABS go here
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
                39,
                Math.toRadians(90)
        );


        Vector2d rightColoredSampleVector = new Vector2d(-48, 27);
        Vector2d middleColoredSampleVector = new Vector2d(-58, 27);
        Vector2d leftColoredSampleVector = new Vector2d(-68, 27);
        Vector2d placedSpecimenVector = new Vector2d(-47, 58);
        Pose2d pickUpSpecimenPose = new Pose2d(
                -46,
                47,
                Math.atan2(
                        -(placedSpecimenVector.x - (-37)),
                        -(placedSpecimenVector.y - (41))
                ) - Math.toRadians(20)
        );

        // CREATE DRIVE
        Pose2d startPose = coloredSampleStartPose;
        PinpointDrive drive = new PinpointDrive(hardwareMap, coloredSampleStartPose);

        // and here starts the TrajectoryActionBuilders.....
        // complete roadrunner TrajectoryBuilder reference: https://cookbook.dairy.foundation/roadrunner_10/complete_trajectorybuilder_reference.html
        // sample actual roadrunner opMode: https://rr.brott.dev/docs/v1-0/guides/centerstage-auto/

        // meepmeep installation and sample file: https://github.com/acmerobotics/MeepMeep
        // TODO: head over to https://rr.brott.dev/docs/v1-0/tuning/ if you want to tune our bot for roadrunner!
        TrajectoryActionBuilder goToHangSpecimenTAB = drive.actionBuilder(coloredSampleStartPose)
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
                .setTangent(Math.toRadians(0))
                .strafeToConstantHeading(
                        new Vector2d(-35, 39)
                )
                // down
                .strafeToConstantHeading(
                        new Vector2d(-35, 13)
                )
                // left
                .strafeToConstantHeading(
                        new Vector2d(-47, 13)
                )
                // up
                .strafeToConstantHeading(
                        new Vector2d(-47, 55)
                )

                // middle sample
                // down
                .strafeToConstantHeading(
                        new Vector2d(-47, 13)
                )
                // left
                .strafeToConstantHeading(
                        new Vector2d(-57, 13)
                )
                // up
                .strafeToConstantHeading(
                        new Vector2d(-57, 55)
                )
                // left sample
                // down
                .strafeToConstantHeading(
                        new Vector2d(-57, 13)
                )
                // left
                .strafeToConstantHeading(
                        new Vector2d(-61, 13)
                )
                // up
                .strafeToConstantHeading(
                        new Vector2d(-61, 55)
                )
                .endTrajectory();

        // hang specimens
        TrajectoryActionBuilder pickUpSpecimenTAB_0 = pushSamplesTAB
                .fresh()
                .setTangent(Math.toRadians(-90))
                .splineToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 6
                        ),
                        Math.toRadians(90)
                )
                .waitSeconds(1.5)
                .strafeToConstantHeading(
                        pickUpSpecimenPose.component1()
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_0 = pickUpSpecimenTAB_0
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 3,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-30)
                )
                .endTrajectory();

        TrajectoryActionBuilder pickUpSpecimenTAB_1 = hangSpecimenTAB_0
                .fresh()
                .setTangent(Math.toRadians(-90))
                .strafeToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 6
                        )
                )
                .waitSeconds(1.5)
                .strafeToConstantHeading(
                        pickUpSpecimenPose.component1()
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_1 = pickUpSpecimenTAB_1
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 5,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-30)
                )
                .endTrajectory();

        TrajectoryActionBuilder pickUpSpecimenTAB_2 = hangSpecimenTAB_1
                .fresh()
                .setTangent(Math.toRadians(-90))
                .strafeToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y - 6
                        )
                )
                .waitSeconds(1.5)
                .strafeToConstantHeading(
                        pickUpSpecimenPose.component1()
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB_2 = pickUpSpecimenTAB_2
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 7,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-30)
                )
                .endTrajectory();

        TrajectoryActionBuilder parkPSHS_TAB = hangSpecimenTAB_2
                .fresh()
                .strafeToConstantHeading(
                        new Vector2d(
                                -58,
                                55
                        )
                )
                .turn(Math.toRadians(180))
                .endTrajectory();

        // INIT ACTIONS
        CommandScheduler.getInstance().schedule(
                new InstantCommand(() -> {

                    intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION);
                    intakeSubsystem.closeVerticalClaw();

                    intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_SPECIMEN_DROPOFF_POSITION);

                })
//                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL)
        );

        waitForStart();

        CommandScheduler.getInstance().schedule(
                new ActionCommand(
                        drive.actionBuilder(coloredSampleStartPose)
                                .turnTo(Math.toRadians(180))
                                .build(),
                        new HashSet<>()
                )
        );
//        CommandScheduler.getInstance().schedule(
//                new SequentialCommandGroup(
//                        new VerticalArmToSpecimenDropoffCommandSequence(intakeSubsystem, WAIT4),
//                        // go to hang specimen position
//                        new ActionCommand(goToHangSpecimenTAB.build(), new HashSet<>()),
//                        new WaitCommand(1000),
//                        // hang the specimen
//                        new SpecimenHangCommandSequence(intakeSubsystem),
//                        new WaitCommand(1000),
//                        // push all the samples
//                        new ActionCommand(pushSamplesTAB.build(), new HashSet<>()),
//                        new WaitCommand(1000),
//
//                        // put intake to hover
//                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
//                        // go to pick up position
//                        new ActionCommand(pickUpSpecimenTAB_0.build(), new HashSet<>()),
//                        new WaitCommand(1000),
//                        // pick up specimen
//                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
//                        new WaitCommand(1000),
//                        // transfer specimen
//                        new SpecimenTransferCommandSequence(intakeSubsystem),
//                        // go to hang position
//                        new ActionCommand(hangSpecimenTAB_0.build(), new HashSet<>()),
//                        new WaitCommand(1000),
//                        // hang specimen
//                        new SpecimenHangCommandSequence(intakeSubsystem),
//
//                        // repeat #1
//                        // put intake to hover
//                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
//                        // go to pick up position
//                        new ActionCommand(pickUpSpecimenTAB_1.build(), new HashSet<>()),
//                        new WaitCommand(1000),
//                        // pick up specimen
//                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
//                        new WaitCommand(1000),
//                        // transfer specimen
//                        new SpecimenTransferCommandSequence(intakeSubsystem),
//                        // go to hang position
//                        new ActionCommand(hangSpecimenTAB_1.build(), new HashSet<>()),
//                        new WaitCommand(1000),
//                        // hang specimen
//                        new SpecimenHangCommandSequence(intakeSubsystem),
//
//                        // repeat #2
//                        // put intake to hover
//                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
//                        // go to pick up position
//                        new ActionCommand(pickUpSpecimenTAB_2.build(), new HashSet<>()),
//                        // pick up specimen
//                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
//                        new WaitCommand(1000),
//                        // transfer specimen
//                        new SpecimenTransferCommandSequence(intakeSubsystem),
//                        // go to hang position
//                        new ActionCommand(hangSpecimenTAB_2.build(), new HashSet<>()),
//                        // hang specimen
//                        new SpecimenHangCommandSequence(intakeSubsystem),
//
//                        // park
//                        new ActionCommand(parkPSHS_TAB.build(), new HashSet<>())
//                )
//        );

    }
}
