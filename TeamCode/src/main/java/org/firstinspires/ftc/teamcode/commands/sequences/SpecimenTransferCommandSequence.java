package org.firstinspires.ftc.teamcode.commands.sequences;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.slides.SetHorizontalSlidePosition;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HorizontalSlideSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.VerticalSlideSubsystem;

@Config
public class SpecimenTransferCommandSequence extends SequentialCommandGroup {


    // waits are in milliseconds
    public static int CLOSE_CLAW_WAIT = 0;
    public static int WAIT0 = 300;
    public static int WAIT1 = 400;
    public static int WAIT2 = 150;
    public static int WAIT3 = 100;
    public static int WAIT4 = 100;

    public SpecimenTransferCommandSequence(ArmSubsystem horizontalArmSubsystem, ArmSubsystem verticalArmSubsystem, HorizontalSlideSubsystem horizontalSlideSubsystem, VerticalSlideSubsystem verticalSlideSubsystem) {
        addCommands(
                // close horizontal arm claw to pick up the specimen
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_CLOSED_POSITION),
                // open vertical arm claw to pick up the specimen
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION),
//                // (wait until ^ done)
                new WaitCommand(CLOSE_CLAW_WAIT),
                // set vertical arm to transfer position
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION),
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION),

//                // (wait until ^ done)
                new WaitCommand(WAIT0),

                // bring back slides
                new SetHorizontalSlidePosition(horizontalSlideSubsystem, Constants.HORIZONTAL_SLIDE_SPECIMEN_TRANSFER_POSITION),
                // set horizontal arm to transfer position
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_SPECIMEN_TRANSFER_POSITION),
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_SPECIMEN_TRANSFER_POSITION),
                new SetClawRollCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION),

                // (wait until ^ done)
                new WaitCommand(WAIT1),
                // close vertical arm claw
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_CLOSED_POSITION),
                // (wait until ^ done)
                new WaitCommand(WAIT2),
                // open horizontal arm claw
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),
                // (wait until ^ done)
                new WaitCommand(WAIT3),
//                // set horizontal slide out a little
//                new InstantCommand(() ->
//                        intakeSubsystem.setHorizontalSlidePosition(HORIZONTAL_SLIDE_SLIGHTLY_OUT_POSITION)
//                ),

                // set vertical slide position to deposit position, after start of this command: wait, then set vertical arm to deposit position
                new VerticalArmToSpecimenDropoffCommandSequence(horizontalArmSubsystem, verticalSlideSubsystem, WAIT4),
                // set horizontal arm to be straight up
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION),
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION)
                // DONE!
        );
        addRequirements(horizontalArmSubsystem, verticalArmSubsystem, horizontalSlideSubsystem, verticalSlideSubsystem);
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
