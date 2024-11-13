package opencvcamera;

public class DetectedSample {
    private double angle;
    private String color;

    public DetectedSample() {
        angle = -1;
        color = "NO COLOR";

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }


}
