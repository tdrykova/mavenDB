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

    private static final String INSERT_NEW_USER = "INSERT INTO users VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private String GET_COUNT_USERS = "SELECT COUNT(*) FROM users";
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    ConnectionDb connect = new ConnectionDb();
    int countUsers = 0;

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
            int isNew = 0;
            userText = userTextField.getText();
            int isVip = 0;

            try {
                preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_USERS);
                //preparedStatement.setInt(1, 1);
                ResultSet res2 = preparedStatement2.executeQuery();
//                res2.last();
//                int rowCount = res2.getRow();

                Statement stmt = connect.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(GET_COUNT_USERS);
                //Retrieving the result
                rs.next();
               // rs.last();
                //int count = rs.getInt(1);
                int count = rs.getRow();
               // System.out.println("Number of records in the cricketers_data table: " + count);

                while (res2.next() && (isVip == 0)) {

                    int id = res2.getInt("id");
                    String name = res2.getString("name");
                    int card = res2.getInt("card");
                    String  email = res2.getString("email");
                    String  status = res2.getString("status");

                    if (userTextField.getText() == name) {
                        isVip++;
                        System.out.println(isVip);
//                        JOptionPane.showMessageDialog(LoginFrame.this,
//                                        "Вы наш Vip-клиент! Добро пожаловать в магазин");


                    }

                        if (!userText.isEmpty() && customerState.getSelectedItem() == "Usual") {
                            isNew++;
                            count++;
                            try {
                                preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_USER);
                                preparedStatement.setInt(1,count);
                                preparedStatement.setString(2,userTextField.getText());
                                if (customerState.getSelectedItem() == "Vip") {
                                    preparedStatement.setString(5, "Vip");
                                    preparedStatement.setString(3, cardTextField.getText());
                                } else {
                                    preparedStatement.setString(5, "Usual");
                                    preparedStatement.setString(3, "0");
                                }
                                preparedStatement.setString(4,"tat@yandex.ru");
                                preparedStatement.execute();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }

                        } else if (userTextField.bounds().isEmpty()){
                            JOptionPane.showMessageDialog(this, "Invalid Username");
                        }

                }
                System.out.println(count);
            } catch (SQLException es) {
                es.printStackTrace();
            }
            dispose();
            new GoodsFrame();

        }

        if (e.getSource() == resetButton) {
            userTextField.setText("");
           // cardTextField.setText("");
        }

        }


    }


