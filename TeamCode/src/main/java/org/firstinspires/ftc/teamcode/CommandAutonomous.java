package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

//@Photon
@Autonomous(name = "COMMAND AUTONOMOUS (use this please now!)")
@Config
public class CommandAutonomous extends CommandOpMode {

    IntakeSubsystem intakeSubsystem;
//    DriveSubsystem driveSubsystem;
    FtcDashboard dashboard;

    Telemetry currentTelemetry;

    public static int CURRENT_TRAJECTORY_SEQUENCE = 0;



    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
//        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);

        dashboard = FtcDashboard.getInstance();
        currentTelemetry = dashboard.getTelemetry();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Pose2d startPose = TrajectorySequences.coloredSampleStartPose;
        drive.setPoseEstimate(startPose);

//        register(driveSubsystem, intakeSubsystem);
        register(intakeSubsystem);




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
//            case 3:
//                drive.followTrajectorySequence(drive.trajectorySequenceBuilder())

        }





    }
}
