package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.HashSet;

import static org.firstinspires.ftc.teamcode.commands.SpecimenTransferCommandSequence.WAIT4;

@Config
@Autonomous(name = "SAMPLE AUTONOMOUS")
public class SampleAuto extends CommandOpMode {

    IntakeSubsystem intakeSubsystem;
    Telemetry currentTelemetry;

    public static double HORIZONTAL_CLAW_ROLL_RIGHT_SAMPLE_POSITION = 50;
    public static double VEL_CONSTRAINT = 10;
    public static double X_LEFT = 49.5;
    public static double X_MIDDLE = 58;
    public static double Y_LEFT_AND_MIDDLE = 52.5;
    public static double X_RIGHT = 52;
    public static double Y_RIGHT = 49;

    @Override
    public void initialize() {

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        intakeSubsystem = new IntakeSubsystem(hardwareMap, currentTelemetry);


        CommandScheduler.getInstance().registerSubsystem(intakeSubsystem);

        // preferred distance (+x) to eventually shift this by: 5.3 in
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
                51,
                52,
                Math.toRadians(180 + 40)
        );

        Pose2d hangSpecimenPose = new Pose2d(
                0,
                38.5,
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
        Pose2d parkPose = new Pose2d(
                22,
                10,
                Math.toRadians(0)
        );

        // CREATE DRIVE
        PinpointDrive drive = new PinpointDrive(hardwareMap, bucketStartPose);

        // and here starts the TrajectoryActionBuilders.....
        // complete roadrunner TrajectoryBuilder reference: https://cookbook.dairy.foundation/roadrunner_10/complete_trajectorybuilder_reference.html
        // sample actual roadrunner opMode: https://rr.brott.dev/docs/v1-0/guides/centerstage-auto/

        // meepmeep installation and sample file: https://github.com/acmerobotics/MeepMeep
        // TODO: head over to https://rr.brott.dev/docs/v1-0/tuning/ if you want to tune our bot for roadrunner!
        TrajectoryActionBuilder dropSampleTAB = drive.actionBuilder(bucketStartPose)
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2()
                )
                .endTrajectory();

        TrajectoryActionBuilder leftSampleTAB = dropSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(X_LEFT, Y_LEFT_AND_MIDDLE),
                        Math.toRadians(-90),
                        new TranslationalVelConstraint(10)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropSample0TAB = leftSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2(),
                        new TranslationalVelConstraint(10)
                )
                .endTrajectory();
        TrajectoryActionBuilder middleSampleTAB = dropSample0TAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(X_MIDDLE, Y_LEFT_AND_MIDDLE),
                        Math.toRadians(-90)

                )
                .endTrajectory();
        TrajectoryActionBuilder dropSample1TAB = middleSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2(),
                        new TranslationalVelConstraint(10)
                )
                .endTrajectory();
        TrajectoryActionBuilder rightSampleTAB = dropSample1TAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(X_RIGHT, Y_RIGHT),
                        Math.toRadians(-50)

                )
                .endTrajectory();
        TrajectoryActionBuilder dropSample2TAB = rightSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        dropSamplePose.component1(),
                        dropSamplePose.component2()
                )
                .endTrajectory();
        TrajectoryActionBuilder parkTAB = dropSample2TAB
                .fresh()
                .setTangent(Math.toRadians(-90))
                .splineToLinearHeading(
                        parkPose,
                        Math.toRadians(180)
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

                }),
                new RunCommand(() -> {
                    currentTelemetry.update();
                })
        );

        waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        // set vertical slide position to deposit position, after start of this command: wait 500 ms, then set vertical arm to deposit position
                        new ParallelCommandGroup(
                                // at the same time, go to the drop sample position
                                // go to drop sample
                                new ActionCommand(dropSampleTAB.build(), new HashSet<>()),
                                // set vertical slide position to transfer position
                                new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION),
                                new SequentialCommandGroup(
                                        new WaitCommand(WAIT4),
                                        // set vertical arm to deposit position
                                        new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT),
                                        // set horizontal arm to be straight up
                                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL)
                                )
                        ),
                        new WaitCommand(200),
                        // drop sample
                        new DropAndResetToIntakeCommandSequence(intakeSubsystem),
                        // go to left sample
                        new ActionCommand(leftSampleTAB.build(), new HashSet<>()),
                        new WaitCommand(300),
                        // pick up sample
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem),
                        new WaitCommand(250),
                        new TriggerSamplePickupAndTransferCommandSequence(intakeSubsystem),
                        // go to drop sample
                        new ActionCommand(dropSample0TAB.build(), new HashSet<>()),
                        new WaitCommand(200),
                        // drop sample
                        new DropAndResetToIntakeCommandSequence(intakeSubsystem),
                        new ActionCommand(middleSampleTAB.build(), new HashSet<>()),
                        new WaitCommand(250),
                        // pick up sample
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem),
                        new WaitCommand(250),
                        new TriggerSamplePickupAndTransferCommandSequence(intakeSubsystem),
                        // go to drop sample
                        new ActionCommand(dropSample1TAB.build(), new HashSet<>()),
                        new WaitCommand(200),
                        // drop sample
                        new DropAndResetToIntakeCommandSequence(intakeSubsystem),
                        new ActionCommand(rightSampleTAB.build(), new HashSet<>()),
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalClawRollPosition(HORIZONTAL_CLAW_ROLL_RIGHT_SAMPLE_POSITION);
                        }),
                        new WaitCommand(250),
                        // pick up sample
                        new TriggerSampleIntakeCommandSequence(intakeSubsystem),
                        new WaitCommand(250),
                        new TriggerSamplePickupAndTransferCommandSequence(intakeSubsystem),
                        // go to drop sample
                        new ActionCommand(dropSample2TAB.build(), new HashSet<>()),
                        new WaitCommand(200),
                        // drop sample
                        new DropAndResetToIntakeCommandSequence(intakeSubsystem),
                        new ActionCommand(parkTAB.build(), new HashSet<>()),
                        new TouchBarCommand(intakeSubsystem)
                )
        );


    }
}
