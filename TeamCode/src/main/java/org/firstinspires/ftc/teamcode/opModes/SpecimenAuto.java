package org.firstinspires.ftc.teamcode.opModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.*;
import com.arcrobotics.ftclib.command.*;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetHorizontalSlidePosition;
import org.firstinspires.ftc.teamcode.commands.sequences.SpecimenHangCommandSequence;
import org.firstinspires.ftc.teamcode.commands.sequences.SpecimenTransferCommandSequence;
import org.firstinspires.ftc.teamcode.commands.sequences.TriggerPickUpSampleCommandSequence;
import org.firstinspires.ftc.teamcode.commands.sequences.VerticalArmToSpecimenDropoffCommandSequence;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

import java.lang.Math;
import java.util.HashSet;

import static org.firstinspires.ftc.teamcode.commands.sequences.SpecimenTransferCommandSequence.WAIT4;

// TODO: change to LinearOpMode if we have to
@Config
@Autonomous(name = "SPECIMEN AUTONOMOUS (currently in testing)")
public class SpecimenAuto extends CommandOpMode {

//    File headingFile = new File("./savedHeading");

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
    public static double VEL_CONSTRAINT = 55;

    public static long THROW_WAIT = 200;
    public static double HORIZONTAL_SLIDE_DROP_EXTENSION = 60;
    public static double PICK_UP_SPECIMEN_Y = 46.5;

    HorizontalSlideSubsystem horizontalSlideSubsystem;
    VerticalSlideSubsystem verticalSlideSubsystem;
    ArmSubsystem horizontalArmSubsystem, verticalArmSubsystem;
    Telemetry currentTelemetry;

    @Override
    public void initialize() {

        currentTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        horizontalSlideSubsystem = new HorizontalSlideSubsystem(hardwareMap, currentTelemetry);
        verticalSlideSubsystem = new VerticalSlideSubsystem(hardwareMap, currentTelemetry);
        verticalArmSubsystem = new ArmSubsystem(hardwareMap, currentTelemetry, ArmSubsystem.Type.VERTICAL);
        horizontalArmSubsystem = new ArmSubsystem(hardwareMap, currentTelemetry, ArmSubsystem.Type.HORIZONTAL);


        register(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem);

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
//                .strafeToLinearHeading(
//                        new Vector2d(
//                                pickUpSpecimenPose.component1().x,
//                                pickUpSpecimenPose.component1().y
//                        ),
//                        Math.toRadians(90),
//                        new TranslationalVelConstraint(10)
//
//                )
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
                        Math.toRadians(-90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();


        TrajectoryActionBuilder parkTAB = hangSpecimenTAB2
                .fresh()
                .strafeToLinearHeading(
                        new Vector2d(
                                -40,
                                52
                        ),
                        Math.toRadians(-90),
                        new TranslationalVelConstraint(VEL_CONSTRAINT)
                )
                .endTrajectory();

        // INIT ACTIONS
        CommandScheduler.getInstance().schedule(
                new SetHorizontalSlidePosition(horizontalSlideSubsystem, Constants.HORIZONTAL_SLIDE_MIN_POSITION),
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_CLOSED_POSITION),
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION),
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_ROLL_SPECIMEN_DROPOFF_POSITION)
        );

        waitForStart();

        CommandScheduler.getInstance().schedule(
                new SequentialCommandGroup(
                        new VerticalArmToSpecimenDropoffCommandSequence(verticalArmSubsystem, verticalSlideSubsystem, WAIT4),
                        // set horizontal arm to be straight up
                        new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION),
                        new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION),
                        // wait 5 secs for team
//                        new WaitCommand(5000),
                        // go to hang specimen position
                        new ActionCommand(goToHangSpecimenTAB.build(), new HashSet<>()),
                        // hang the specimen
                        new SpecimenHangCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem),

                        // right sample
                        // slides to min, claw open, roll to place
                        new SetHorizontalSlidePosition(horizontalSlideSubsystem, Constants.HORIZONTAL_SLIDE_MIN_POSITION),
                        new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),
                        new SetClawRollCommand(horizontalArmSubsystem, CLAW_ROLL),

                        new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),
                        new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION),
                        new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION),

                        // go to sample
                        new ActionCommand(rightSampleTAB.build(), new HashSet<>()),
                        // pick up sample
                        new TriggerPickUpSampleCommandSequence(horizontalArmSubsystem),
                        new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_PICKUP_POSITION),
                        // go to drop sample
                        new ActionCommand(dropRightSampleTAB.build(), new HashSet<>()),
                        // max slides
                        new InstantCommand(() -> horizontalSlideSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MAX_POSITION)),
                        new WaitCommand(THROW_WAIT),
                        // drop sample, projecting it forward
                        new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),
                        new WaitCommand(150),

                        // middle sample
                        // slides to min, claw open, roll to place
                        new SetHorizontalSlidePosition(horizontalSlideSubsystem, Constants.HORIZONTAL_SLIDE_MIN_POSITION),
                        new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),
                        new SetClawRollCommand(horizontalArmSubsystem, CLAW_ROLL),

                        new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),
                        new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_HOVER_POSITION),
                        new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION),

                        // go to sample
                        new ActionCommand(middleSampleTAB.build(), new HashSet<>()),
//                        new WaitCommand(100),
                        new TriggerPickUpSampleCommandSequence(horizontalArmSubsystem),
                        new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_PICKUP_POSITION),
                        new WaitCommand(200),

                        // go to wait for specimen pose
                        new ActionCommand(goToWaitForSpecimenPoseTAB0.build(), new HashSet<>()),
                        new WaitCommand(25),
                        // then drop the sample:
                        // MIDDLE slides this time
                        // new InstantCommand(() -> intakeSubsystem.setHorizontalSlidePosition(HORIZONTAL_SLIDE_DROP_EXTENSION)),
                        // wait time increased since it's only dropping it
                        //new WaitCommand(50),
                        // drop sample normally
                        new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),
                        new WaitCommand(200),

                        // TODO: this is when we pick up the specimen from the wall

                        // transfer specimen
                        new SpecimenTransferCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem),
                        // go to hang position
                        new ActionCommand(hangSpecimenTAB0.build(), new HashSet<>()),
//                        new WaitCommand(150),
                        // hang specimen
                        new SpecimenHangCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem),

                        // cycle 2
                        // TODO: this is when we pick up the specimen from the wall
                        // transfer specimen
                        new SpecimenTransferCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem),
                        // go to hang position
                        new ActionCommand(hangSpecimenTAB1.build(), new HashSet<>()),
                        new WaitCommand(250),
                        // hang specimen
                        new SpecimenHangCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem),

                        // cycle 3
                        // TODO: this is when we pick up the specimen from the wall
                        // transfer specimen
                        new SpecimenTransferCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem),
                        // go to hang position
                        new ActionCommand(hangSpecimenTAB2.build(), new HashSet<>()),
                        // hang specimen
                        new SpecimenHangCommandSequence(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem),

                        // park
                        new ActionCommand(parkTAB.build(), new HashSet<>())
                )
        );

    }
}
