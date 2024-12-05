package org.firstinspires.ftc.teamcode.ftclibnew;

import com.arcrobotics.ftclib.command.CommandBase;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {

    private DriveSubsystem driveSubsystem;
    private DoubleSupplier strafeSpeed;
    private DoubleSupplier rotationSpeed;
    private DoubleSupplier forwardSpeed;
    private DoubleSupplier angle;

    public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier rotationSpeed) {
        this.driveSubsystem = driveSubsystem;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.rotationSpeed = rotationSpeed;

        addRequirements(driveSubsystem);

    }

    @Override
    public void execute() {
        driveSubsystem.driveRobotCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), rotationSpeed.getAsDouble());
    }

//    @Override
//    public boolean isFinished() {
//        return false;
//    }
}
