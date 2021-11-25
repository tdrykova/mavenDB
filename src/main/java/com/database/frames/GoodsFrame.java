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
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsFrame extends JFrame {

    private final Color[] colors = {Color.cyan, Color.orange};
    private final String TEMPL_label = "Метка %d";
    private final String TEMPL_dynamic = "Динамическая метка %d";
    private final String TEMPL_button = "Кнопка %d";
    private final String TEMPL_tab = "Вкладка %d";
    private int clicksCount = 0;

    private static final String GET_ALL_SMARTPHONES = "SELECT * FROM smartphones";
    private static final String INSERT = "INSERT image INTO smartphones VALUES(?)";

    public GoodsFrame() {
        super("Пример панели с вкладками JTabbedPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Левая панель со вкладками
        JTabbedPane tabsLeft = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
        // Создание вкладок
        for (int i = 1; i < colors.length + 1; i++) {
            JPanel panel = new JPanel();
            // Подкрашиваем панель
            //panel.setBackground(colors[i - 1]);
            ConnectionDb connect = new ConnectionDb();
            // Размещение метки во вкладке
            panel.add(new JLabel(String.format(TEMPL_label, i)));
            // name
            JLabel label = new JLabel();
            label.setLocation(500, 500);
            panel.add(label);

            if (i == 1) {
                BookTableModel bookTableModel = new BookTableModel();
                JTable bookTable = new JTable(bookTableModel);
                JScrollPane bookTableScrollPage = new JScrollPane(bookTable); // что прокрутить
                bookTableScrollPage.setPreferredSize(new Dimension(400, 200)); // размер табл

                //bookTableScrollPage.addMouseListener();

                // Добавление вкладки
                tabsLeft.addTab("smartphones", panel);

//                try {
//                    PreparedStatement preparedStatement = connect.getConnection().prepareStatement(INSERT);
//                    preparedStatement.setBlob(2, new FileInputStream("pngwing.com.png"));
//                } catch (SQLException | FileNotFoundException e) {
//                    e.printStackTrace();
//                }

                bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        int row = bookTable.rowAtPoint(evt.getPoint());
                        int col = bookTable.columnAtPoint(evt.getPoint());
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
                                    //int count = res2.getInt("img");
                                    String  price = res2.getString("price");


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

            // Подключение мнемоники
          //  tabsLeft.setMnemonicAt(i - 1, String.valueOf(i).charAt(0));
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
        JPanel panel = new JPanel();
        String[] columnNames = {
                "id",
                "name",
                "count",
                "price"
        };

        String[][] data = {
                {"addins", "02.11.2006 19:15", "Folder", ""},
                {"AppPatch", "03.10.2006 14:10", "Folder", ""},
                {"assembly", "02.11.2006 14:20", "Folder", ""},
                {"Boot", "13.10.2007 10:46", "Folder", ""},
                {"Branding", "13.10.2007 12:10", "Folder", ""},
                {"Cursors", "23.09.2006 16:34", "Folder", ""},
                {"Debug", "07.12.2006 17:45", "Folder", ""},
                {"Fonts", "03.10.2006 14:08", "Folder", ""},
                {"Help", "08.11.2006 18:23", "Folder", ""},
                {"explorer.exe", "18.10.2006 14:13", "File", "2,93MB"},
                {"helppane.exe", "22.08.2006 11:39", "File", "4,58MB"},
                {"twunk.exe", "19.08.2007 10:37", "File", "1,08MB"},
                {"nsreg.exe", "07.08.2007 11:14", "File", "2,10MB"},
                {"avisp.exe", "17.12.2007 16:58", "File", "12,67MB"},
        };

        JTable table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane);
        // frame.setPreferredSize(new Dimension(450, 200));
        // scrollPane.pack();
        //setLocationRelativeTo(null);
        panel.setVisible(true);

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
