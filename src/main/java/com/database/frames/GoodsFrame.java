package com.database.frames;

import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

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
    private Object[][] array2 = {};
    // Заголовки столбцов

    public GoodsFrame() {

        ConnectionDb connect = new ConnectionDb();

        JPanel panel1 = new JPanel();
        panel1.setSize(200,100);

//
//        JFrame frame = new JFrame("Test frame");
//          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JPanel panel = new JPanel();
//        panel.setSize(200,200);

       // JTable table = new JTable(data, columnNames);
//        JTextArea textArea = new JTextArea(25,25);
//        textArea.setText(textArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");
        //JTable table = new JTable();

        DefaultTableModel defTableModel = new DefaultTableModel(array2, columnsHeader);
        JTable defTable = new JTable(defTableModel);

        panel1.add(defTable);
        JScrollPane bookTableScrollPage = new JScrollPane(defTable); // что прокрутить
        bookTableScrollPage.setPreferredSize(new Dimension(400, 100));

        JScrollPane scrollPane = new JScrollPane(bookTableScrollPage);


//        panel1.add(scrollPane);
        // frame.setPreferredSize(new Dimension(450, 200));
        // scrollPane.pack();
        //setLocationRelativeTo(null);
        panel1.setVisible(true);

        // JTable table = new JTable(data, columnNames);
//        JTextArea textArea = new JTextArea(25,25);
//        textArea.setText(textArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");

//        String[] colHeadings = new String[] {"id","name", "price","count", "total"};
//        int numRows = 0;
//        DefaultTableModel model = new DefaultTableModel(numRows, colHeadings.length);
//        model.setColumnIdentifiers(colHeadings);
//        JTable table = new JTable(model);
//        DefaultTableModel model1 = (DefaultTableModel)table.getModel();

       // JScrollPane scrollPane = new JScrollPane(textArea);

      //  panel1.add(scrollPane);
        // frame.setPreferredSize(new Dimension(450, 200));
        // scrollPane.pack();
        //setLocationRelativeTo(null);


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
                BookTableModel bookTableModel1 = new BookTableModel();
                JTable bookTable1 = new JTable(bookTableModel1);
                JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1); // что прокрутить
                bookTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл


                // Добавление вкладки
                tabsLeft.addTab("smartphones", panel);

                btnBuy.addActionListener(new ActionListener() {
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

                      //  ArrayList<String > arrayList = new ArrayList<>();


                        String item = String.valueOf(row[0]) + "   " + String.valueOf(row[1]) + "   " + String.valueOf(row[2])
                                + "   " + String.valueOf(row[3]) + "   "  + String.valueOf(row[4]);
                        modelSecond.addRow(row);
                        //item = "";
                        // arrayList.add(item);
                      //  ListOfGoods.jt.append(item);
                    }


                }});

//                bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
//                    @Override
//                    public void mouseClicked(java.awt.event.MouseEvent evt) {
//                        int row = bookTable.rowAtPoint(evt.getPoint());
//                        int col = bookTable.columnAtPoint(evt.getPoint());
//                        clicksCount = 0;
//                        int kol = 0;
//                        countLabel.setText(Integer.toString(clicksCount));
//                        if (row >= 0 && col >= 0) {
//                            System.out.println(row);
//                            System.out.println(col);
//                            btnBuy.addActionListener(new ActionListener() {
//                                @Override
//                                public void actionPerformed(ActionEvent e) {
//                                    int idx = bookTable.getSelectedRow();
//                                    Vector<String> v = new Vector<String>(5);
////                                    v.add( Integer.toString(row));
////                                    v.add( String.valueOf(table.getValueAt(row+1,0)));
////                                    v.add( String.valueOf(table.getValueAt(row+1,1)));
////                                    v.add( String.valueOf(table.getValueAt(row+1,2)));
////                                    v.add( String.valueOf(table.getValueAt(row+1,3)));
////                                    model1.addRow(v);
//                                    model.insertRow(idx , new String[] {"Товар №" + String.valueOf(bookTable.getRowCount()),
//                                            "кг", "Цена"});
//                                }
//
//
//
//                            });
//
//                            //model.addRow(new Object[]{id, name, price, countLabel.getText(), total});
//                            //JLabel label = new JLabel();
//                            //label.setText("id" + row);
////                            PreparedStatement preparedStatement = null;
////                            PreparedStatement preparedStatement3 = null;
////                           // preparedStatement = connect.getMySqlConnection(connect).prepareStatement(GET_ROW_COMPUTER);
////                            try {
////                                preparedStatement = connect.getConnection().prepareStatement(GET_ALL_SMARTPHONES);
////                                //preparedStatement.setInt(1, 1);
////                                preparedStatement3 = connect.getConnection().prepareStatement(SELECT_ONE);
////                                ResultSet res2 = preparedStatement.executeQuery();
////
////                                preparedStatement3.setInt(1, row+1);
////                                ResultSet res3 = preparedStatement3.executeQuery();
////
////                                while (res2.next()) {
////                                    kol++;
////                                    int id = res2.getInt("id");
////                                    String name = res2.getString("name");
////                                    int count = res2.getInt("count");
////                                    String  price = res2.getString("price");
//
////                                    if (id == row + 1 && kol == id) {
////                                        btnBuy.addActionListener(new ActionListener() {
////                                            public void actionPerformed(ActionEvent e) {
////
////                                                {
////                                                    int priceInt = Integer.parseInt(price);
////                                                    int countInt = Integer.parseInt(countLabel.getText());
////                                                    int total = countInt * priceInt;
////                                                    String st = Integer.toString(id) + "          " + name + "          " + price + "          " + countLabel.getText() + "     " + total;
////                                                    //textArea.append(st);
////                                                    // table.addRowSelectionInterval(row, row);
////                                                    st = "";
////                                                    //model1.addRow(new Object[] {id, name, price, countLabel.getText(), total});
////                                                    model.addRow(new Object[]{id, name, price, countLabel.getText(), total});
////                                                    label.setText(name);
////                                                    countBuy++;
////                                                }
////                                            }
////                                        });
////
////                                    }
//
//
//
////                                    if (id == row + 1) {
////
////                                        System.out.println("id: " + id + ", name: " + name + "," +
////                                                  ", price: " + price);
////
////                                        label.setText(name);
////
////                                    }
////                                }
////                            } catch (SQLException e) {
////                                e.printStackTrace();
////                            }
//
//                        }
//                    }
//                });



                panel.add(bookTableScrollPage1);
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
        JButton btnGoToCashier = new JButton();
        btnGoToCashier.setText("Go to a cash");
        btnGoToCashier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               // ListOfGoods listOfGoods = new ListOfGoods();
                JFrame fr2 = new JFrame();
                //int indexs[] = new int[0];

                JFrame f = new JFrame("textfield");
                f.setLayout(new BorderLayout());

                JPanel panel1 = new JPanel();
                JPanel panel2 = new JPanel();


                // create a label to display text
               // l = new JLabel("nothing entered");

                // create a new button
                JButton b = new JButton("submit");
                b.setText("Get a SMS-check");
                b.setSize(new Dimension(10,20));

                JTextArea checkTextArea = new JTextArea(25,5);
                checkTextArea.setText(checkTextArea.getText() + "================  TECHNO POINT  ==========\n" + "\t  NUM       GOODS    PRICE QUANTITY   TOTAL\n\t ");
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

               // DefaultTableModel modelSecond = (DefaultTableModel)defTable.getModel();
                JTable jTable = new JTable(modelSecond);


                for (int i = 0; i < rows; i++) {
                    row[0] = modelSecond.getValueAt(i, 0);
                    row[1] = modelSecond.getValueAt(i, 1);
                    row[2] = modelSecond.getValueAt(i, 3);
                    row[3] = modelSecond.getValueAt(i, 3);
                    row[4] = modelSecond.getValueAt(i, 4);

                    //  ArrayList<String > arrayList = new ArrayList<>();


                    String item = row[0] + "   " + row[1] + "   " + row[2]  + "   " + row[3] + "   " + row[4];

                    checkTextArea.append(item + "\n");
                    item = "";

                }
                f.setVisible(true);
            }});

        JButton btnDeleteGoods = new JButton();
        btnDeleteGoods.setText("Return selected goods");
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

