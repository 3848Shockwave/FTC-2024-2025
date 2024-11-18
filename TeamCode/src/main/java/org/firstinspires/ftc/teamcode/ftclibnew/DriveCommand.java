package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.CommandBase;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private DoubleSupplier strafeSpeed, forwardSpeed, rotationSpeed, heading;

    public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier rotationSpeed, DoubleSupplier heading) {
        this.driveSubsystem = driveSubsystem;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.rotationSpeed = rotationSpeed;
        this.heading = heading;

        addRequirements(driveSubsystem);

    }

    @Override
    public void execute() {
//        driveSubsystem.driveRobotCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), rotationSpeed.getAsDouble());
        driveSubsystem.driveFieldCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), rotationSpeed.getAsDouble(), heading.getAsDouble());
    }
}
