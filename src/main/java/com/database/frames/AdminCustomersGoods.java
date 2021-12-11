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

    private static final String INSERT_NEW_USER = "INSERT INTO users VALUES (?, ?, ?)";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String GET_ALL_GOODS_OF_USER = "SELECT * FROM goods";

    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;

    private String[] columnsHeader = {"id", "name", "count","price", "total"};
//    private Object[][] array2 = {{ "Kundan Kumar", "4031", "CSE", "8", "9" },
//            { "Anand Jha", "6014", "IT", "i", "9" }};

    private Object[][] array2 = {};

    JPanel panel1 = new JPanel();
    DefaultTableModel defTableModel = new DefaultTableModel(array2, columnsHeader);
    JTable defTable = new JTable(defTableModel);
    JScrollPane bookTableScrollPage = new JScrollPane(defTable); // что прокрутить

    JScrollPane scrollPane = new JScrollPane(bookTableScrollPage);


    ConnectionDb connect = new ConnectionDb();

    ArrayList<String > person = new ArrayList<>();


    Container container = getContentPane();

    JComboBox stateOfPerson = new JComboBox();

    JButton showGoodsButton = new JButton("Show Goods");
    JButton returnButton = new JButton("Return to tables");

    JTextArea checkTextArea = new JTextArea(25,5);

//    DefaultTableModel defTableModel = new DefaultTableModel(array2, columnsHeader);
//    JTable defTable = new JTable(defTableModel);
//
//
//    JScrollPane bookTableScrollPage = new JScrollPane(defTable); // что прокрутить
//    JScrollPane scrollPane = new JScrollPane(bookTableScrollPage);

//    DefaultTableModel tableModel = new DefaultTableModel();
//    JTable table = new JTable(tableModel);

    //JTable table = new JTable(array2, columnsHeader);
    JTable table = new JTable(defTableModel);
//    DefaultTableModel dtm = (DefaultTableModel) table.getModel();

  //  JScrollPane sp = new JScrollPane(table);
    AdminCustomersGoods() {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();

//        panel1.setSize(200,100);
//        panel1.add(defTable);
//        bookTableScrollPage.setPreferredSize(new Dimension(400, 100));
//        panel1.setVisible(true);
//
//        container.add(bookTableScrollPage);
        addActionEvent();
        setSize(900, 600);
        setVisible(true);





       // setResizable(false);
    }

    public void setLayoutManager() {
        container.setLayout(null);
    }

    public void setLocationAndSize() {

        stateOfPerson.setBounds(50, 150, 200, 30);
        showGoodsButton.setBounds(50,200,100,30);
        returnButton.setBounds(50,250,100,30);
        bookTableScrollPage.setBounds(300,150,500,200);
        //defTable.setBounds(300,150,400,300);
//        bookTableScrollPage.setPreferredSize(new Dimension(400, 100));
//        defTable.setBounds(300, 150,400,400);

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
                person.add(phone);
                String[] phoneUsers = new String[person.size()];
                person.toArray(phoneUsers);
                stateOfPerson.setModel(new DefaultComboBoxModel(phoneUsers));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        container.add(stateOfPerson);

        container.add(showGoodsButton);
        container.add(returnButton);

       // container.add(defTable);
//        container.add(bookTableScrollPage);
        //container.add(new JScrollPane(table));
       container.add(table);
       container.add(bookTableScrollPage);

    }

    public void addActionEvent() {
        showGoodsButton.addActionListener(this);
        returnButton.addActionListener(this);
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

                    if (id.equals(userPhone)) {

//
                        System.out.println(id + " " + name + " "+ count +
                                " " + price + " " + total);

                        Vector<String > v = new Vector<String>(5);
                        v.add(id);
                        v.add(name);
                        v.add(count);
                        v.add(price);
                        v.add(total);

                        modelSecond.addRow(v);
                        System.out.println("+");
                    }
                }
            } catch (SQLException es) {
                es.printStackTrace();
            }
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

