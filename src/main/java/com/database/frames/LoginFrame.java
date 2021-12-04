package com.database.frames;

import com.database.openedu.ConnectionDb;
import com.database.templates.TabbedPaneTemplate;

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

    String[] person = {
            " ",
            "User",
            "Admin"
    };

    Container container = getContentPane();
    JLabel statusLabel = new JLabel("STATUS");
    JLabel userLabel = new JLabel("NAME");
    JLabel numberOfPhoneLabel = new JLabel("PHONE NUM");
    JLabel passwordLabel = new JLabel("PASSWORD");

    JComboBox stateOfPerson = new JComboBox(person);
    JTextField userTextField = new JTextField();
    JTextField phoneTextField = new JTextField();
    JPasswordField passwordTextField = new JPasswordField();


    JButton loginButton = new JButton("ENTER");
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
        statusLabel.setBounds(50, 150, 100, 30);
        userLabel.setBounds(50, 220, 100, 30);

        numberOfPhoneLabel.setBounds(50, 290, 100, 30);
        numberOfPhoneLabel.setVisible(false);
        passwordLabel.setBounds(50, 290, 100, 30);
        passwordLabel.setVisible(false);

        stateOfPerson.setBounds(150, 150, 150, 30);
        userTextField.setBounds(150, 220, 150, 30);

        phoneTextField.setBounds(150, 290, 150, 30);
        phoneTextField.setVisible(false);
        passwordTextField.setBounds(150, 290, 150, 30);
        passwordTextField.setVisible(false);

        loginButton.setBounds(200, 440, 100, 30);
        resetButton.setBounds(50, 440, 100, 30);
    }

    public void addComponentsToContainer() {
        container.add(statusLabel);
        container.add(userLabel);
        container.add(numberOfPhoneLabel);
        container.add(passwordLabel);


        container.add(stateOfPerson);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stateOfPerson.getSelectedItem() == "User") {
                    phoneTextField.setVisible(true);
                    numberOfPhoneLabel.setVisible(true);
                    passwordTextField.setVisible(false);
                    passwordLabel.setVisible(false);
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
                }

                if (stateOfPerson.getSelectedItem() == "Admin") {
                    passwordTextField.setVisible(true);
                    passwordLabel.setVisible(true);
                    phoneTextField.setVisible(false);
                    numberOfPhoneLabel.setVisible(false);
                    passwordTextField.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            if (!Character.isDigit(e.getKeyChar()))
                                e.consume();
                        }
                    });
                    phoneTextField.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            if (passwordTextField.getText().length() >= 11 ) // limit textfield to 11 characters
                                e.consume();
                        }
                    });
                }

                if (stateOfPerson.getSelectedItem() == " ") {
                    passwordTextField.setVisible(false);
                    passwordLabel.setVisible(false);
                    phoneTextField.setVisible(false);
                    numberOfPhoneLabel.setVisible(false);
                }

            }
        };
        stateOfPerson.addActionListener(actionListener);


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

        container.add(passwordTextField);

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

            if (stateOfPerson.getSelectedItem() == "User") {
                int isNew = 0;
                int isVip = 0;
                int countUsers = 0;

                try {
                    preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_USERS);
                    ResultSet res2 = preparedStatement2.executeQuery();

                    while (res2.next()) {
                        countUsers = res2.getInt(1);
                        int id = res2.getInt("id");
                        String name = res2.getString("name");
                        String phone = res2.getString("phone");

                        if (userTextField.getText().equals(name) && phoneTextField.getText().equals(phone)) {
                            isVip++;
                            System.out.println(isVip);
                            JOptionPane.showMessageDialog(LoginFrame.this,
                                    "Вы наш Vip-клиент! Ваша скидка 5% на все товары");
                        }
                    }
                } catch (SQLException es) {
                    es.printStackTrace();
                }

                if (isVip == 0) {
                    isNew++;
                    try {
                        countUsers++;
                        System.out.println(countUsers);

                        preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_USER);
                        preparedStatement.setInt(1, countUsers++);
                        preparedStatement.setString(2, userTextField.getText());
                        preparedStatement.setString(3, phoneTextField.getText());
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

            else {
                dispose();
                AdminFrame adminFrame = new AdminFrame();
                adminFrame.setTitle("Admin Form");
                adminFrame.setVisible(true);
                adminFrame.setBounds(10,10,1000,1000);
                adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               // adminFrame.setResizable(false);

            }


        }

        if (e.getSource() == resetButton) {
            userTextField.setText("");
            phoneTextField.setText("");
            passwordTextField.setText("");
        }
        }
    }


