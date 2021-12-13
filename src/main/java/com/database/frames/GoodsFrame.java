package com.database.frames;

import com.database.connection.GoodsTableModel;
import com.database.connection.ConnectionDb;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class GoodsFrame extends JFrame {

    private final ArrayList<String> collectionOfAddedGoods = new ArrayList<>();

    private DefaultTableModel modelSecond;
    Container container = getContentPane();
    private int finalSum = 0;
    private int finalSumVip = 0;

    private static final String INSERT_NEW_GOODS = "INSERT INTO goods VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE_EXIT_USER = "DELETE FROM users WHERE phone = ?";

    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;

    public GoodsFrame() {

        ConnectionDb connect = new ConnectionDb();
        String keyPhone = LoginFrame.phoneTextField.getText();

        JPanel panel1 = new JPanel();
        panel1.setSize(200,100);
        String[] columnsHeader = {"id", "name", "count", "price", "total"};
        Object[][] array2 = {};
        DefaultTableModel defTableModel = new DefaultTableModel(array2, columnsHeader);
        JTable defTable = new JTable(defTableModel);
        panel1.add(defTable);

        JScrollPane bookTableScrollPage = new JScrollPane(defTable);
        bookTableScrollPage.setPreferredSize(new Dimension(400, 100));
        JScrollPane scrollPane = new JScrollPane(bookTableScrollPage);
        panel1.setVisible(true);

        JLabel totalSum = new JLabel();
        totalSum.setText("0");
        JLabel totalSumLabel = new JLabel();
        totalSumLabel.setText("Total sum: ");

        JLabel totalVipSum = new JLabel();
        totalVipSum.setText("0");
        totalVipSum.setVisible(false);
        JLabel totalSumVipLabel = new JLabel();
        totalSumVipLabel.setText("With discount: ");
        totalSumVipLabel.setVisible(false);

        if (LoginFrame.isVip > 0) {
            totalVipSum.setVisible(true);
            totalSumVipLabel.setVisible(true);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTabbedPane tabsLeft = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        // Создание вкладок
        int countDb = 3;
        for (int i = 1; i <= countDb; i++) {
            JPanel panel = new JPanel();
            panel.add(new JLabel("Count"));
            JButton btnPlus = new JButton();
            JButton btnMinus = new JButton();
            JButton btnAdd = new JButton();
            JLabel countLabel = new JLabel();
            int clicksCount = 0;
            countLabel.setText(Integer.toString(clicksCount));
            btnMinus.setText("-");
            btnPlus.setText("+");
            btnAdd.setText("Add");
            panel.add(btnMinus);
            panel.add(countLabel);
            panel.add(btnPlus);
            panel.add(btnAdd);

            btnPlus.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String st = countLabel.getText();
                    int k = Integer.parseInt(st);
                    if (k <= 4) k++;
                    countLabel.setText(Integer.toString(k));
                }
            });

            btnMinus.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String st = countLabel.getText();
                    int k = Integer.parseInt(st);
                    if (k >= 1)  k--;
                    countLabel.setText(Integer.toString(k));
                }
            });

            loadAllGoodsFromDb(i,connect,panel,btnAdd,defTable,countLabel,totalSum,totalVipSum, tabsLeft);
        }

        // Подключение слушателя мыши
        tabsLeft.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int idx = ((JTabbedPane) e.getSource()).indexAtLocation(e.getX(), e.getY());
                idx++;
                System.out.println("Выбрана вкладка " + idx);
            }
        });

        JButton btnGetCheque = new JButton();
        btnGetCheque.setText("Get a cheque");
        btnGetCheque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String [] row = new String[6];
                DefaultTableModel modelSecond = (DefaultTableModel) defTable.getModel();
                int length = modelSecond.getRowCount();

                int ind = 0;
                if (length > 0) {
                while (length > 0) {
                    row[0] = String.valueOf(modelSecond.getValueAt(ind, 0));
                    row[1] = String.valueOf(modelSecond.getValueAt(ind, 1));
                    row[2] = String.valueOf(modelSecond.getValueAt(ind, 2));
                    row[3] = String.valueOf(modelSecond.getValueAt(ind, 3));
                    row[4] = String.valueOf(modelSecond.getValueAt(ind, 4));
                    if (LoginFrame.isVip > 0) row[5] = String.valueOf(totalVipSum.getText());
                    else row[5] = String.valueOf(totalSum.getText());

                    length--;
                    ind++;

                    try {
                        preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_GOODS);
                        preparedStatement.setString(1, keyPhone);
                        preparedStatement.setString(2, row[1]);
                        preparedStatement.setString(3, row[2]);
                        preparedStatement.setString(4, row[3]);
                        preparedStatement.setString(5, row[4]);
                        preparedStatement.setString(6, row[5]);
                        preparedStatement.execute();
                        preparedStatement.close();
                        //connect.getConnection().close();
                        System.out.println("Selected goods are added to db");
                        connect.finish();
                        //System.out.println("Connection with db is closed");
                    }
                    catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("Selected goods aren't saved to db");
                    }
                    System.out.println("All selected goods are saved to db");
                }
                    JOptionPane.showMessageDialog(GoodsFrame.this, "Your order has accepted, our operator will call you soon by phone");
                    dispose();
                 } else
                     JOptionPane.showMessageDialog(GoodsFrame.this, "Your basket is empty - exit or choose goods");
            }});

        JButton btnDeleteGoods = new JButton();
        btnDeleteGoods.setText("Return goods");
        btnDeleteGoods.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i = defTable.getSelectedRow();
                if (i >= 0) {
                    String [] row = new String[5];

                    row[0] = String.valueOf(defTable.getValueAt(i, 0));
                    row[1] = String.valueOf(defTable.getValueAt(i, 1));
                    row[2] = String.valueOf(defTable.getValueAt(i, 2));
                    row[3] = String.valueOf(defTable.getValueAt(i, 3));
                    row[4] = String.valueOf(defTable.getValueAt(i, 4));

                    finalSum -= Integer.parseInt(row[4]);
                    collectionOfAddedGoods.remove(row[0]);
                    totalSum.setText(String.valueOf(finalSum));
                    finalSumVip = (int) (finalSum * 0.95);
                    totalVipSum.setText(String.valueOf(finalSumVip));
                    modelSecond.removeRow(defTable.getSelectedRow());
                    System.out.println("Selected goods are deleted from the basket");
                } else JOptionPane.showMessageDialog(GoodsFrame.this, "Choose an item to return");
            }});

        JButton btnExit = new JButton();
        btnExit.setText("EXIT");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(GoodsFrame.this,
                        "Do you want to exit? :",
                        String.valueOf(JOptionPane.YES_NO_OPTION),
                        JOptionPane.YES_NO_OPTION);
                if (result == 0 && LoginFrame.isVip == 0){
                    System.out.println("You pressed Yes");
                try {
                    preparedStatement2 = connect.getConnection().prepareStatement(DELETE_EXIT_USER);
                    preparedStatement2.setString(1, keyPhone); // id: 1, name: Name, age: 33, email: wfew
                    preparedStatement2.executeUpdate();
                    preparedStatement2.close();
                    //connect.getConnection().close();
                    System.out.println("Delete new user from db due to exit");
                    connect.finish();
                    //System.out.println("Connection with db is closed");
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("User isn't removed");
                 }
                }
                else if (result == 0 && LoginFrame.isVip != 0) {
                    dispose();
                    System.out.println("Last added goods of Vip user aren't saved to db due to exit");
                }
                else System.out.println("You pressed NO");
            }});

        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(tabsLeft);
        container.add(bookTableScrollPage);
        container.add(btnDeleteGoods);
        container.add(btnGetCheque);
        container.add(btnExit);
        container.add(totalSumLabel);
        container.add(totalSum);
        container.add(totalSumVipLabel);
        container.add(totalVipSum);

        setBounds(500, 150, 750, 600);
        setVisible(true);
        setResizable(false);
    }

    // common method for button Get cheque
    public void loadAllGoodsFromDb(int i, ConnectionDb connect, JPanel panel, JButton btnAdd, JTable defTable,
                                   JLabel countLabel, JLabel totalSum, JLabel totalVipSum, JTabbedPane tabsLeft) {
        String nameOfDb = "";
        if (i == 1) nameOfDb = "smartphones";
        else if (i == 2) nameOfDb = "computers";
        else if (i == 3) nameOfDb = "tv";

        GoodsTableModel goodsTableModel1 = new GoodsTableModel();
        JTable goodsTable = new JTable(goodsTableModel1);
        JScrollPane goodsTableScrollPage1 = new JScrollPane(goodsTable);
        goodsTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл
        tabsLeft.addTab(nameOfDb, panel);

        panel.add(goodsTableScrollPage1);
        switch (nameOfDb) {
            case "smartphones":
                goodsTableModel1.addDataSmartPhones(connect);
                break;
            case "computers":
                goodsTableModel1.addDataComputers(connect);
                break;
            case "tv":
                goodsTableModel1.addDataTv(connect);
                break;
        }
        System.out.println("Database of " + nameOfDb + " is uploaded");

        String finalNameOfDb = nameOfDb;
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int m = goodsTable.getSelectedRow();
                if (m >= 0) {
                    TableModel modelFirst = goodsTable.getModel();
                    String [] row = new String[5];
                    modelSecond = (DefaultTableModel)defTable.getModel();
                    int i = goodsTable.getSelectedRow();

                    row[0] = String.valueOf(modelFirst.getValueAt(i, 0));
                    row[1] = String.valueOf(modelFirst.getValueAt(i, 1));
                    row[2] = String.valueOf(Integer.parseInt(countLabel.getText()));
                    row[3] = String.valueOf(modelFirst.getValueAt(i, 3));
                    row[4] = String.valueOf(Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3])));

                    if (!countLabel.getText().equals("0") && !collectionOfAddedGoods.contains(row[0])) {
                        modelSecond.addRow(row);
                        collectionOfAddedGoods.add(row[0]);
                        System.out.println("Selected goods are added to basket from db of " + finalNameOfDb);
                        finalSum += Integer.parseInt(row[4]);
                        totalSum.setText(String.valueOf(finalSum));
                        finalSumVip = (int) (finalSum * 0.95);
                        totalVipSum.setText(String.valueOf(finalSumVip));
                    } else
                    if (collectionOfAddedGoods.contains(row[0])) {
                        JOptionPane.showMessageDialog(GoodsFrame.this, "This item is added. " +
                                "If you want to change a number of goods, return these and then choose correct count");
                    } else
                    if (countLabel.getText().equals("0")) JOptionPane.showMessageDialog(GoodsFrame.this, "Choose count of selected goods");

                } else JOptionPane.showMessageDialog(GoodsFrame.this, "Choose goods from the table");
                countLabel.setText("0");
            }});

    }




}

