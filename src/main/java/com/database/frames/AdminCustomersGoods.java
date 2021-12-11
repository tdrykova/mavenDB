package com.database.frames;

import com.database.connection.ConnectionDb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class AdminCustomersGoods extends JFrame implements ActionListener {

    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String GET_ALL_GOODS_OF_USER = "SELECT * FROM goods";
    private final String[] columnsHeader = {"id", "name", "count","price", "total", "cheque"};
    private final Object[][] arrayData = {};

    ConnectionDb connect = new ConnectionDb();
    PreparedStatement preparedStatement2 = null;

    DefaultTableModel defTableModel = new DefaultTableModel(arrayData, columnsHeader);
    JTable defTable = new JTable(defTableModel);
    JScrollPane bookTableScrollPage = new JScrollPane(defTable);
    JTable table = new JTable(defTableModel);

    ArrayList<String > person = new ArrayList<>();
    Container container = getContentPane();

    JComboBox stateOfPerson = new JComboBox();
    JButton showGoodsButton = new JButton("SHOW GOODS");
    JButton returnButton = new JButton("BACK PREV PAGE");
    JButton exitButton = new JButton("EXIT");

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
        stateOfPerson.setBounds(50, 150, 170, 30);
        showGoodsButton.setBounds(50,200,170,30);
        returnButton.setBounds(50,250,170,30);
        exitButton.setBounds(50,300,170,30);
        bookTableScrollPage.setBounds(300,150,500,200);
    }

    public void addComponentsToContainer() {
        try {
            preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_USERS);
            ResultSet res2 = preparedStatement2.executeQuery();

            while (res2.next()) {
                String phone = res2.getString("phone");
                person.add(phone);
                String[] phoneUsers = new String[person.size()];
                person.toArray(phoneUsers);
                stateOfPerson.setModel(new DefaultComboBoxModel(phoneUsers));
            }
            res2.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        container.add(stateOfPerson);
        container.add(showGoodsButton);
        container.add(returnButton);
        container.add(exitButton);
       container.add(table);
       container.add(bookTableScrollPage);
    }

    public void addActionEvent() {
        showGoodsButton.addActionListener(this);
        returnButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showGoodsButton) {
            DefaultTableModel modelSecond = (DefaultTableModel) table.getModel();
            while (modelSecond.getRowCount() > 0) {
                modelSecond.removeRow(0);
            }
            String userPhone = String.valueOf(stateOfPerson.getSelectedItem());
            try {
                preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_GOODS_OF_USER);
                ResultSet res2 = preparedStatement2.executeQuery();

                while (res2.next()) {
                    String id = res2.getString(1);
                    String name = res2.getString(2);
                    String count = res2.getString(3);
                    String price = res2.getString(4);
                    String total = res2.getString(5);
                    String cheque = res2.getString(6);

                    if (id.equals(userPhone)) {
                        System.out.println(id + " " + name + " "+ count +
                                " " + price + " " + total + " " + cheque);
                        Vector<String> v = new Vector<String>(6);
                        v.add(id);
                        v.add(name);
                        v.add(count);
                        v.add(price);
                        v.add(total);
                        v.add(cheque);

                        modelSecond.addRow(v);
                    }
                }
                res2.close();
            } catch (SQLException es) {
                es.printStackTrace();
                System.out.println("Goods aren't shown");
            }
        }

        if (e.getSource() == exitButton) {
            int result = JOptionPane.showConfirmDialog(AdminCustomersGoods.this,
                    "Do you want to exit? :",
                    String.valueOf(JOptionPane.YES_NO_OPTION),
                    JOptionPane.YES_NO_OPTION);
            if (result == 0){
                System.out.println("You pressed Yes");
                dispose();
            } else System.out.println("You pressed NO");
        }

        if (e.getSource() == returnButton) {
            System.out.println("Admin has returned");
            dispose();
            AdminFrame adminFrame = new AdminFrame();
            adminFrame.setTitle("Admin Form");
            adminFrame.setVisible(true);
            adminFrame.setBounds(10, 10, 1300, 800);
            adminFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            adminFrame.setResizable(false);
        }
    }
}

