package org.firstinspires.ftc.teamcode.opModes;

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

import java.io.File;
import java.lang.Math;
import java.util.HashSet;

import static org.firstinspires.ftc.teamcode.commands.SpecimenTransferCommandSequence.WAIT4;

// TODO: change to LinearOpMode if we have to
@Config
@Autonomous(name = "SPECIMEN AUTONOMOUS (currently in testing)")
public class SpecimenAuto extends CommandOpMode {

    File headingFile = new File("./savedHeading");

    public static double RIGHT_X = -37;
    public static double Y = 29;
    public static double RIGHT_SAMPLE_HEADING = 200;
    public static double RIGHT_TURN_HEADING = 100;
    public static double MIDDLE_X = -47;
    public static double MIDDLE_SAMPLE_HEADING = 200;
    public static double MIDDLE_TURN_HEADING = 100;
    public static double LEFT_X = -57;
    public static double LEFT_SAMPLE_HEADING = 200;
    public static double CLAW_ROLL = 20;
    public static double VEL_CONSTRAINT = 30;
    public static long THROW_WAIT = 200;
    public static double HORIZONTAL_SLIDE_DROP_EXTENSION = 60;
    public static double PICK_UP_SPECIMEN_Y = 47;

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
                37,
                Math.toRadians(90)
        );


        Vector2d rightColoredSampleVector = new Vector2d(-48, 27);
        Vector2d middleColoredSampleVector = new Vector2d(-58, 27);
        Vector2d leftColoredSampleVector = new Vector2d(-68, 27);
        Vector2d placedSpecimenVector = new Vector2d(-47, 58);
        Pose2d pickUpSpecimenPose = new Pose2d(
                -48,
                PICK_UP_SPECIMEN_Y,
                Math.toRadians(90)
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
                        new Vector2d(

                                hangSpecimenPose.component1().x,
                                hangSpecimenPose.component1().y
                        ),
                        hangSpecimenPose.component2(),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();

        // specimen
        // to continue off a previous command, you do:
        // TAB tab = previousTAB.fresh(). [insert trajectories here] .endTrajectory();

        TrajectoryActionBuilder rightSampleTAB = goToHangSpecimenTAB
                .fresh()
                .setTangent(Math.toRadians(180 - 20))
                .splineToLinearHeading(
                        new Pose2d(
                                RIGHT_X,
                                Y,
                                Math.toRadians(RIGHT_SAMPLE_HEADING)
                        ),
                        Math.toRadians(180 + 40),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();
        TrajectoryActionBuilder dropRightSampleTAB = rightSampleTAB
                .fresh()
                .turnTo(
                        Math.toRadians(RIGHT_TURN_HEADING)
                )
                .endTrajectory();
        TrajectoryActionBuilder middleSampleTAB = dropRightSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(MIDDLE_X, Y),
                        Math.toRadians(MIDDLE_SAMPLE_HEADING),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();
//        TrajectoryActionBuilder dropMiddleSampleTAB = middleSampleTAB
//                .fresh()
//                .turnTo(
//                        Math.toRadians(MIDDLE_TURN_HEADING)
//                )
//                .endTrajectory();
//        TrajectoryActionBuilder leftSampleTAB = dropMiddleSampleTAB
//                .fresh()
//                .strafeToLinearHeading(
//                        new Vector2d(LEFT_X, Y),
//                        Math.toRadians(LEFT_SAMPLE_HEADING),
//                        new TranslationalVelConstraint(VEL_CONSTRAINT)
//                )
//                .endTrajectory();

        // hang specimens
        TrajectoryActionBuilder goToWaitForSpecimenPoseTAB0 = middleSampleTAB
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y
                        ),
                        Math.toRadians(90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder hangSpecimenTAB0 = goToWaitForSpecimenPoseTAB0
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 3,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-50),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();

        // hang specimens
        TrajectoryActionBuilder goToWaitForSpecimenPoseTAB1 = hangSpecimenTAB0
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y
                        ),
                        Math.toRadians(90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder hangSpecimenTAB1 = goToWaitForSpecimenPoseTAB1
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 6,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-50),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();
        // hang specimens
        TrajectoryActionBuilder goToWaitForSpecimenPoseTAB2 = hangSpecimenTAB1
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                pickUpSpecimenPose.component1().x,
                                pickUpSpecimenPose.component1().y
                        ),
                        Math.toRadians(90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder hangSpecimenTAB2 = goToWaitForSpecimenPoseTAB2
                .fresh()
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(
                        new Vector2d(
                                hangSpecimenPose.component1().x - 9,
                                hangSpecimenPose.component1().y
                        ),
                        Math.toRadians(-50),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder parkTAB = hangSpecimenTAB2
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                -40,
                                60
                        ),
                        Math.toRadians(-90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
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
                        new VerticalArmToSpecimenDropoffCommandSequence(intakeSubsystem, WAIT4),
                        // wait 5 secs for team
//                        new WaitCommand(5000),
                        // go to hang specimen position
                        new ActionCommand(goToHangSpecimenTAB.build(), new HashSet<>()),
                        // hang the specimen
                        new SpecimenHangCommandSequence(intakeSubsystem),

                        // right sample
                        // slides to min, claw open, roll to place
                        new InstantCommand(() ->
                        {
                            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION);
                            intakeSubsystem.openHorizontalClaw();
                            intakeSubsystem.setHorizontalClawRollPosition(CLAW_ROLL);
                        }),
                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
                        // go to sample
                        new ActionCommand(rightSampleTAB.build(), new HashSet<>()),
                        // pick up sample
                        new TriggerPickUpSampleCommandSequence(intakeSubsystem),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_PICKUP_POSITION)),
                        // go to drop sample
                        new ActionCommand(dropRightSampleTAB.build(), new HashSet<>()),
                        // max slides
                        new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_POSITION)),
                        new WaitCommand(THROW_WAIT),
                        // drop sample, projecting it forward
                        new InstantCommand(() -> intakeSubsystem.openHorizontalClaw()),
                        new WaitCommand(150),

                        // middle sample
                        // slides to min, claw open, roll to place
                        new InstantCommand(() ->
                        {
                            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION);
                            intakeSubsystem.openHorizontalClaw();
                            intakeSubsystem.setHorizontalClawRollPosition(CLAW_ROLL);
                        }),
                        new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.HOVER_OVER_SAMPLE),
                        // go to sample
                        new ActionCommand(middleSampleTAB.build(), new HashSet<>()),
//                        new WaitCommand(100),
                        new TriggerPickUpSampleCommandSequence(intakeSubsystem),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_PICKUP_POSITION)),
                        new WaitCommand(250),

                        // go to wait for specimen pose
                        new ActionCommand(goToWaitForSpecimenPoseTAB0.build(), new HashSet<>()),
                        new WaitCommand(100),
                        // then drop the sample:
                        // MIDDLE slides this time
                        new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(HORIZONTAL_SLIDE_DROP_EXTENSION)),
                        // wait time increased since it's only dropping it
                        new WaitCommand(225),
                        // drop sample normally
                        new InstantCommand(() -> intakeSubsystem.openHorizontalClaw()),
                        new WaitCommand(100),

                        // slides to min, wrist to vertical, claw roll perpendicular
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION);
                            intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION);
                            intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION);
                            intakeSubsystem.setHorizontalClawPitchPosition(100);
                        }),

                        // cycle 1
                        // WAIT
                        new WaitCommand(1500),
                        // pick up specimen
                        new InstantCommand(() -> intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION)),
                        new WaitCommand(50),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION)),
                        new WaitCommand(150),
                        // transfer specimen
                        new SpecimenTransferCommandSequence(intakeSubsystem),
                        // go to hang position
                        new ActionCommand(hangSpecimenTAB0.build(), new HashSet<>()),
//                        new WaitCommand(150),
                        // hang specimen
                        new SpecimenHangCommandSequence(intakeSubsystem),

                        // cycle 2
                        // slides to min, wrist to vertical, claw roll perpendicular
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION);
                            intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION);
                            intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION);
                            intakeSubsystem.setHorizontalClawPitchPosition(100);
                        }),
                        new ActionCommand(goToWaitForSpecimenPoseTAB1.build(), new HashSet<>()),
                        // WAIT
                        new WaitCommand(1500),
                        // pick up specimen
                        new InstantCommand(() -> intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION)),
                        new WaitCommand(50),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION)),
                        new WaitCommand(250),
                        // transfer specimen
                        new SpecimenTransferCommandSequence(intakeSubsystem),
                        // go to hang position
                        new ActionCommand(hangSpecimenTAB1.build(), new HashSet<>()),
                        new WaitCommand(250),
                        // hang specimen
                        new SpecimenHangCommandSequence(intakeSubsystem),

                        // cycle 3
                        // slides to min, wrist to vertical, claw roll perpendicular
                        new InstantCommand(() -> {
                            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_POSITION);
                            intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_PERPENDICULAR_POSITION);
                            intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION);
                            intakeSubsystem.setHorizontalClawPitchPosition(100);
                        }),
                        new ActionCommand(goToWaitForSpecimenPoseTAB2.build(), new HashSet<>()),
                        // WAIT
                        new WaitCommand(1500),
                        // pick up specimen
                        new InstantCommand(() -> intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_INTAKE_POSITION)),
                        new WaitCommand(50),
                        new InstantCommand(() -> intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_INTAKE_POSITION)),
                        new WaitCommand(150),

                        // transfer specimen
                        new SpecimenTransferCommandSequence(intakeSubsystem),
                        // go to hang position
                        new ActionCommand(hangSpecimenTAB2.build(), new HashSet<>()),
                        // hang specimen
                        new SpecimenHangCommandSequence(intakeSubsystem),

                        // park
                        new ActionCommand(parkTAB.build(), new HashSet<>())
                )
        );

    }
}
