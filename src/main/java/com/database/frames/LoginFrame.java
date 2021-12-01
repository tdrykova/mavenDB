package com.database.frames;

import com.database.openedu.ConnectionDb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginFrame extends JFrame implements ActionListener {

    private static final String INSERT_NEW_USER = "INSERT INTO users VALUES (?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private String GET_COUNT_USERS = "SELECT COUNT(*) FROM users";
    private String IS_EXIST = "SELECT EXISTS(SELECT FROM table WHERE phone = 1)";
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    ConnectionDb connect = new ConnectionDb();
    int countUsers = 0;

//    String[] customer = {
//            "Usual",
//            "Vip"
//    };

    Container container = getContentPane();
    JLabel userLabel = new JLabel("USERNAME");
    //JLabel numberOfDiscCardLabel = new JLabel("SALE CARD");
    JLabel numberOfPhoneLabel = new JLabel("PHONE NUM");
//    JLabel emailAddressLabel = new JLabel("E-MAIL");
    //JLabel customerLabel = new JLabel("CUSTOMER");
    JLabel discountLabel = new JLabel("DISCOUNT");

    JTextField userTextField = new JTextField();
   // JComboBox customerState = new JComboBox(customer);
    JTextField phoneTextField = new JTextField();
//    JTextField emailTextField = new JTextField();

//    JLabel discountPercentage = new JLabel("0");
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
        numberOfPhoneLabel.setBounds(50, 220, 100, 30);
//        emailAddressLabel.setBounds(50, 290, 100, 30);
//        discountLabel.setBounds(50, 360, 100, 30);

        userTextField.setBounds(150, 150, 150, 30);
        phoneTextField.setBounds(150, 220, 150, 30);
//        emailTextField.setBounds(150, 290, 150, 30);
//        discountPercentage.setBounds(150, 360, 150, 30);

        loginButton.setBounds(200, 440, 100, 30);
        resetButton.setBounds(50, 440, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(userLabel);
        container.add(numberOfPhoneLabel);
//        container.add(emailAddressLabel);
//        container.add(discountLabel);

        container.add(userTextField);
        userTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        userTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (userTextField.getText().length() >= 10 ) // limit textfield to 10 characters
                    e.consume();
            }
        });

        container.add(phoneTextField);
        phoneTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        phoneTextField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (phoneTextField.getText().length() >= 11 ) // limit textfield to 11 characters
                    e.consume();
            }
        });

//        container.add(emailTextField);
//        emailTextField.addKeyListener(new KeyAdapter() {
//            public void keyTyped(KeyEvent e) {
//                if (emailTextField.getText().length() >= 25 ) // limit textfield to 11 characters
//                    e.consume();
//            }
//        });

//        container.add(discountPercentage);
        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            int isNew = 0;
            int isVip = 0;
            int countUsers = 0;

            try {
                preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_USERS);
                ResultSet res2 = preparedStatement2.executeQuery();

                while (res2.next() ) {
                    countUsers = res2.getInt(1);
                    int id = res2.getInt("id");
                    String name = res2.getString("name");
                    String phone = res2.getString("phone");

                    if (userTextField.getText() == name && phoneTextField.getText() == phone) {
                        isVip++;
                        System.out.println(isVip);
//                        JOptionPane.showMessageDialog(LoginFrame.this,
//                                        "Вы наш Vip-клиент! Добро пожаловать в магазин");

                    }
                } System.out.println(isVip);
            } catch (SQLException es) {
                es.printStackTrace();
            }

            if (isVip == 0) {
                    isNew++;
                    try {
                        //Statement stmt = connect.getConnection().createStatement();
                       // ResultSet rs = stmt.executeQuery(GET_COUNT_USERS);

                        countUsers++;
                        System.out.println(countUsers);

                        preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_USER);
                        preparedStatement.setInt(1,countUsers++);
                        preparedStatement.setString(2,userTextField.getText());
                        preparedStatement.setString(3,phoneTextField.getText());
                        //preparedStatement.setString(4,emailTextField.getText());
                        preparedStatement.execute();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
//            else if (userTextField.bounds().isEmpty()){
//                    JOptionPane.showMessageDialog(this, "Invalid Username");
//                }

            dispose();
            new GoodsFrame();
        }

        if (e.getSource() == resetButton) {
            userTextField.setText("");
            phoneTextField.setText("");
           // emailTextField.setText("");
           // cardTextField.setText("");
        }

        }


    }


