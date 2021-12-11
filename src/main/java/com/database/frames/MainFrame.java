package com.database.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class MainFrame extends JFrame {

    static JFrame f;
    static JProgressBar b;

    public void createMainFrame() {

        // create a frame
        f = new JFrame("ProgressBar demo");

        // create a panel
        JPanel p = new JPanel();

        // create a progressbar
        b = new JProgressBar();

        // set initial value
        b.setValue(0);

        b.setStringPainted(true);

        // add progressbar
        p.add(b);

        // add panel
        f.add(p);

        // set the size of the frame
       // f.setSize(500, 500);
        f.setBounds(800, 150, 500, 500);
        f.setResizable(false);
        f.setVisible(true);

        fill();
        f.setVisible(false);
    }


    // function to increase progress
    public static void fill()
    {
        int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                b.setValue(i + 10);

                // delay the thread
                Thread.sleep(50);
                i += 20;
            }
        }
        catch (Exception e) {
        }
        f.dispose();

        LoginFrame frame = new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(800, 150, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}

