package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.roadrunner.drive.Drive;
import com.arcrobotics.ftclib.command.CommandOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class CommandAutonomous extends CommandOpMode {

    IntakeSubsystem intakeSubsystem;
    DriveSubsystem driveSubsystem;
    FtcDashboard dashboard;

    Telemetry currentTelemetry;

    @Override
    public void initialize() {

        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        driveSubsystem = new DriveSubsystem(hardwareMap, currentTelemetry);

        dashboard = FtcDashboard.getInstance();
        currentTelemetry = dashboard.getTelemetry();

        register(driveSubsystem, intakeSubsystem);


    }
}
