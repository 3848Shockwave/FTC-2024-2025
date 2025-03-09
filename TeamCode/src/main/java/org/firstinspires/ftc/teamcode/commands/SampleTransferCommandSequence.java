package org.firstinspires.ftc.teamcode.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawGripCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawPitchCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetClawRollCommand;
import org.firstinspires.ftc.teamcode.commands.arm.SetWristPitchCommand;
import org.firstinspires.ftc.teamcode.commands.horizontalArm.SetHorizontalSlidePosition;
import org.firstinspires.ftc.teamcode.constants.Constants;
import org.firstinspires.ftc.teamcode.commands.verticalArm.SetVerticalSlidePositionCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@Config
public class SampleTransferCommandSequence extends SequentialCommandGroup {


    // waits are in milliseconds
    public static int CLOSE_CLAW_WAIT = 100;
    public static int HORIZONTAL_SLIDE_RETRACT_WAIT = 300;
    public static int HORIZONTAL_SLIDE_RETRACT_OFFSET = 20;
    public static int WAIT0 = 200;
    public static int WAIT1 = 150;
    public static int WAIT2 = 200;
    public static int WAIT3 = 100;
    public static int WAIT4 = 300;

    public SampleTransferCommandSequence(ArmSubsystem horizontalArmSubsystem, ArmSubsystem verticalArmSubsystem, IntakeSubsystem intakeSubsystem) {
        addCommands(
                // close horizontal arm claw to pick up the sample
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_CLOSED_POSITION),
                // open vertical arm claw to pick up the sample
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION),
//                // (wait until ^ done)
                new WaitCommand(CLOSE_CLAW_WAIT),
                // set vertical arm to transfer position
                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_TRANSFER_POSITION),
                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_TRANSFER_POSITION),
//                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_OPEN_POSITION),

                // set horizontal arm to transfer position
                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_TRANSFER_POSITION),
                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_TRANSFER_POSITION),
//                // (wait until ^ done)
                new WaitCommand(WAIT0),

                // do the little slide thingy
                new InstantCommand(() -> {
                    intakeSubsystem.setHorizontalSlidePosition(Constants.HORIZONTAL_SLIDE_TRANSFER_POSITION + HORIZONTAL_SLIDE_RETRACT_OFFSET);
                }),
                new SetClawRollCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_ROLL_TRANSFER_POSITION),

                new WaitCommand(HORIZONTAL_SLIDE_RETRACT_WAIT),

                // actually put the slides in this time
                new SetHorizontalSlidePosition(intakeSubsystem, Constants.HORIZONTAL_SLIDE_TRANSFER_POSITION),
//                // (wait until ^ done)
                new WaitCommand(WAIT1),
//                // close vertical arm claw
                new SetClawGripCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_GRIP_CLOSED_POSITION),
//                // (wait until ^ done)
                new WaitCommand(WAIT2),
//                // open horizontal arm claw
                new SetClawGripCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_GRIP_OPEN_POSITION),

//                // (wait until ^ done)
                new WaitCommand(WAIT3),

                // set vertical slide position to deposit position, after start of this command: wait 500 ms, then set vertical arm to deposit position
                new ParallelCommandGroup(
                        // set vertical slide position to transfer position
                        new SetVerticalSlidePositionCommand(intakeSubsystem, Constants.VERTICAL_SLIDE_MOTOR_DEPOSIT_POSITION),
                        new SequentialCommandGroup(
                                new WaitCommand(WAIT4),
                                // set vertical arm to deposit position
                                new SetWristPitchCommand(verticalArmSubsystem, Constants.VERTICAL_WRIST_PITCH_DEPOSIT_POSITION),
                                new SetClawPitchCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_PITCH_DEPOSIT_POSITION),
                                new SetClawRollCommand(verticalArmSubsystem, Constants.VERTICAL_CLAW_ROLL_DEPOSIT_POSITION),

                                // set horizontal arm to be straight up
                                new SetWristPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_WRIST_PITCH_VERTICAL_POSITION),
                                new SetClawPitchCommand(horizontalArmSubsystem, Constants.HORIZONTAL_CLAW_PITCH_HOVER_POSITION)
                        )
                )
                // DONE!
        );
        addRequirements(horizontalArmSubsystem, verticalArmSubsystem, intakeSubsystem);
    }

}
