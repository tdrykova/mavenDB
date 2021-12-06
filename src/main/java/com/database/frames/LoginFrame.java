package com.database.frames;

import com.database.Person.User;
import com.database.User2;
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

public class LoginFrame extends JFrame implements ActionListener {

    private static final String INSERT_NEW_USER = "INSERT INTO users VALUES (?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM users";

    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;

    char[] str = new char[2];

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
    JLabel userHuntLabel = new JLabel("* No more than 10 characters");
    JLabel numberOfPhoneLabel = new JLabel("PHONE NUM");
    JLabel numberOfPhoneHuntLabel = new JLabel("* Ex.: 89264543266");
    JLabel passwordLabel = new JLabel("PASSWORD");
    JCheckBox showPassword = new JCheckBox("Show Password");

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

        stateOfPerson.setBounds(150, 150, 170, 30);
        userTextField.setBounds(150, 220, 170, 30);
        userHuntLabel.setBounds(150, 250, 170, 30);

        phoneTextField.setBounds(150, 290, 170, 30);
        phoneTextField.setVisible(false);
        numberOfPhoneHuntLabel.setBounds(150,320,170,30);
        numberOfPhoneHuntLabel.setVisible(false);
        passwordTextField.setBounds(150, 290, 170, 30);
        passwordTextField.setVisible(false);
        showPassword.setBounds(150,320,170,30);
        showPassword.setVisible(false);

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
                    numberOfPhoneHuntLabel.setVisible(true);
                    passwordTextField.setVisible(false);
                    passwordLabel.setVisible(false);
                    showPassword.setVisible(false);
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
                    showPassword.setVisible(true);
                    phoneTextField.setVisible(false);
                    numberOfPhoneLabel.setVisible(false);
                    numberOfPhoneHuntLabel.setVisible(false);
                    passwordTextField.addKeyListener(new KeyAdapter() {
                        public void keyTyped(KeyEvent e) {
                            if (Character.isDigit(e.getKeyChar()))
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
                    numberOfPhoneHuntLabel.setVisible(false);
                }

            }
        };
        stateOfPerson.addActionListener(actionListener);

        container.add(userTextField);
        container.add(userHuntLabel);
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
        container.add(numberOfPhoneHuntLabel);

        container.add(passwordTextField);

        container.add(showPassword);

        container.add(loginButton);
        container.add(resetButton);
    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {

            phoneTextField.getText().getChars(0,2, str,0);
            if (!userTextField.getText().isEmpty() &&
                    phoneTextField.getText().length() == 11 && str[0] == '8'
                    && str[1] == '9') {

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

                        User user = new User();
                        user.setPhoneNumber(phoneTextField.getText());
                        user.setName(userTextField.getText());
                        System.out.println(user.getName());
                        System.out.println(user.getPhoneNumber());

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

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Name or Password");
            }

            if (stateOfPerson.getSelectedItem() == "Admin") {

                if (userTextField.getText().equalsIgnoreCase("Admin") &&
                        passwordTextField.getText().equalsIgnoreCase("rroot")) {
                    //JOptionPane.showMessageDialog(this, "Login Successful");
                    dispose();
                    AdminFrame adminFrame = new AdminFrame();
                    adminFrame.setTitle("Admin Form");
                    adminFrame.setVisible(true);
                    adminFrame.setBounds(10,10,1300,800);
                    adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    // adminFrame.setResizable(false);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Name or Password");
                }
            }
        }

        if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordTextField.setEchoChar((char) 0);
            } else {
                passwordTextField.setEchoChar('*');
            }

        if (e.getSource() == resetButton) {
            userTextField.setText("");
            phoneTextField.setText("");
            passwordTextField.setText("");
        }
        }
    }
}


