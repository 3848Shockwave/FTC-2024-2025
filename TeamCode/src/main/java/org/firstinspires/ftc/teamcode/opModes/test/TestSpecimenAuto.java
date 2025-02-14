package org.firstinspires.ftc.teamcode.opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.*;
import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.jetbrains.annotations.NotNull;

import java.lang.Math;
import java.util.HashSet;

import static org.firstinspires.ftc.teamcode.commands.SampleTransferCommandSequence.CLOSE_CLAW_WAIT;
import static org.firstinspires.ftc.teamcode.commands.SpecimenTransferCommandSequence.WAIT4;
import static org.firstinspires.ftc.teamcode.commands.TriggerSamplePickupAndTransferCommandSequence.DROP_CLOSE_WAIT;
import static org.firstinspires.ftc.teamcode.opModes.SampleAuto.HORIZONTAL_CLAW_ROLL_RIGHT_SAMPLE_POSITION;

// TODO: change to LinearOpMode if we have to
@Config
@Autonomous(name = "TEST SPECIMEN AUTO")
public class TestSpecimenAuto extends CommandOpMode {

    public static double HEADING = 180 + 50;
    public static double X = -29;
    public static double Y = 46;
    public static double CLAW_ROLL = 20;

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
                37.5,
                Math.toRadians(90)
        );


        Vector2d rightColoredSampleVector = new Vector2d(-48, 27);
        Vector2d middleColoredSampleVector = new Vector2d(-58, 27);
        Vector2d leftColoredSampleVector = new Vector2d(-68, 27);
        Vector2d placedSpecimenVector = new Vector2d(-47, 58);
        Pose2d pickUpSpecimenPose = new Pose2d(
                -46,
                46,
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
//        TrajectoryActionBuilder goToHangSpecimenTAB = drive.actionBuilder(coloredSampleStartPose)
//                .strafeToLinearHeading(
//                        new Vector2d(
//
//                                hangSpecimenPose.component1().x,
//                                hangSpecimenPose.component1().y
//                        ),
//                        hangSpecimenPose.component2()
//                )
//                .endTrajectory();

        // specimen
        // to continue off a previous command, you do:
        // TAB tab = previousTAB.fresh(). [insert trajectories here] .endTrajectory();

//        TrajectoryActionBuilder rightSampleTAB = goToHangSpecimenTAB
//                .fresh()
//                .strafeToLinearHeading(
//                        new Vector2d(-29, 46),
//                        Math.toRadians(180 + 50)
//                )
//                .endTrajectory();
        TrajectoryActionBuilder rightSampleTAB = drive.actionBuilder(hangSpecimenPose)
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(-29, 46),
                        Math.toRadians(180 + 50)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropRightSampleTAB = rightSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(135)
                )
                .endTrajectory();
        TrajectoryActionBuilder middleSampleTAB = dropRightSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(-39, 46),
                        Math.toRadians(180 + 50)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropMiddleSampleTAB = middleSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(135)
                )
                .endTrajectory();
        TrajectoryActionBuilder leftSampleTAB = dropMiddleSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(-49, 46),
                        Math.toRadians(180 + 50)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropLeftSampleTAB = leftSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(135)
                )
                .endTrajectory();


        // hang specimens
        TrajectoryActionBuilder pickUpSpecimenTAB = dropLeftSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        pickUpSpecimenPose.component1(),
                        Math.toRadians(90)
                )
                .endTrajectory();

        // move 2 in forward
        TrajectoryActionBuilder slightlyForwardTAB = pickUpSpecimenTAB
                .fresh()
                .strafeToConstantHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y + 1
                        )
                )
                .endTrajectory();

        TrajectoryActionBuilder hangSpecimenTAB = slightlyForwardTAB
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 3,
                                hangSpecimenPose.component1().y - 2
                        ),
                        Math.toRadians(-50)
                )
                .endTrajectory();


        TrajectoryActionBuilder parkTAB = hangSpecimenTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                -58,
                                55
                        ),
                        Math.toRadians(-90)
                )
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
        );

        waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
//                        new VerticalArmToSpecimenDropoffCommandSequence(intakeSubsystem, WAIT4),
//                        // wait 5 secs for team
////                        new WaitCommand(5000),
//                        // go to hang specimen position
//                        new ActionCommand(goToHangSpecimenTAB.build(), new HashSet<>()),
//                        new WaitCommand(100),
//                        // hang the specimen
//                        new SpecimenHangCommandSequence(intakeSubsystem),
//                        new WaitCommand(250),
//
//                        // right sample
                        new ActionCommand(rightSampleTAB.build(), new HashSet<>()),
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalClawRollPosition(CLAW_ROLL);
                        }),
                        new WaitCommand(250),
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem),
                        new WaitCommand(250),
                        new TriggerPickUpSampleCommandSequence(intakeSubsystem),
//                        new PickUpSampleNoExtensionCommandSequence(intakeSubsystem),
                        new ActionCommand(dropRightSampleTAB.build(), new HashSet<>()),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_POSITION)),
                        new WaitCommand(250),
                        new InstantCommand(() -> intakeSubsystem.openHorizontalClaw()),

                        // middle sample
                        new ActionCommand(middleSampleTAB.build(), new HashSet<>()),
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalClawRollPosition(CLAW_ROLL);
                        }),
                        new WaitCommand(250),
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem),
                        new WaitCommand(250),
                        new TriggerPickUpSampleCommandSequence(intakeSubsystem),
//                        new PickUpSampleNoExtensionCommandSequence(intakeSubsystem),
                        new ActionCommand(dropMiddleSampleTAB.build(), new HashSet<>()),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_POSITION)),
                        new WaitCommand(250),
                        new InstantCommand(() -> intakeSubsystem.openHorizontalClaw()),

                        // left sample
                        new ActionCommand(leftSampleTAB.build(), new HashSet<>()),
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalClawRollPosition(CLAW_ROLL);
                        }),
                        new WaitCommand(250),
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem),
                        new WaitCommand(250),
                        new TriggerPickUpSampleCommandSequence(intakeSubsystem),
//                        new PickUpSampleNoExtensionCommandSequence(intakeSubsystem),
                        new ActionCommand(dropLeftSampleTAB.build(), new HashSet<>()),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_POSITION)),
                        new WaitCommand(250),
                        new InstantCommand(() -> intakeSubsystem.openHorizontalClaw()),
                        new WaitCommand(250),

                        // put intake to hover
                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
                        // go to pick up position
                        new ActionCommand(pickUpSpecimenTAB.build(), new HashSet<>()),
//                        new WaitCommand(1000),
                        // pick up specimen
                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.INTAKE),
                        new WaitCommand(250),
                        new ActionCommand(slightlyForwardTAB.build(), new HashSet<>()),
                        // transfer specimen
                        new SpecimenTransferCommandSequence(intakeSubsystem),
                        // go to hang position
                        new ActionCommand(hangSpecimenTAB.build(), new HashSet<>()),
                        new WaitCommand(500),
                        // hang specimen
                        new SpecimenHangCommandSequence(intakeSubsystem),

                        // park
                        new ActionCommand(parkTAB.build(), new HashSet<>())
                )
        );

    }
}
