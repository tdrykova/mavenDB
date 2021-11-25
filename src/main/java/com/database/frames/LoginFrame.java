package com.database.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginFrame extends JFrame implements ActionListener {

    String[] customer = {
            "Usual",
            "Vip"
    };

    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    JLabel numberOfDiscCardLabel = new JLabel("SALE CARD");
    JLabel customerLabel = new JLabel("CUSTOMER");
    JLabel discountLabel = new JLabel("DISCOUNT");
    JTextField userTextField = new JTextField();
    JComboBox customerState = new JComboBox(customer);
    JTextField cardTextField = new JTextField();
    JLabel discountPercentage = new JLabel("0");
    JButton loginButton = new JButton("LOGIN");
    JButton resetButton = new JButton("RESET");


    LoginFrame() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {
        userLabel.setBounds(50, 150, 100, 30);
        customerLabel.setBounds(50, 220, 100, 30);
        numberOfDiscCardLabel.setBounds(50, 290, 100, 30);
        discountLabel.setBounds(50, 360, 100, 30);

        userTextField.setBounds(150, 150, 150, 30);
        customerState.setBounds(150, 220, 150, 30);
        cardTextField.setBounds(150, 290, 150, 30);
        discountPercentage.setBounds(150, 360, 150, 30);

        loginButton.setBounds(50, 440, 100, 30);
        resetButton.setBounds(200, 440, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(customerLabel);
        container.add(numberOfDiscCardLabel);
        container.add(discountLabel);

        container.add(userTextField);
        userTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        userTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (userTextField.getText().length() >= 10 ) // limit textfield to 3 characters
                    e.consume();
            }
        });

        customerState.addActionListener(actionListenerClientState);
        container.add(customerState);

        container.add(cardTextField);
        cardTextField.setEnabled(false);
        cardTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        cardTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (cardTextField.getText().length() >= 8 ) // limit textfield to 3 characters
                    e.consume();
            }
        });

        container.add(discountPercentage);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    ActionListener actionListenerClientState = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            JComboBox box = (JComboBox)e.getSource();
            String item = (String)box.getSelectedItem();
            if (item.equals("Vip")) {
                discountPercentage.setText("15");
                cardTextField.setEnabled(true);
            }
            else {
                discountPercentage.setText("0");
                cardTextField.setText("");
                cardTextField.setEnabled(false);
            }
        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String userText;
            userText = userTextField.getText();
            if (!userText.isEmpty()) {
                dispose();
                new GoodsFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username");
            }

        }
        if (e.getSource() == resetButton) {
            userTextField.setText("");
        }
    }

}
