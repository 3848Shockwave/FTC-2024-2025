package org.firstinspires.ftc.teamcode;

public enum servoPositions {
     VERTCLAWGRIPOPEN(1.0),
     VERTCLAWGRIPCLOSE(0.0),

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
