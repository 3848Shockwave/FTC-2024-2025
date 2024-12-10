package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

@Config
public class DriveCommand extends CommandBase {
    private DriveSubsystem driveSubsystem;
    private DoubleSupplier strafeSpeed;
    private DoubleSupplier forwardSpeed;
    private DoubleSupplier rotationSpeed;
    private DoubleSupplier heading;
    private IMU imu;
    private BooleanSupplier isFieldCentric;

    public static boolean isEnabled = false;

    // HAS to be DoubleSuppliers because it never ends. These doubles constantly update but the method does not,
    // so it has to use DoubleSuppliers to get the updated values
    public DriveCommand(DriveSubsystem driveSubsystem, DoubleSupplier strafeSpeed, DoubleSupplier forwardSpeed, DoubleSupplier rotationSpeed, BooleanSupplier isFieldCentric) {
        this.driveSubsystem = driveSubsystem;
        this.strafeSpeed = strafeSpeed;
        this.forwardSpeed = forwardSpeed;
        this.rotationSpeed = rotationSpeed;
        this.isFieldCentric = isFieldCentric;

        addRequirements(driveSubsystem);

    }

//    @Override
//    public void initialize() {
//    }

    @Override
    public void execute() {
        // don't drive if not enabled
        if (!isEnabled) return;


        // MANUALLY REVERSED ROTATION SPEED, I SHOULDN'T HAVE NEEDED TO BUT I HAD TO :(
        if (isFieldCentric.getAsBoolean()) {
            driveSubsystem.driveFieldCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), rotationSpeed.getAsDouble());
        } else {
            driveSubsystem.driveRobotCentric(strafeSpeed.getAsDouble(), forwardSpeed.getAsDouble(), rotationSpeed.getAsDouble());
        }
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
