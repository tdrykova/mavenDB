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

        f = new JFrame("textfield");
        f.setLayout(new BorderLayout());

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();


        // create a label to display text
        l = new JLabel("nothing entered");

        // create a new button
        b = new JButton("submit");
        b.setText("Get a SMS-check");
        b.setSize(new Dimension(10,20));

        JTextArea checkTextArea = new JTextArea(25,5);
        checkTextArea.setText(checkTextArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");
        JScrollPane scrollPane = new JScrollPane(checkTextArea);
        scrollPane.setSize(600, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel1.add(checkTextArea);
        panel2.add(b);

        f.add(panel1, BorderLayout.NORTH);
        f.add(panel2, BorderLayout.CENTER);

        f.setSize(600, 500);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
