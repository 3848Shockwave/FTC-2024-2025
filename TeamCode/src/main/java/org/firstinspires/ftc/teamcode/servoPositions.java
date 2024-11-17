package org.firstinspires.ftc.teamcode;

public enum servoPositions {
     VERTCLAWGRIPOPEN(1.0),
     VERTCLAWGRIPCLOSE(0.0),

    HORZCLAWPIVOPEN(1.0),
    HORZCLAWPIVCLOSE(0.4),
    VERTCLAWPIVOPEN(0.5),
    VERTCLAWPIVCLOSE(0.0),
    VERTPIVTRANSFER(.2),
    HORZPIVTRANSFER(-.55),
    HORZROTPICK(-.80),
    HORZARMLEFTPICK(-.3),
    HORZARMRIGHTPICK(.3),
    HORZARMROTLEFTTRANSFER(.9),
    HORZARMROTRIGHTTRANSFER(-.9),
    HORZCLAWROTTRANSFER(-1.0),
    VERTCLAWROTTRANSFER(-1.0),
    VERTARMROTRIGHTTRANSFER(-0.65),
    VERTARMROTLEFTTRANSFER(0.65),


     HORZCLAWGRIPOPEN(1.0),
     HORZCLAWGRIPCLOSE(0.0),

   ;
     private final double position;

     servoPositions(double position) {
          this.position = position;
     }

     public double getPosition() {
          return position;
     }
}
