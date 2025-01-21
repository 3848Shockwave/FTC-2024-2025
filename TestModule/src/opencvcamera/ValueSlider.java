package opencvcamera;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ValueSlider extends JSlider {

//    public JTextArea textArea;
    public JLabel label;
    public String name;

    // TODO show slider value
    // http://www.java2s.com/Tutorials/Java/Swing_How_to/JSlider/Show_JSlider_value_while_dragging.htm


    public ValueSlider(int min, int max, int defaultValue, int width, String name, JPanel panel) {
//        super(JSlider.HORIZONTAL, 0, 255, 0);
        super(JSlider.HORIZONTAL, min, max, defaultValue);

        this.name = name;
        label = new JLabel();
        label.setFont(new Font("Helvetica", Font.PLAIN, 20));

        this.setSize(width, 50);
        this.setMajorTickSpacing(50);
        this.setMinorTickSpacing(5);
//        this.setPaintTicks(true);
//        this.setPaintLabels(true);
        this.setPaintTrack(true);
        label.setText(name + ": " + defaultValue);
        this.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // update text field when the slider value changes
                JSlider source = (JSlider) e.getSource();
                label.setText(name + ": " + source.getValue());

            }
        });

        panel.add(label);
        panel.add(this);
    }

}
