package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalArmPositionCommand;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class SampleTransferCommandSequence extends SequentialCommandGroup {


    // waits are in milliseconds
    public static int CLOSE_CLAW_WAIT = 0;
    public static int WAIT0 = 200;
    public static int WAIT1 = 600;
    public static int WAIT2 = 200;
    public static int WAIT3 = 0;
    public static int WAIT4 = 200;

    public SampleTransferCommandSequence(IntakeSubsystem intakeSubsystem) {
        addCommands(
                // close horizontal arm claw to pick up the sample
                new InstantCommand(intakeSubsystem::closeHorizontalClaw),
                // open vertical arm claw to pick up the sample
                new InstantCommand(intakeSubsystem::openVerticalClaw),
//                // (wait until ^ done)
                new WaitCommand(CLOSE_CLAW_WAIT),
                // set vertical arm to transfer position
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
                // set horizontal arm to transfer position
                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION);
                }
                ),
//                // (wait until ^ done)
                new WaitCommand(WAIT0),
                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION);
                    intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_TRANSFER_POSITION);
                    intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION);
                }
                ),
//                // (wait until ^ done)
                new WaitCommand(WAIT1),
//                // close vertical arm claw
                new InstantCommand(intakeSubsystem::closeVerticalClaw),
//                // (wait until ^ done)
                new WaitCommand(WAIT2),
//                // open horizontal arm claw
                new InstantCommand(intakeSubsystem::openHorizontalClaw),
//                // (wait until ^ done)
                new WaitCommand(WAIT3),

                // set vertical slide position to deposit position, after start of this command: wait 500 ms, then set vertical arm to deposit position
                new ParallelCommandGroup(
                        // set vertical slide position to transfer position
                        new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION),
                        new SequentialCommandGroup(
                                new WaitCommand(WAIT4),
                                // set vertical arm to deposit position
                                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT),
                                // set horizontal arm to be straight up
                                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.VERTICAL)
                        )
                )
                // DONE!
        );
        addRequirements(intakeSubsystem);
    }

//    @Override
//    public void execute() {
//        telemetry.addLine("transfer command sequence EXECUTING!");
//    }

//    @Override
//    public void initialize() {
//        intakeSubsystem.setCurrentState(IntakeSubsystem.IntakeState.TRANSFER);
//    }
//
//    @Override
//    public void end(boolean interrupted) {
//        intakeSubsystem.currentIntakeState = IntakeSubsystem.IntakeState.DEPOSIT;
//    }
}
