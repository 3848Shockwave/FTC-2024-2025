package org.firstinspires.ftc.teamcode.commands.verticalArm;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class SetVerticalArmPositionCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final IntakeSubsystem.IntakeState intakeState;

    public SetVerticalArmPositionCommand(IntakeSubsystem intakeSubsystem, IntakeSubsystem.IntakeState intakeState) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeState = intakeState;
//        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        switch (intakeState) {
            case TRANSFER:
                intakeSubsystem.openVerticalClaw();
                intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION);
                intakeSubsystem.setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION);
                intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_TRANSFER_POSITION);
                break;
            case DEPOSIT:
                intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_DEPOSIT_POSITION);
                intakeSubsystem.setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_DEPOSIT_POSITION);
                intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_DEPOSIT_POSITION);
                break;
        }

    }

    @Override
    public boolean isFinished() {
        return true;
    }


}
