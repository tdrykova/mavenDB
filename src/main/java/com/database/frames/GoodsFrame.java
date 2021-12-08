package com.database.frames;

import com.database.Person.User;
import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;
import javafx.beans.binding.MapExpression;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GoodsFrame extends JFrame {

    // public static MapExpression<String, String> mapOfGoods = new ;
    // public static MapExpression<Object, Object> mapOfGoods;
    private final Color[] colors = {Color.cyan, Color.orange, Color.orange};
    private final int countDb = 3;
    private final String TEMPL_label = "Метка %d";
    private int clicksCount = 0;
    private int countAddGoods = 0;

    private static final String GET_ALL_SMARTPHONES = "SELECT * FROM smartphones";
    private static final String INSERT = "INSERT image INTO smartphones VALUES(?)";
    private static final String SELECT_ONE = "SELECT * FROM smartphones WHERE id = ?";
    private String[] columnsHeader = {"id", "name", "count","price", "total"};
    private ArrayList<String> nameOfGoods = new ArrayList<>();

    private DefaultTableModel modelSecond;
    //private DefaultTableModel defTableModel;
    private Object[][] array2 = {};
    Container container = getContentPane();
    private int finalSum = 0;

    private static final String INSERT_NEW_GOODS = "INSERT INTO goods VALUES (?, ?, ?, ?, ?)";
    private static final String GET_ALL_GOODS = "SELECT * FROM goods";

    private static final String DELETE_GOODS = "DELETE FROM goods WHERE id = ? AND name = ?";

    PreparedStatement preparedStatement3 = null;
    PreparedStatement preparedStatement = null;
    PreparedStatement preparedStatement2 = null;

    //public ArrayList<String []> dataArrayList;
 // public HashMap<String, String> mapOfGoods = new HashMap<String, String>();
    // Заголовки столбцов

    public GoodsFrame() {


        ConnectionDb connect = new ConnectionDb();

        JPanel panel1 = new JPanel();
        panel1.setSize(200,100);
        DefaultTableModel defTableModel = new DefaultTableModel(array2, columnsHeader);
        JTable defTable = new JTable(defTableModel);
        panel1.add(defTable);

        JScrollPane bookTableScrollPage = new JScrollPane(defTable); // что прокрутить
        bookTableScrollPage.setPreferredSize(new Dimension(400, 100));
        JScrollPane scrollPane = new JScrollPane(bookTableScrollPage);
        panel1.setVisible(true);

        JLabel totalSum = new JLabel();
        totalSum.setText("0");
        JLabel totalSumLabel = new JLabel();
        totalSumLabel.setText("Total sum: ");
       // panel1.add(totalSum);

       // super("Пример панели с вкладками JTabbedPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        String keyPhone = LoginFrame.phoneTextField.getText();
        // Левая панель со вкладками
        JTabbedPane tabsLeft = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        // Создание вкладок
        for (int i = 1; i <= countDb; i++) {
            JPanel panel = new JPanel();
            // Размещение метки во вкладке
            panel.add(new JLabel("Count"));
           // panel.add(new JLabel("* no more than 5 items"));
            JButton btnPlus = new JButton();
            JButton btnMinus = new JButton();
            JButton btnAdd = new JButton();
            JLabel countLabel = new JLabel();
            countLabel.setText(Integer.toString(clicksCount));
            btnMinus.setText("-");
            btnPlus.setText("+");
            btnAdd.setText("Add");
         //   btnAdd.setEnabled(false);
//            btnMinus.setEnabled(false);
//            btnPlus.setEnabled(false);
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
                    if (k >=1)  k--;
                    countLabel.setText(Integer.toString(k));
                }
            });

            // name
            JLabel label = new JLabel();
            label.setLocation(500, 500);
            panel.add(label);
            // ключ бд товаров, выбранных пользователем
//            String keyPhone = LoginFrame.phoneTextField.getText();
            if (i == 1) {
                BookTableModel bookTableModel1 = new BookTableModel();
                JTable bookTable1 = new JTable(bookTableModel1);
                JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1); // что прокрутить
                bookTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл
                tabsLeft.addTab("smartphones", panel);

                btnAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int m = bookTable1.getSelectedRow();
                    System.out.println(m);
                    if (m >= 0) {
                        TableModel modelFirst = bookTable1.getModel();
                        String [] row = new String[5];
                        modelSecond = (DefaultTableModel)defTable.getModel();
                        int i = bookTable1.getSelectedRow();

                        row[0] = String.valueOf(modelFirst.getValueAt(i, 0));
                        row[1] = String.valueOf(modelFirst.getValueAt(i, 1));
                        row[2] = String.valueOf(Integer.parseInt(countLabel.getText()));
                        row[3] = String.valueOf(modelFirst.getValueAt(i, 3));
                        row[4] = String.valueOf(Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3])));

                        String item = "id: " + row[0] + "; name: " + row[1] + "; count:" + row[2]
                                + "; price: " + row[3] + "; total: "  + row[4];


                        if (!countLabel.getText().equals("0") && !nameOfGoods.contains(row[1])) {
                            modelSecond.addRow(row);
                            nameOfGoods.add(row[1]);
                            System.out.println(nameOfGoods);
                            finalSum += Integer.parseInt(row[4]);
                            totalSum.setText(String.valueOf(finalSum));

                            try {
                                preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_GOODS);
                                preparedStatement.setString(1, keyPhone);
                                preparedStatement.setString(2, row[1]);
                                preparedStatement.setString(3, row[2]);
                                preparedStatement.setString(4, row[3]);
                                preparedStatement.setString(5, row[4]);
                                preparedStatement.execute();

                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        } else
                            if (nameOfGoods.contains(row[1])) {
                                JOptionPane.showMessageDialog(GoodsFrame.this, "This item is added. " +
                                        "If you want to change a number of goods, return these and then choose correct count");
                            } else
                                if (countLabel.getText().equals("0")) JOptionPane.showMessageDialog(GoodsFrame.this, "Choose count of selected goods");

                    } else JOptionPane.showMessageDialog(GoodsFrame.this, "Choose goods from the table");
                      countLabel.setText("0");
                }});

                panel.add(bookTableScrollPage1);
                bookTableModel1.addDataSmartPhones(connect);
            }

            if (i == 2) {
                BookTableModel bookTableModel1 = new BookTableModel();
                JTable bookTable1 = new JTable(bookTableModel1);
                JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1); // что прокрутить
                bookTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл

                // Добавление вкладки
                tabsLeft.addTab("computers", panel);
                panel.add(bookTableScrollPage1);
                bookTableModel1.addDataComputers(connect);

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int m = bookTable1.getSelectedRow();
                        System.out.println(m);
                        if (m >= 0) {
                            TableModel modelFirst = bookTable1.getModel();
                            String[] row = new String[5];
                            modelSecond = (DefaultTableModel) defTable.getModel();
                            int i = bookTable1.getSelectedRow();

                            row[0] = String.valueOf(modelFirst.getValueAt(i, 0));
                            row[1] = String.valueOf(modelFirst.getValueAt(i, 1));
                            row[2] = String.valueOf(Integer.parseInt(countLabel.getText()));
                            row[3] = String.valueOf(modelFirst.getValueAt(i, 3));
                            row[4] = String.valueOf(Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3])));

                            String item = "id: " + row[0] + "; name: " + row[1] + "; count:" + row[2]
                                    + "; price: " + row[3] + "; total: " + row[4];

                            if (!countLabel.getText().equals("0") && !nameOfGoods.contains(row[1])) {
                                modelSecond.addRow(row);
                                nameOfGoods.add(row[1]);
                                System.out.println(nameOfGoods);
                                finalSum += Integer.parseInt(row[4]);
                                totalSum.setText(String.valueOf(finalSum));

                                try {
                                    preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_GOODS);
                                    preparedStatement.setString(1, keyPhone);
                                    preparedStatement.setString(2, row[1]);
                                    preparedStatement.setString(3, row[2]);
                                    preparedStatement.setString(4, row[3]);
                                    preparedStatement.setString(5, row[4]);
                                    preparedStatement.execute();

                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }

                            } else
                            if (nameOfGoods.contains(row[1])) {
                                JOptionPane.showMessageDialog(GoodsFrame.this, "This item is added. " +
                                        "If you want to change a number of goods, return these and then choose correct count");
                            } else
                            if (countLabel.getText().equals("0")) JOptionPane.showMessageDialog(GoodsFrame.this, "Choose count of selected goods");

                        } else JOptionPane.showMessageDialog(GoodsFrame.this, "Choose goods from the table");
                        countLabel.setText("0");
                    }});
            }

            if (i == 3) {
                BookTableModel bookTableModel1 = new BookTableModel();
                JTable bookTable1 = new JTable(bookTableModel1);
                JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1); // что прокрутить
                bookTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл

                // Добавление вкладки
                tabsLeft.addTab("tv", panel);
                panel.add(bookTableScrollPage1);
                bookTableModel1.addDataTv(connect);

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int m = bookTable1.getSelectedRow();
                        System.out.println(m);
                        if (m >= 0) {
                            TableModel modelFirst = bookTable1.getModel();
                            String[] row = new String[5];
                            modelSecond = (DefaultTableModel) defTable.getModel();
                            int i = -1;
                            i = bookTable1.getSelectedRow();
                          //  if (i != -1) {
                                // btnAdd.setEnabled(true);
                                row[0] = String.valueOf(modelFirst.getValueAt(i, 0));
                                row[1] = String.valueOf(modelFirst.getValueAt(i, 1));
                                row[2] = String.valueOf(Integer.parseInt(countLabel.getText()));
                                row[3] = String.valueOf(modelFirst.getValueAt(i, 3));
                                row[4] = String.valueOf(Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3])));
                            //  }
                            String item = "id: " + row[0] + "; name: " + row[1] + "; count:" + row[2]
                                    + "; price: " + row[3] + "; total: " + row[4];

                            if (!countLabel.getText().equals("0") && !nameOfGoods.contains(row[1])) {
                                modelSecond.addRow(row);
                                nameOfGoods.add(row[1]);
                                System.out.println(nameOfGoods);
                                finalSum += Integer.parseInt(row[4]);
                                totalSum.setText(String.valueOf(finalSum));

                                try {
                                    preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_GOODS);
                                    preparedStatement.setString(1, keyPhone);
                                    preparedStatement.setString(2, row[1]);
                                    preparedStatement.setString(3, row[2]);
                                    preparedStatement.setString(4, row[3]);
                                    preparedStatement.setString(5, row[4]);
                                    preparedStatement.execute();

                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }
                            } else
                            if (nameOfGoods.contains(row[1])) {
                                JOptionPane.showMessageDialog(GoodsFrame.this, "This item is added. " +
                                        "If you want to change a number of goods, return these and then choose correct count");
                            } else
                            if (countLabel.getText().equals("0")) JOptionPane.showMessageDialog(GoodsFrame.this, "Choose count of selected goods");

                        } else JOptionPane.showMessageDialog(GoodsFrame.this, "Choose goods from the table");

                        countLabel.setText("0");
                    }});
            }
        }

        // Подключение слушателя событий
        tabsLeft.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                // Получение выделенной вкладки
                JPanel panel = (JPanel) ((JTabbedPane) e.getSource()).getSelectedComponent();
                // Количество компонентов в панели
                int count = panel.getComponentCount();
                // Добавление на вкладку новой метки
               // panel.add(new JLabel(String.format(TEMPL_dynamic, count)));
            }
        });

        // Подключение слушателя мыши
        tabsLeft.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Определяем индекс выделенной мышкой вкладки
                int idx = ((JTabbedPane) e.getSource()).indexAtLocation(e.getX(), e.getY());
                System.out.println("Выбрана вкладка " + idx);
            }
        });

        JButton btnGetCheque = new JButton();
        btnGetCheque.setText("Get a cheque");
        // the amount of your order is 500 rubles

       // btnGoToCashier.addActionListener();
//        btnGoToCashier.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                JFrame f = new JFrame("textfield");
//                f.setLayout(new BorderLayout());
//
//                JPanel panel1 = new JPanel();
//                JPanel panel2 = new JPanel();
//
//                // create a new button
//                JButton b = new JButton("submit");
//                b.setText("Get a SMS-check");
//                b.setSize(new Dimension(10,20));
//
//                JTextArea checkTextArea = new JTextArea(25,5);
////                checkTextArea.setText(checkTextArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");
//                checkTextArea.setText("================  TECHNO POINT  ================\n" + "  NUM       GOODS             PRICE          QUANTITY           TOTAL    \n");
//                JScrollPane scrollPane = new JScrollPane(checkTextArea);
//                scrollPane.setSize(600, 400);
//                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//                panel1.add(checkTextArea);
//                panel2.add(b);
//
//                f.add(panel1, BorderLayout.NORTH);
//                f.add(panel2, BorderLayout.CENTER);
//
//                f.setSize(600, 500);
//                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//
//
//                Object[] row = new Object[5];
//                int cols = modelSecond.getColumnCount();
//                int rows = modelSecond.getRowCount();
//
//                for (int i = 0; i < rows; i++) {
//                    countAddGoods++;
////                    row[0] = modelSecond.getValueAt(i, 0);
//                    row[0] = countAddGoods;
//                    row[1] = modelSecond.getValueAt(i, 1);
//                    row[2] = modelSecond.getValueAt(i, 3);
//                    row[3] = modelSecond.getValueAt(i, 3);
//                    row[4] = modelSecond.getValueAt(i, 4);
//
//                    String item = "   " + row[0] + "               " + row[1] + "            " + row[2]  + "           " + row[3] + "        " + row[4];
//                    checkTextArea.append(item + "\n");
//                    item = "";
//                }
//                f.setVisible(true);
//            }});

        JButton btnDeleteGoods = new JButton();
        btnDeleteGoods.setText("Return selected goods");
        btnDeleteGoods.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int i = defTable.getSelectedRow();
                String [] row = new String[5];

                row[0] = String.valueOf(defTable.getValueAt(i, 0));
                row[1] = String.valueOf(defTable.getValueAt(i, 1));
                row[2] = String.valueOf(defTable.getValueAt(i, 2));
                row[3] = String.valueOf(defTable.getValueAt(i, 3));
                row[4] = String.valueOf(defTable.getValueAt(i, 4));

                finalSum -= Integer.parseInt(row[4]);
                nameOfGoods.remove(row[1]);
                totalSum.setText(String.valueOf(finalSum));
                System.out.println(row[4]);
                modelSecond.removeRow(defTable.getSelectedRow());

                try {
                    preparedStatement3 = connect.getConnection().prepareStatement(DELETE_GOODS);
                    preparedStatement3.setString(1, keyPhone); // id: 1, name: Name, age: 33, email: wfew
                    preparedStatement3.setString(2, row[1]); // id: 1, name: Name, age: 33, email: wfew
                    preparedStatement3.executeUpdate();
                    System.out.println("try");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    System.out.println("no delete");
                }

            }});

        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(tabsLeft);
        container.add(bookTableScrollPage);
        container.add(btnDeleteGoods);
        container.add(btnGetCheque);
        container.add(totalSumLabel);
        container.add(totalSum);
        setSize(700, 600);
        setVisible(true);
        setResizable(false);
    }




}

