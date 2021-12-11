package com.database.frames;

import com.database.connection.GoodsTableModel;
import com.database.connection.ConnectionDb;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminFrame extends JFrame implements ActionListener {

    private static final String INSERT_NEW_SMARTPHONE = "INSERT INTO smartphones VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_SMART = "SELECT * FROM smartphones";
    private static final String DELETE_SMART = "DELETE FROM smartphones WHERE id = ?";

    private static final String INSERT_NEW_COMPUTER = "INSERT INTO computers VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_COMPUTERS = "SELECT * FROM computers";
    private static final String DELETE_COMP = "DELETE FROM computers WHERE id = ?";

    private static final String INSERT_NEW_TV = "INSERT INTO tv VALUES (?, ?, ?, ?)";
    private static final String GET_ALL_TV = "SELECT * FROM tv";
    private static final String DELETE_TV = "DELETE FROM tv WHERE id = ?";

    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;
    PreparedStatement preparedStatement3 = null;

    ConnectionDb connect = new ConnectionDb();
    private String nameOfDb = "";
    private int idGoodsTv = 0;
    private int idGoodsComp = 0;
    private int idGoodsSmart = 0;


    private GoodsTableModel smartModel;
    private JScrollPane smartScroll;
    private JPanel smartPanel;
    private JTable smartTable;

    private GoodsTableModel compModel;
    private JScrollPane compScroll;
    private JPanel compPanel;
    private JTable compTable;

    private GoodsTableModel tvModel;
    private JScrollPane tvScroll;
    private JPanel tvPanel;
    private JTable tvTable;

    String[] db = {
            " ",
            "smartphones",
            "computers",
            "tv"
    };

    Container container = getContentPane();
    JLabel dbLabel = new JLabel("DBNAME");
    JLabel nameLabel = new JLabel("NAME");
    JLabel nameHintLabel = new JLabel("No more than 15 characters");
    JLabel priceLabel = new JLabel("PRICE");
    JLabel priceHintLabel = new JLabel("Number without spaces");

    JComboBox dbComboBox = new JComboBox(db);
    JTextField nameField = new JTextField();
    JTextField priceField = new JTextField();

    JButton addButton = new JButton("ADD");
    JButton deleteButton = new JButton("DELETE");
    JButton resetButton = new JButton("RESET");
    JButton customersGoodsButton = new JButton("SHOW GOODS");

    // Панель со вкладками
    JTabbedPane tabsLeft = new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);

    AdminFrame()
    {
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }

    public void addActionEvent() {
        addButton.addActionListener(this);
        deleteButton.addActionListener(this);
        resetButton.addActionListener(this);
        customersGoodsButton.addActionListener(this);
    }

    public void setLocationAndSize()
    {
        dbLabel.setBounds(50,150,100,30);
        nameLabel.setBounds(50,220,100,30);
        nameHintLabel.setBounds(150,250,170,30);
        priceLabel.setBounds(50,290,100,30);
        priceHintLabel.setBounds(150,320,170,30);

        dbComboBox.setBounds(150,150,170,30);
        customersGoodsButton.setBounds(350,150,150,30);
        nameField.setBounds(150,220,170,30);
        priceField.setBounds(150,290,170,30);

        resetButton.setBounds(50,390,100,30);
        deleteButton.setBounds(200,390,100,30);
        addButton.setBounds(350,390,100,30);

        tabsLeft.setBounds(600,100,600,500);
    }
    public void addComponentsToContainer() {
        container.add(dbLabel);
        container.add(nameLabel);
        container.add(nameHintLabel);
        container.add(priceLabel);
        container.add(priceHintLabel);

        container.add(dbComboBox);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (dbComboBox.getSelectedItem() == "smartphones") {
                    nameOfDb = "smartphones";
                    System.out.println("Name of db is selected");
                }
                if (dbComboBox.getSelectedItem() == "computers") {
                    nameOfDb = "computers";
                    System.out.println("Name of db is selected");
                }
                if (dbComboBox.getSelectedItem() == "tv") {
                    nameOfDb = "tv";
                    System.out.println("Name of db is selected");
                }
                if (dbComboBox.getSelectedItem() == " ") {
                    nameOfDb = "";
                }
            }
        };
        dbComboBox.addActionListener(actionListener);

        container.add(nameField);
        nameField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (nameField.getText().length() >= 15)
                    e.consume();
            }
        });

        container.add(priceField);
        priceField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()))
                    e.consume();
            }
        });
        priceField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (priceField.getText().length() >= 6)
                    e.consume();
            }
        });

        container.add(resetButton);
        container.add(deleteButton);
        container.add(addButton);
        container.add(customersGoodsButton);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание вкладок
        int countDb = 3;
        for (int i = 1; i <= countDb; i++) {
            JPanel panel = new JPanel();
            // Добавление вкладки
            String TEMPLE_tab = "Вкладка %d";
            tabsLeft.addTab(String.format(TEMPLE_tab, i), panel);

            GoodsTableModel goodsTableModel1 = new GoodsTableModel();
            JTable bookTable1 = new JTable(goodsTableModel1);
            JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1);
            bookTableScrollPage1.setPreferredSize(new Dimension(500, 200)); // размер таблицы

            if (i == 1) {
                smartTable = bookTable1;
                smartPanel = panel;
                smartModel = goodsTableModel1;
                smartScroll = bookTableScrollPage1;
                tabsLeft.addTab("smartphones", panel);
                panel.add(bookTableScrollPage1);
                goodsTableModel1.addDataSmartPhones(connect);
            }

            if (i == 2) {
                compTable = bookTable1;
                compPanel = panel;
                compModel = goodsTableModel1;
                compScroll = bookTableScrollPage1;
                tabsLeft.addTab("computers", panel);
                panel.add(bookTableScrollPage1);
                goodsTableModel1.addDataComputers(connect);
            }

            if (i == 3) {
                tvTable = bookTable1;
                tvPanel = panel;
                tvModel = goodsTableModel1;
                tvScroll = bookTableScrollPage1;
                tabsLeft.addTab("tv", panel);
                panel.add(bookTableScrollPage1);
                goodsTableModel1.addDataTv(connect);
            }
        }

        // Подключение слушателя мыши
        tabsLeft.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Определяем индекс выделенной мышкой вкладки
                int idx = ((JTabbedPane)e.getSource()).indexAtLocation(e.getX(), e.getY());
                idx++;
                System.out.println("Выбрана вкладка " + idx);
            }
        });

        container.add(tabsLeft);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {
            if (!nameField.getText().isEmpty() && !priceField.getText().isEmpty()
                    && !nameOfDb.equals("")) {
                int idGoods = 0;
                try {
                    preparedStatement = connect.getConnection().prepareStatement(GET_ALL_TV);
                    ResultSet res = preparedStatement.executeQuery();
                    preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_SMART);
                    ResultSet res2 = preparedStatement2.executeQuery();
                    preparedStatement3 = connect.getConnection().prepareStatement(GET_ALL_COMPUTERS);
                    ResultSet res3 = preparedStatement3.executeQuery();

                    while (res2.next()) {
                        idGoodsSmart = res2.getInt(1);
                    }
                    while (res.next()) {
                        idGoodsTv = res.getInt(1);
                    }
                    while (res3.next()) {
                        idGoodsComp = res3.getInt(1);
                    }
                    idGoods = Math.max(idGoodsSmart, Math.max(idGoodsTv, idGoodsComp));
                    idGoods++;
                    System.out.println(idGoods);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                switch (nameOfDb) {
                    case "smartphones":
                        System.out.println("New item ia added to db of "  + nameOfDb);
                        try {
                            preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_SMARTPHONE);
                            preparedStatement.setInt(1, idGoods);
                            preparedStatement.setString(2, nameField.getText());
                            preparedStatement.setString(3, "1");
                            preparedStatement.setString(4, priceField.getText());
                            preparedStatement.execute();

                            String name = nameField.getText();
                            String count = "1";
                            String price = priceField.getText();

                            String[] row = {Integer.toString(idGoods), name, count, price};

                            smartPanel.add(smartScroll);
                            smartModel.addData(row);

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            System.out.println("Error in an addition to db");
                        }
                        break;

                    case "computers":
                        System.out.println("New item ia added to db of " + nameOfDb);
                        try {
                            // refresh db
                            preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_COMPUTER);
                            preparedStatement.setInt(1, idGoods);
                            preparedStatement.setString(2, nameField.getText());
                            preparedStatement.setString(3, "1");
                            preparedStatement.setString(4, priceField.getText());
                            preparedStatement.execute();

                            // refresh Panel
                            String name = nameField.getText();
                            String count = "1";
                            String price = priceField.getText();

                            String[] row = {Integer.toString(idGoods), name, count, price};

                            compPanel.add(compScroll);
                            compModel.addData(row);

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            System.out.println("Error in an addition to db");
                        }
                        break;

                    case "tv":
                        System.out.println("New item ia added to db of " + nameOfDb);
                        try {
                            preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_TV);
                            preparedStatement.setInt(1, idGoods);
                            preparedStatement.setString(2, nameField.getText());
                            preparedStatement.setString(3, "1");
                            preparedStatement.setString(4, priceField.getText());
                            preparedStatement.execute();

                            String name = nameField.getText();
                            String count = "1";
                            String price = priceField.getText();

                            String[] row = {Integer.toString(idGoods), name, count, price};

                            tvPanel.add(tvScroll);
                            tvModel.addData(row);

                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            System.out.println("Error in an addition to db");
                        }
                        break;
                }
            } else if (nameOfDb.equals("")) JOptionPane.showMessageDialog(AdminFrame.this, "Choose name of db");
            else if (nameField.getText().isEmpty()) JOptionPane.showMessageDialog(AdminFrame.this, "Enter name of the item");
            else JOptionPane.showMessageDialog(AdminFrame.this, "Enter price of the item");

        }

        if (e.getSource() == resetButton) {
            priceField.setText("");
            nameField.setText("");
            dbComboBox.setSelectedIndex(0);
            System.out.println("TextFields are cleared");
        }

        if (e.getSource() == deleteButton) {

            if (!nameOfDb.equals("")) {
                switch (nameOfDb) {

                    case "smartphones": {
                        TableModel modelFirst = smartTable.getModel();
                        int ind = smartTable.getSelectedRow();
                        if (ind >= 0 && nameOfDb.equals("smartphones")) {

                            String[] row = new String[4];
                            row[0] = String.valueOf(modelFirst.getValueAt(ind, 0));

                            System.out.println(row[0]);

                            try {
                                preparedStatement3 = connect.getConnection().prepareStatement(DELETE_SMART);
                                preparedStatement3.setInt(1, Integer.parseInt(row[0]));
                                preparedStatement3.executeUpdate();
                                System.out.println("Item is deleted from db of " + nameOfDb);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                System.out.println("Items aren't deleted from db " + nameOfDb);
                            }
                            smartModel.removeAll();
                            smartModel.addDataSmartPhones(connect);
                            break;
                        } else if (ind < 0) {
                            JOptionPane.showMessageDialog(AdminFrame.this,
                                    "Choose deleted item from the table of " + nameOfDb
                            + " or change the name of selected db");
                            break;
                        }
                        }

                    case "tv": {
                        TableModel modelFirst = tvTable.getModel();
                        int ind = tvTable.getSelectedRow();
                        if (ind >= 0 && nameOfDb.equals("tv")) {

                            String[] row = new String[4];
                            row[0] = String.valueOf(modelFirst.getValueAt(ind, 0));

                            System.out.println(row[0]);

                            try {
                                preparedStatement3 = connect.getConnection().prepareStatement(DELETE_TV);
                                preparedStatement3.setInt(1, Integer.parseInt(row[0])); // id: 1, name: Name, age: 33, email: wfew
                                preparedStatement3.executeUpdate();
                                System.out.println("Item is deleted from db of " + nameOfDb);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                System.out.println("Items aren't deleted from db " + nameOfDb);
                            }
                            tvModel.removeAll();
                            tvModel.addDataTv(connect);
                            break;
                        } else if (ind < 0) {
                            JOptionPane.showMessageDialog(AdminFrame.this,
                                    "Choose deleted item from the table of " + nameOfDb
                                            + " or change the name of selected db");
                            break;
                        }
                    }
                    case "computers": {
                        TableModel modelFirst = compTable.getModel();
                        int ind = compTable.getSelectedRow();
                        if (ind >= 0 && nameOfDb.equals("computers")) {

                            String[] row = new String[4];
                            row[0] = String.valueOf(modelFirst.getValueAt(ind, 0));

                            System.out.println(row[0]);

                            try {
                                preparedStatement3 = connect.getConnection().prepareStatement(DELETE_COMP);
                                preparedStatement3.setInt(1, Integer.parseInt(row[0]));
                                preparedStatement3.executeUpdate();
                                System.out.println("Item is deleted from db of " + nameOfDb);
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                System.out.println("Items aren't deleted from db " + nameOfDb);
                            }
                            compModel.removeAll();
                            compModel.addDataComputers(connect);
                            break;
                        } else if (ind < 0) {
                            JOptionPane.showMessageDialog(AdminFrame.this,
                                    "Choose deleted item from the table of " + nameOfDb
                                            + " or change the name of selected db");
                            break;
                        }
                    }
                }
            }
            else JOptionPane.showMessageDialog(AdminFrame.this, "Choose correct name of db");
        }

        if (e.getSource() == customersGoodsButton) {
            dispose();
            new AdminCustomersGoods();
        }
    }
}
