package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.constants.SpecimenConstants;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

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
        Pose2d startPose = TrajectorySequences.coloredSampleStartPose;
        drive.setPoseEstimate(startPose);

//        register(driveSubsystem, intakeSubsystem);
        register(intakeSubsystem);

        // initially set horizontal arm position to hover
        schedule(new InstantCommand(() -> {
            intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_MIN_EXTENSION);
            intakeSubsystem.setVerticalWristPitchPosition(SpecimenConstants.VERTICAL_WRIST_PITCH_SPECIMEN_DROPOFF_POSITION);
            intakeSubsystem.setVerticalClawPitchPosition(SpecimenConstants.VERTICAL_CLAW_PITCH_SPECIMEN_DROPOFF_POSITION);
            intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_DEPOSIT_POSITION);
            intakeSubsystem.closeVerticalClaw();

        }), new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL));


        switch (CURRENT_TRAJECTORY_SEQUENCE) {
            case 0:
                drive.followTrajectorySequence(TrajectorySequences.pushSamplesTS(drive));
                break;
            case 1:
                drive.followTrajectorySequence(TrajectorySequences.neutralStraysTS(drive));
                break;
            case 2:
                drive.followTrajectorySequence(TrajectorySequences.submersibleCycleTS(drive));
                break;
            case 3:
                drive.followTrajectorySequence(TrajectorySequences.moveAndHangSpecimensTS(drive));
                break;

        }


    }
}
