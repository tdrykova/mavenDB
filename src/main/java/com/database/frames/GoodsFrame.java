package com.database.frames;

import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsFrame extends JFrame {

    private final Color[] colors = {Color.cyan, Color.orange};
    private final String TEMPL_label = "Метка %d";
    private final String TEMPL_dynamic = "Динамическая метка %d";
    private int clicksCount = 0;

    private static final String GET_ALL_SMARTPHONES = "SELECT * FROM smartphones";
    private static final String INSERT = "INSERT image INTO smartphones VALUES(?)";



    public GoodsFrame() {

        ConnectionDb connect = new ConnectionDb();
        JPanel panel1 = new JPanel();
        panel1.setSize(200,200);

        // JTable table = new JTable(data, columnNames);
        JTextArea textArea = new JTextArea(25,25);
        textArea.setText(textArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");


        JScrollPane scrollPane = new JScrollPane(textArea);

        panel1.add(scrollPane);
        // frame.setPreferredSize(new Dimension(450, 200));
        // scrollPane.pack();
        //setLocationRelativeTo(null);
        panel1.setVisible(true);


       // super("Пример панели с вкладками JTabbedPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Левая панель со вкладками
        JTabbedPane tabsLeft = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        // Создание вкладок
        for (int i = 1; i < colors.length + 1; i++) {
            JPanel panel = new JPanel();
            // Размещение метки во вкладке
            panel.add(new JLabel(String.format(TEMPL_label, i)));
            JButton btnPlus = new JButton();
            JButton btnMinus = new JButton();
            JButton btnBuy = new JButton();
            JLabel countLabel = new JLabel();
            countLabel.setText(Integer.toString(clicksCount));
            btnPlus.setText("+");
            btnMinus.setText("-");
            btnBuy.setText("Buy");
            panel.add(btnPlus);
            panel.add(countLabel);
            panel.add(btnMinus);
            panel.add(btnBuy);

            btnPlus.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String st = countLabel.getText();
                    int k = Integer.parseInt(st);
                    k++;
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

            if (i == 1) {
                BookTableModel bookTableModel = new BookTableModel();
                JTable bookTable = new JTable(bookTableModel);
                JScrollPane bookTableScrollPage = new JScrollPane(bookTable); // что прокрутить
                bookTableScrollPage.setPreferredSize(new Dimension(400, 200)); // размер табл


                // Добавление вкладки
                tabsLeft.addTab("smartphones", panel);



                bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        int row = bookTable.rowAtPoint(evt.getPoint());
                        int col = bookTable.columnAtPoint(evt.getPoint());
                        clicksCount = 0;
                        countLabel.setText(Integer.toString(clicksCount));
                        if (row >= 0 && col >= 0) {
                            //JLabel label = new JLabel();
                            //label.setText("id" + row);
                            PreparedStatement preparedStatement = null;
                           // preparedStatement = connect.getMySqlConnection(connect).prepareStatement(GET_ROW_COMPUTER);
                            try {
                                preparedStatement = connect.getConnection().prepareStatement(GET_ALL_SMARTPHONES);
                                //preparedStatement.setInt(1, 1);
                                ResultSet res2 = preparedStatement.executeQuery();

                                while (res2.next()) {
                                    int id = res2.getInt("id");
                                    String name = res2.getString("name");
                                    int count = res2.getInt("count");
                                    String  price = res2.getString("price");

                                    btnBuy.addActionListener(new ActionListener() {
                                        public void actionPerformed(ActionEvent e) {
                                            if (id == row + 1) {
                                            int priceInt = Integer.parseInt(price);
                                            int countInt = Integer.parseInt(countLabel.getText());
                                            int total = countInt * priceInt;
                                            String st = Integer.toString(id) + "          " + name + "          " + price + "          " + countLabel.getText() + "     " + total;
                                                textArea.append(st);
                                                st = "";

                                                label.setText(name);

                                            }
 }
                                    });
                                    if (id == row + 1) {

                                        System.out.println("id: " + id + ", name: " + name + "," +
                                                  ", price: " + price);

                                        label.setText(name);

                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });



                panel.add(bookTableScrollPage);
                panel.add(bookTableScrollPage);
                bookTableModel.addDataSmartPhones(connect);
            }

            if (i == 2) {
                BookTableModel bookTableModel = new BookTableModel();
                JTable bookTable = new JTable(bookTableModel);
                JScrollPane bookTableScrollPage = new JScrollPane(bookTable); // что прокрутить
                bookTableScrollPage.setPreferredSize(new Dimension(400, 400)); // размер табл

                // Добавление вкладки
                tabsLeft.addTab("computers", panel);

                panel.add(bookTableScrollPage);
                bookTableModel.addDataComputers(connect);
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
                panel.add(new JLabel(String.format(TEMPL_dynamic, count)));
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




        //  JFrame frame = new JFrame("Test frame");
        //  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JPanel panel = new JPanel();
//        panel.setSize(200,200);
//
//       // JTable table = new JTable(data, columnNames);
//        JTextArea textArea = new JTextArea(25,25);
//        textArea.setText(textArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");
//
//
//        JScrollPane scrollPane = new JScrollPane(textArea);
//
//        panel.add(scrollPane);
//        // frame.setPreferredSize(new Dimension(450, 200));
//        // scrollPane.pack();
//        //setLocationRelativeTo(null);
//        panel.setVisible(true);

        // Определение табличного расположения компонентов
        getContentPane().setLayout(new GridLayout());
        // Добавление вкладок в панель содержимого
        getContentPane().add(tabsLeft);
        getContentPane().add(scrollPane);
        // Вывод окна на экран
        setSize(800, 400);
        setVisible(true);
    }
}
