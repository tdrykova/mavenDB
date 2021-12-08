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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static com.database.frames.LoginFrame.userHash;

public class AdminCustomersGoods extends JFrame implements ActionListener {

    private static final String INSERT_NEW_USER = "INSERT INTO users VALUES (?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM users";

    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    public static HashMap<String, String> userHash = new HashMap<String, String>();

    char[] str = new char[2];

    ConnectionDb connect = new ConnectionDb();

    ArrayList<String > person = new ArrayList<>();


    Container container = getContentPane();

    JComboBox stateOfPerson = new JComboBox();

    JButton showGoodsButton = new JButton("Show Goods");
    JButton returnButton = new JButton("Return to tables");

    JTextArea checkTextArea = new JTextArea(25,5);

  //  public Set<String> keys = GoodsFrame.mapOfGoods.keySet();


    AdminCustomersGoods() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        setSize(900, 600);
        setVisible(true);
        setResizable(false);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {

        stateOfPerson.setBounds(50, 150, 200, 30);
        showGoodsButton.setBounds(50,200,100,30);
        returnButton.setBounds(50,250,100,30);
        checkTextArea.setBounds(300,150,400,300);

    }

    public void addComponentsToContainer() {
        try {
            preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_USERS);
            ResultSet res2 = preparedStatement2.executeQuery();

            while (res2.next()) {
                // countUsers = res2.getInt(1);
                int id = res2.getInt("id");
                String name = res2.getString("name");
                String phone = res2.getString("phone");
                person.add(phone + " " + name);
                String[] phoneUsers = new String[person.size()];
                person.toArray(phoneUsers);
                stateOfPerson.setModel(new DefaultComboBoxModel(phoneUsers));

//                    if (userTextField.getText().equals(name) && phoneTextField.getText().equals(phone)) {
//                        isVip++;
//                        System.out.println(isVip);
//                        JOptionPane.showMessageDialog(LoginFrame.this,
//                                "Вы наш Vip-клиент! Ваша скидка 5% на все товары");
//                    }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        container.add(stateOfPerson);

        container.add(showGoodsButton);
        container.add(returnButton);
        container.add(checkTextArea);
    }

    public void addActionEvent() {
        showGoodsButton.addActionListener(this);
        returnButton.addActionListener(this);
       // showPassword.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showGoodsButton) {

//            try {
//                preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_USERS);
//                ResultSet res2 = preparedStatement2.executeQuery();
//
//                while (res2.next()) {
//                    // countUsers = res2.getInt(1);
//                    int id = res2.getInt("id");
//                    String name = res2.getString("name");
//                    String phone = res2.getString("phone");
//                    person.add(phone);
//
////                    if (userTextField.getText().equals(name) && phoneTextField.getText().equals(phone)) {
////                        isVip++;
////                        System.out.println(isVip);
////                        JOptionPane.showMessageDialog(LoginFrame.this,
////                                "Вы наш Vip-клиент! Ваша скидка 5% на все товары");
////                    }
//
//                }
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            ResultSet res2 = preparedStatement2.executeQuery();
//
//            while (res2.next()) {
//               // countUsers = res2.getInt(1);
//                int id = res2.getInt("id");
//                String name = res2.getString("name");
//                String phone = res2.getString("phone");
//
//                if (userTextField.getText().equals(name) && phoneTextField.getText().equals(phone)) {
//                    isVip++;
//                    System.out.println(isVip);
//                    JOptionPane.showMessageDialog(LoginFrame.this,
//                            "Вы наш Vip-клиент! Ваша скидка 5% на все товары");
//                }

            System.out.println("0");
           // System.out.println("Ключи: " + keys);
           // userHash.get();
//            Set<String> hashSet = userHash.keySet();
//            for (String key: userHash.keySet()) {
//                System.out.println(userHash.get(key));
//            }
        }
    }


////                checkTextArea.setText(checkTextArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");
//                checkTextArea.setText("================  TECHNO POINT  ================\n" + "  NUM       GOODS             PRICE          QUANTITY           TOTAL    \n");
//                JScrollPane scrollPane = new JScrollPane(checkTextArea);
//                scrollPane.setSize(600, 400);
//                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
}

