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
import java.util.Set;

public class AdminCustomersGoods extends JFrame implements ActionListener {

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

  //  public Set<String> keys = GoodsFrame.mapOfGoods.keySet();


    AdminCustomersGoods() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {

        stateOfPerson.setBounds(50, 290, 100, 30);
        loginButton.setBounds(50,310,100,30);

    }

    public void addComponentsToContainer() {
        container.add(stateOfPerson);
        container.add(loginButton);

    }

    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
           // System.out.println("Ключи: " + keys);
        }
    }
}

