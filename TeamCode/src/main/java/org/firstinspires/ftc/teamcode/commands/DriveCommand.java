package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

public class DriveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private DoubleSupplier strafeSpeed, forwardSpeed, rotationSpeed, heading;

    // HAS to be DoubleSuppliers because it never ends. These doubles constantly update but the method does not,
    // so it has to use DoubleSuppliers to get the updated values
    public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier rotationSpeed, DoubleSupplier heading) {
        this.driveSubsystem = driveSubsystem;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.rotationSpeed = rotationSpeed;
        this.heading = heading;

        addRequirements(driveSubsystem);

    }

//    @Override
//    public void initialize() {
//    }

    @Override
    public void execute() {
//        driveSubsystem.driveRobotCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), rotationSpeed.getAsDouble());
        driveSubsystem.driveFieldCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), rotationSpeed.getAsDouble(), heading.getAsDouble());
    }

//    @Override
//    public void end(boolean interrupted) {
//    }

    // never ends since it doesn't override isFinished()
//    @Override
//    public boolean isFinished() {
//        return false;
//    }
}
