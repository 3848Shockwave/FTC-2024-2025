package org.firstinspires.ftc.teamcode.opModes.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.*;
import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.lang.Math;
import java.util.HashSet;

import static org.firstinspires.ftc.teamcode.opModes.SampleAuto.*;

@Config
@Autonomous(name = "TEST SAMPLE AUTONOMOUS")
public class TestSampleAuto extends CommandOpMode {

    IntakeSubsystem intakeSubsystem;
    Telemetry currentTelemetry;
    public static double X_TEST = 11.5;

    public static double VEL_CONSTRAINT = 10;

    @Override
    public void initialize() {

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        intakeSubsystem = new IntakeSubsystem(hardwareMap, currentTelemetry);


        CommandScheduler.getInstance().registerSubsystem(intakeSubsystem);

        // copy from here
        Pose2d bucketStartPose = new Pose2d(
                11.5,
                62,
                Math.toRadians(-90)
        );



        // CREATE DRIVE
        PinpointDrive drive = new PinpointDrive(hardwareMap, bucketStartPose);

        // and here starts the TrajectoryActionBuilders.....
        // complete roadrunner TrajectoryBuilder reference: https://cookbook.dairy.foundation/roadrunner_10/complete_trajectorybuilder_reference.html
        // sample actual roadrunner opMode: https://rr.brott.dev/docs/v1-0/guides/centerstage-auto/

        // meepmeep installation and sample file: https://github.com/acmerobotics/MeepMeep
        // TODO: head over to https://rr.brott.dev/docs/v1-0/tuning/ if you want to tune our bot for roadrunner!
        TrajectoryActionBuilder rightSampleTAB = drive.actionBuilder(bucketStartPose)
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(40, 50),
                        Math.toRadians(-50)

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
                        new ActionCommand(drive.actionBuilder(bucketStartPose)
                                .fresh()
//                                .strafeToLinearHeading()
                                .build(), new HashSet<>())
//                        // drop sample
//                        new DropAndResetToIntakeCommandSequence(intakeSubsystem),
//                        new ActionCommand(rightSampleTAB.build(), new HashSet<>()),
//                        new InstantCommand(() -> {
//                            intakeSubsystem.setHorizontalClawRollPosition(HORIZONTAL_CLAW_ROLL_RIGHT_SAMPLE_POSITION);
//                        }),
//                        new WaitCommand(250),
//                        // pick up sample
//                        new TriggerSampleIntakeCommandSequence(intakeSubsystem),
//                        new WaitCommand(250),
//                        new TriggerSamplePickupAndTransferCommandSequence(intakeSubsystem)
                )
        );


    }
}
