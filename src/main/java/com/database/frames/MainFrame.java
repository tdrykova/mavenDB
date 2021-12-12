package com.database.frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MainFrame extends JFrame {

    static JFrame f;
    static JProgressBar b;
    Container container = getContentPane();

    public void createMainFrame() {

        BufferedImage image = null;

        {
            try {
                image = ImageIO.read(new File("C:/Users/tatry/Downloads/basket.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JLabel label = new JLabel(new ImageIcon(image));

        // create a frame
        f = new JFrame("Opening...");

        // create a panel
        JPanel p = new JPanel();

        JLabel nameStore = new JLabel("TechnoPoint");
        nameStore.setBounds(0,30,100,200);
        nameStore.setFont(new Font("TimesRoman", Font.BOLD, 22));

        // create a progressbar
        b = new JProgressBar();

        // set initial value
        b.setValue(0);

        b.setStringPainted(true);


        p.add(b);
        p.add(nameStore);
        p.add(label);

        // add panel
        f.add(p);

        f.setBounds(400, 300, 600, 550);
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

