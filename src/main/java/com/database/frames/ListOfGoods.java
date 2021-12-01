package com.database.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListOfGoods extends JFrame implements ActionListener {

    public JFrame f;

    // JButton
    static JButton b;

    // label to display text
    static JLabel l;

    // text area
    public JTextArea jt;

    public ListOfGoods() {

        JButton exitButton = new JButton("Выход");
        exitButton.setBounds(490, 5, 90, 30);


    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
