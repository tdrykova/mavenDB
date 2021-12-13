package com.database.frames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MainFrame extends JFrame {

    static JFrame frame = new JFrame("Opening...");
    static JProgressBar bar = new JProgressBar();
    public void createMainFrame() {

        BufferedImage image = null;

            try {
                image = ImageIO.read(new File("C:/Users/tatry/Downloads/basket.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        JLabel labelImage = new JLabel(new ImageIcon(image));
        JPanel panel = new JPanel();

        JLabel nameStore = new JLabel("TechnoPoint");
        nameStore.setBounds(0,30,100,200);
        nameStore.setFont(new Font("TimesRoman", Font.BOLD, 22));

        bar.setValue(0);
        bar.setStringPainted(true);

        panel.add(bar);
        panel.add(nameStore);
        panel.add(labelImage);

        frame.add(panel);
        frame.setBounds(700, 200, 600, 550);
        frame.setResizable(false);
        frame.setVisible(true);

        fill();
        frame.setVisible(false);
    }

    // function to increase progress
    public static void fill()
    {
        int i = 0;
        try {
            while (i <= 100) {
                // fill the menu bar
                bar.setValue(i + 10);
                // delay the thread
                Thread.sleep(50);
                i += 20;
            }
        }
        catch (Exception e) {
            System.out.println("ProgressBar isn't filled");
        }
        frame.dispose();

        LoginFrame frame = new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(800, 150, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }
}

