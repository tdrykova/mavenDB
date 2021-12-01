package com.database.frames;

import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;

public class GoodsFrame extends JFrame {

    private final Color[] colors = {Color.cyan, Color.orange};
    private final String TEMPL_label = "Метка %d";
    private final String TEMPL_dynamic = "Динамическая метка %d";
    private int clicksCount = 0;
    private int countBuy = 0;

    private static final String GET_ALL_SMARTPHONES = "SELECT * FROM smartphones";
    private static final String INSERT = "INSERT image INTO smartphones VALUES(?)";
    private static final String SELECT_ONE = "SELECT * FROM smartphones WHERE id = ?";
    private String[] columnsHeader = {"id", "name", "count","price", "total"};

    private DefaultTableModel modelSecond;
    //private DefaultTableModel defTableModel;
    private Object[][] array2 = {};
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
            JButton btnAdd = new JButton();
            JLabel countLabel = new JLabel();
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
                    if (k >=1)  k--;
                    countLabel.setText(Integer.toString(k));
                }
            });

            // name
            JLabel label = new JLabel();
            label.setLocation(500, 500);
            panel.add(label);

            if (i == 1) {
                BookTableModel bookTableModel1 = new BookTableModel();
                JTable bookTable1 = new JTable(bookTableModel1);
                JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1); // что прокрутить
                bookTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл

                // Добавление вкладки
                tabsLeft.addTab("smartphones", panel);

                btnAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {

                    TableModel modelFirst = bookTable1.getModel();
                    int indexs[] = bookTable1.getSelectedRows();

                    Object[] row = new Object[5];

                    modelSecond = (DefaultTableModel)defTable.getModel();

                    for (int i = 0; i < indexs.length; i++) {
                        row[0] = modelFirst.getValueAt(indexs[i], 0);
                        row[1] = modelFirst.getValueAt(indexs[i], 1);
                        row[2] = Integer.parseInt(countLabel.getText());
                        row[3] = modelFirst.getValueAt(indexs[i], 3);
                        row[4] = Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3]));

                        String item = String.valueOf(row[0]) + "   " + String.valueOf(row[1]) + "   " + String.valueOf(row[2])
                                + "   " + String.valueOf(row[3]) + "   "  + String.valueOf(row[4]);
                        if (countLabel.getText() != "0") {
                            modelSecond.addRow(row);
                        }

                    }
                    countLabel.setText("0");
                }});

               // panel.add(bookTableScrollPage1);
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

                btnAdd.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        TableModel modelFirst = bookTable1.getModel();
                        int indexs[] = bookTable1.getSelectedRows();

                        Object[] row = new Object[5];

                        modelSecond = (DefaultTableModel)defTable.getModel();

                        for (int i = 0; i < indexs.length; i++) {
                            row[0] = modelFirst.getValueAt(indexs[i], 0);
                            row[1] = modelFirst.getValueAt(indexs[i], 1);
                            row[2] = Integer.parseInt(countLabel.getText());
                            row[3] = modelFirst.getValueAt(indexs[i], 3);
                            row[4] = Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3]));

                            String item = String.valueOf(row[0]) + "   " + String.valueOf(row[1]) + "   " + String.valueOf(row[2])
                                    + "   " + String.valueOf(row[3]) + "   "  + String.valueOf(row[4]);
                            if (clicksCount > 0) {
                                modelSecond.addRow(row);
                            }
                        }
                    }});

                panel.add(bookTableScrollPage1);
                bookTableModel1.addDataComputers(connect);
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

        JButton btnGoToCashier = new JButton();
        btnGoToCashier.setText("Go to a cash");
        btnGoToCashier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFrame f = new JFrame("textfield");
                f.setLayout(new BorderLayout());

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();

                // create a new button
                JButton b = new JButton("submit");
                b.setText("Get a SMS-check");
                b.setSize(new Dimension(10,20));

                JTextArea checkTextArea = new JTextArea(25,5);
//                checkTextArea.setText(checkTextArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");
                checkTextArea.setText("================  TECHNO POINT  ================\n" + "  NUM       GOODS             PRICE          QUANTITY           TOTAL    \n");
                JScrollPane scrollPane = new JScrollPane(checkTextArea);
                scrollPane.setSize(600, 400);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                panel1.add(checkTextArea);
                panel2.add(b);

                f.add(panel1, BorderLayout.NORTH);
                f.add(panel2, BorderLayout.CENTER);

                f.setSize(600, 500);
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


                Object[] row = new Object[5];
                int cols = modelSecond.getColumnCount();
                int rows = modelSecond.getRowCount();

                for (int i = 0; i < rows; i++) {
                    row[0] = modelSecond.getValueAt(i, 0);
                    row[1] = modelSecond.getValueAt(i, 1);
                    row[2] = modelSecond.getValueAt(i, 3);
                    row[3] = modelSecond.getValueAt(i, 3);
                    row[4] = modelSecond.getValueAt(i, 4);

                    String item = "   " + row[0] + "               " + row[1] + "            " + row[2]  + "           " + row[3] + "        " + row[4];
                    checkTextArea.append(item + "\n");
                    item = "";
                }
                f.setVisible(true);
            }});

        JButton btnDeleteGoods = new JButton();
        btnDeleteGoods.setText("Return selected goods");
        btnDeleteGoods.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelSecond.removeRow(defTable.getSelectedRow());
            }});

        // Определение табличного расположения компонентов
        getContentPane().setLayout(new GridLayout(4,2));
        // Добавление вкладок в панель содержимого
       getContentPane().add(tabsLeft);
       getContentPane().add(bookTableScrollPage);
       getContentPane().add(btnGoToCashier);
       getContentPane().add(btnDeleteGoods);
        // Вывод окна на экран
        setSize(800, 600);
        setVisible(true);
    }
}

