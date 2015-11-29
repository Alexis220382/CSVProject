package progress;

import javax.swing.*;
import java.awt.*;

public class Progress extends JFrame {


    JProgressBar current;

    int num = 0;


    public Progress() {

        super("Progress");

        JPanel pane = new JPanel();

        pane.setLayout(new FlowLayout());

        current = new JProgressBar(0, 2000);

        current.setValue(0);

        current.setStringPainted(true);

        pane.add(current);

        setContentPane(pane);

    }


    public void iterate(int a) {

        while (num < 2000) {

            current.setValue(num);

            try {

                Thread.sleep(a);

            } catch (InterruptedException e) {
            }

            num += 95;

        }

    }

    public Progress getProgressBar(String title, int count_rows){
        Progress frame = new Progress();
        frame.setTitle(title);
        frame.pack();
        frame.setLocation(600, 300);
        frame.setSize(500, 70);
        frame.setVisible(true);
        frame.iterate(count_rows);
        frame.dispose();
        return frame;
    }
}



