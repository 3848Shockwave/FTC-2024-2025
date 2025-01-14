package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.roadrunner.drive.Drive;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.trajectory.Trajectory;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class RunTrajectorySequenceCommand extends CommandBase {
    private IntakeSubsystem intakeSubsystem;
    private TrajectorySequence trajectorySequence;
    private SampleMecanumDrive drive;
    public RunTrajectorySequenceCommand(IntakeSubsystem intakeSubsystem, TrajectorySequence trajectorySequence, SampleMecanumDrive drive) {

    }

    @Override
    public void initialize() {
        drive.followTrajectorySequence(trajectorySequence);

    }
}
