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
public class TransferCommandSequence extends SequentialCommandGroup {

    private IntakeSubsystem intakeSubsystem;
    private Telemetry telemetry;

    public static int WAIT0 = 2000;
    public static int WAIT1 = 2000;
    public static int WAIT2 = 2000;
    public static int WAIT3 = 2000;
    public static int WAIT4 = 2000;

    public TransferCommandSequence(IntakeSubsystem intakeSubsystem, Telemetry telemetry) {
        this.intakeSubsystem = intakeSubsystem;
        this.telemetry = telemetry;
        addCommands(
                // set vertical arm to transfer position
                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
//                // (wait until ^ done)
                new WaitCommand(WAIT0),
                // set horizontal arm to transfer position
                new SetHorizontalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.TRANSFER),
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
                        new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION, Constants.TEST_DOUBLE, telemetry),
                        new SequentialCommandGroup(
                                new WaitCommand(WAIT4),
                                // set vertical arm to deposit position
                                new SetVerticalArmPositionCommand(intakeSubsystem, IntakeSubsystem.IntakeState.DEPOSIT)
                        )
                )
                // DONE!
        );
        addRequirements(intakeSubsystem);
    }

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
