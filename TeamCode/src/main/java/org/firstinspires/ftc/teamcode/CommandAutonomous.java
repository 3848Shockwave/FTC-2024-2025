package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Autonomous(name = "COMMAND AUTONOMOUS (use this please now!)")
@Config
public class CommandAutonomous extends CommandOpMode {

    IntakeSubsystem intakeSubsystem;
//    DriveSubsystem driveSubsystem;
    FtcDashboard dashboard;

    Telemetry currentTelemetry;

    public static int CURRENT_TRAJECTORY_SEQUENCE = 1;



    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
//        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);

        dashboard = FtcDashboard.getInstance();
        currentTelemetry = dashboard.getTelemetry();

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

//        register(driveSubsystem, intakeSubsystem);
        register(intakeSubsystem);

        switch (CURRENT_TRAJECTORY_SEQUENCE) {
            case 0:
                drive.followTrajectorySequence(TrajectorySequences.pushSamplesTS(drive));
            case 1:
                drive.followTrajectorySequence(TrajectorySequences.neutralStraysTS(drive));
            case 2:
                drive.followTrajectorySequence(TrajectorySequences.submersibleCycleTS(drive));

        }

        drive.followTrajectorySequence(TrajectorySequences.neutralStraysTS(drive));




    }
}
