package org.firstinspires.ftc.teamcode.commands.slides;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

public class SetVerticalSlidePositionCommand extends CommandBase {

    private VerticalSlideSubsystem verticalSlideSubsystem;
    private int targetPosition;


    public SetVerticalSlidePositionCommand(VerticalSlideSubsystem verticalSlideSubsystem, int targetPosition) {
        this.verticalSlideSubsystem = verticalSlideSubsystem;
        this.targetPosition = targetPosition;
        // THIS MAKES IT BLOCKING: IT R E Q U I R E S THE SUBSYSTEM
//        addRequirements(intakeSubsystem);
//        interruptOn(() -> false);

    }

    @Override
    public void initialize() {
        verticalSlideSubsystem.setVerticalSlideMotorsTargetPosition(targetPosition);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

}
