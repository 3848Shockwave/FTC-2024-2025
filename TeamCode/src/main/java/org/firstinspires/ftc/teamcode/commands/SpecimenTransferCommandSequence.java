package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class SpecimenTransferCommandSequence extends SequentialCommandGroup {

    private IntakeSubsystem intakeSubsystem;
    private Telemetry telemetry;

    // waits are in milliseconds
    public static int CLOSE_CLAW_WAIT = 500;
    public static int WAIT0 = 500;
    public static int WAIT1 = 500;
    public static int WAIT2 = 500;
    public static int WAIT3 = 700;
    public static int WAIT3_5 = 500;
    public static int WAIT4 = 500;

    public static int HORIZONTAL_SLIDE_SPECIMEN_TRANSFER_POSITION = 0;
    public static int HORIZONTAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION = 77;
    public static int HORIZONTAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION = 40;
    public static int VERTICAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION = 85;
    public static int VERTICAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION = 180;

    public static int VERTICAL_SLIDE_MOTOR_SPECIMEN_DEPOSIT_POSITION = 2000;
    public static int HORIZONTAL_SLIDE_GTFO = 70;

    public SpecimenTransferCommandSequence(IntakeSubsystem intakeSubsystem, Telemetry telemetry) {
        this.intakeSubsystem = intakeSubsystem;
        this.telemetry = telemetry;
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
                    intakeSubsystem.setVerticalWristPitchPosition(VERTICAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setVerticalClawPitchPosition(VERTICAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setVerticalClawRollPosition(Constants.VERTICAL_CLAW_ROLL_TRANSFER_POSITION);
                }),
//                // (wait until ^ done)
                new WaitCommand(WAIT0),
                // set horizontal arm to transfer position
                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalWristPitchPosition(HORIZONTAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION);
                    // bring back slides (SPECIMEN)
                    intakeSubsystem.setHorizontalSlidePosition(HORIZONTAL_SLIDE_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setHorizontalClawPitchPosition(HORIZONTAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION);
                    intakeSubsystem.setHorizontalClawRollPosition(Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION);
                }),
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

                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalSlidePosition(HORIZONTAL_SLIDE_GTFO);
                }),

                new WaitCommand(WAIT3_5),

                // set vertical slide position to deposit position, after start of this command: wait 500 ms, then set vertical arm to deposit position
                new ParallelCommandGroup(
                        // set vertical slide position to transfer position
                        new SetVerticalSlidePositionCommand(intakeSubsystem, VERTICAL_SLIDE_MOTOR_SPECIMEN_DEPOSIT_POSITION, telemetry),
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
