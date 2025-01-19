package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class SpecimenTransferCommandSequence extends SequentialCommandGroup {


    // waits are in milliseconds
    public static double HORIZONTAL_SLIDE_SLIGHTLY_OUT_POSITION = 87 + 5;
    public static int CLOSE_CLAW_WAIT = 0;
    public static int WAIT0 = 300;
    public static int WAIT1 = 400;
    public static int WAIT2 = 150;
    public static int WAIT3 = 100;
    public static int WAIT4 = 100;

    public SpecimenTransferCommandSequence(IntakeSubsystem intakeSubsystem) {
        addCommands(
                // close horizontal arm claw to pick up the sample
                new InstantCommand(intakeSubsystem::closeHorizontalClaw),
                // open vertical arm claw to pick up the sample
                new InstantCommand(intakeSubsystem::openVerticalClaw),
//                // (wait until ^ done)
                new WaitCommand(CLOSE_CLAW_WAIT),
                // set vertical arm to transfer position
                new InstantCommand(() -> {
                    intakeSubsystem.openVerticalClaw();
                    intakeSubsystem.setVerticalWristPitchPosition(Constants.VERTICAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setVerticalClawPitchPosition(Constants.VERTICAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_TRANSFER_POSITION);
                }),
//                // (wait until ^ done)
                new WaitCommand(WAIT0),
                // set horizontal arm to transfer position
                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalWristPitchPosition(Constants.HORIZONTAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION);
                    // bring back slides (SPECIMEN)
                    intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setHorizontalClawPitchPosition(Constants.HORIZONTAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION);
                }),
                // (wait until ^ done)
                new WaitCommand(WAIT1),
                // close vertical arm claw
                new InstantCommand(intakeSubsystem::closeVerticalClaw),
                // (wait until ^ done)
                new WaitCommand(WAIT2),
                // open horizontal arm claw
                new InstantCommand(intakeSubsystem::openHorizontalClaw),
                // (wait until ^ done)
                new WaitCommand(WAIT3),
//                // set horizontal slide out a little
//                new InstantCommand(() ->
//                        intakeSubsystem.setHorizontalSlidePosition(HORIZONTAL_SLIDE_SLIGHTLY_OUT_POSITION)
//                ),

                // set vertical slide position to deposit position, after start of this command: wait, then set vertical arm to deposit position
                new VerticalArmToSpecimenDropoffCommandSequence(intakeSubsystem, WAIT4)
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
