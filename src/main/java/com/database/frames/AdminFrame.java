package com.database.frames;

import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AdminFrame extends JFrame implements ActionListener {

    private  final  Color[]  colors = {Color.cyan, Color.orange};
    private final int countDb = 3;
    private  final  String   TEMPL_label   = "Метка %d";
    private  final  String   TEMPL_dynamic = "Динамическая метка %d";
    private  final  String   TEMPL_button  = "Кнопка %d";
    private  final  String   TEMPL_tab     = "Вкладка %d";

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
    private int idGoods = 0;

    private BookTableModel smartModel;
    private JScrollPane smartScroll;
    private JPanel smartPanel;
    private JTable smartTable;

    private BookTableModel compModel;
    private JScrollPane compScroll;
    private JPanel compPanel;
    private JTable compTable;

    private BookTableModel tvModel;
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
    JLabel priceLabel = new JLabel("PRICE");

    JComboBox dbComboBox = new JComboBox(db);
    JTextField nameField=new JTextField();
    JTextField priceField=new JTextField();

    JButton addButton = new JButton("ADD");
    JButton deleteButton=new JButton("DELETE");
    JButton resetButton=new JButton("RESET");

    // Левая панель с вкладками
    JTabbedPane tabsLeft = new JTabbedPane(JTabbedPane.BOTTOM,
            JTabbedPane.SCROLL_TAB_LAYOUT);

    AdminFrame()
    {
        //Calling methods inside constructor.
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
    }

    public void setLocationAndSize()
    {
        dbLabel.setBounds(50,150,100,30);
        nameLabel.setBounds(50,220,100,30);
        priceLabel.setBounds(50,290,100,30);

        dbComboBox.setBounds(150,150,150,30);
        nameField.setBounds(150,220,150,30);
        priceField.setBounds(150,290,150,30);

        resetButton.setBounds(50,350,100,30);
        deleteButton.setBounds(200,350,100,30);
        addButton.setBounds(350,350,100,30);

        tabsLeft.setBounds(600,100,500,500);
    }
    public void addComponentsToContainer()
    {
        container.add(dbLabel);
        container.add(nameLabel);
        container.add(priceLabel);

        container.add(dbComboBox);
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (dbComboBox.getSelectedItem() == "smartphones") {
                    nameOfDb = "smartphones";
                }
                if (dbComboBox.getSelectedItem() == "computers") {
                    nameOfDb = "computers";
                }
                if (dbComboBox.getSelectedItem() == "tv") {
                    nameOfDb = "tv";
                }
            }
        };
        dbComboBox.addActionListener(actionListener);

        container.add(nameField);
        container.add(priceField);

        container.add(resetButton);
        container.add(deleteButton);
        container.add(addButton);

        // super("Пример панели с вкладками JTabbedPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Создание вкладок
        for (int i = 1; i <= countDb; i++) {
            JPanel panel = new JPanel();
            // Подкрашиваем панель
           // panel.setBackground(colors[i - 1]);
            // Размещение метки во вкладке
            panel.add(new JLabel(String.format(TEMPL_label, i)));
            // Добавление вкладки
            tabsLeft.addTab(String.format(TEMPL_tab, i), panel);

            BookTableModel bookTableModel1 = new BookTableModel();
            JTable bookTable1 = new JTable(bookTableModel1);
            JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1); // что прокрутить
            bookTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл

            if (i == 1) {
//                // Добавление вкладки
                smartTable = bookTable1;
                smartPanel = panel;
                smartModel = bookTableModel1;
                smartScroll = bookTableScrollPage1;
                tabsLeft.addTab("smartphones", panel);
                panel.add(bookTableScrollPage1);
                bookTableModel1.addDataSmartPhones(connect);

            }

            if (i == 2) {
                compTable = bookTable1;
                compPanel = panel;
                compModel = bookTableModel1;
                compScroll = bookTableScrollPage1;
                // Добавление вкладки
                tabsLeft.addTab("computers", panel);
                panel.add(bookTableScrollPage1);
                bookTableModel1.addDataComputers(connect);
            }

            if (i == 3) {
                tvTable = bookTable1;
                tvPanel = panel;
                tvModel = bookTableModel1;
                tvScroll = bookTableScrollPage1;
                // Добавление вкладки
                tabsLeft.addTab("tv", panel);
                panel.add(bookTableScrollPage1);
                bookTableModel1.addDataTv(connect);
            }
        }
        // Подключение слушателя событий
        tabsLeft.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                // Получение выделенной вкладки
                JPanel panel = (JPanel)((JTabbedPane)e.getSource()).getSelectedComponent();
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
                int idx = ((JTabbedPane)e.getSource()).indexAtLocation(e.getX(), e.getY());
                System.out.println("Выбрана вкладка " + idx);
            }
        });

        container.add(tabsLeft);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {

            switch (nameOfDb) {
                case "smartphones":
                    System.out.println(nameOfDb);
                    try {
                        preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_SMART);
                        ResultSet res2 = preparedStatement2.executeQuery();

                        while (res2.next()) {
                            idGoods = res2.getInt(1);
                        }
                        idGoods++;

                        preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_SMARTPHONE);
                        preparedStatement.setInt(1, idGoods);
                        preparedStatement.setString(2, nameField.getText());
                        preparedStatement.setString(3, "1");
                        preparedStatement.setString(4, priceField.getText());
                        preparedStatement.execute();

                        int id = idGoods;
                        String name = nameField.getText();
                        String count = "1";
                        String price = priceField.getText();

                        String[] row = {Integer.toString(id), name, count, price};

                        smartPanel.add(smartScroll);
                        smartModel.addData(row);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("error");
                    }

                    break;
                case "computers":
                    System.out.println(nameOfDb);
                    try {
                        preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_COMPUTERS);
                        ResultSet res2 = preparedStatement2.executeQuery();

                        while (res2.next()) {
                            idGoods = res2.getInt(1);
                        }
                        idGoods++;

                        // refresh db
                        preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_COMPUTER);
                        preparedStatement.setInt(1, idGoods);
                        preparedStatement.setString(2, nameField.getText());
                        preparedStatement.setString(3, "1");
                        preparedStatement.setString(4, priceField.getText());
                        preparedStatement.execute();

                        // refresh Panel
                        int id = idGoods;
                        String name = nameField.getText();
                        String count = "1";
                        String price = priceField.getText();

                        String[] row = {Integer.toString(id), name, count, price};

                        compPanel.add(compScroll);
                        compModel.addData(row);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("error");
                    }
                    break;
                case "tv":
                    System.out.println(nameOfDb);
                    try {
                        preparedStatement2 = connect.getConnection().prepareStatement(GET_ALL_TV);
                        ResultSet res2 = preparedStatement2.executeQuery();

                        while (res2.next()) {
                            idGoods = res2.getInt(1);
                        }
                        idGoods++;

                        preparedStatement = connect.getConnection().prepareStatement(INSERT_NEW_TV);
                        preparedStatement.setInt(1, idGoods);
                        preparedStatement.setString(2, nameField.getText());
                        preparedStatement.setString(3, "1");
                        preparedStatement.setString(4, priceField.getText());
                        preparedStatement.execute();

                        int id = idGoods;
                        String name = nameField.getText();
                        String count = "1";
                        String price = priceField.getText();

                        String[] row = {Integer.toString(id), name, count, price};

                        tvPanel.add(tvScroll);
                        tvModel.addData(row);

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("error");
                    }
                    break;
            }
        }

        if (e.getSource() == resetButton) {
            priceField.setText("");
            nameField.setText("");
            dbComboBox.setSelectedIndex(0);
        }

        if (e.getSource() == deleteButton) {
            switch (nameOfDb) {
                case "smartphones": {
                    TableModel modelFirst = smartTable.getModel();
                    int ind = smartTable.getSelectedRow();
                    String[] row = new String[4];
                    row[0] = String.valueOf(modelFirst.getValueAt(ind, 0));
                    row[1] = String.valueOf(modelFirst.getValueAt(ind, 1));
                    row[2] = String.valueOf(modelFirst.getValueAt(ind, 2));
                    row[3] = String.valueOf(modelFirst.getValueAt(ind, 3));

                    System.out.println(row[0]);

                    try {
                        preparedStatement3 = connect.getConnection().prepareStatement(DELETE_SMART);
                        preparedStatement3.setInt(1, Integer.parseInt(row[0])); // id: 1, name: Name, age: 33, email: wfew
                        preparedStatement3.executeUpdate();
                        System.out.println("try");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("no delete");
                    }
                    smartModel.removeAll();
                    smartModel.addDataSmartPhones(connect);
                    break;
                }
                case "tv": {
                    TableModel modelFirst = tvTable.getModel();
                    int ind = tvTable.getSelectedRow();
                    String[] row = new String[4];
                    row[0] = String.valueOf(modelFirst.getValueAt(ind, 0));
                    row[1] = String.valueOf(modelFirst.getValueAt(ind, 1));
                    row[2] = String.valueOf(modelFirst.getValueAt(ind, 2));
                    row[3] = String.valueOf(modelFirst.getValueAt(ind, 3));

                    System.out.println(row[0]);

                    try {
                        preparedStatement3 = connect.getConnection().prepareStatement(DELETE_TV);
                        preparedStatement3.setInt(1, Integer.parseInt(row[0])); // id: 1, name: Name, age: 33, email: wfew
                        preparedStatement3.executeUpdate();
                        System.out.println("try");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("no delete");
                    }
                    tvModel.removeAll();
                    tvModel.addDataTv(connect);
                    break;
                }
                case "computers": {
                    TableModel modelFirst = compTable.getModel();
                    int ind = compTable.getSelectedRow();
                    String[] row = new String[4];
                    row[0] = String.valueOf(modelFirst.getValueAt(ind, 0));
                    row[1] = String.valueOf(modelFirst.getValueAt(ind, 1));
                    row[2] = String.valueOf(modelFirst.getValueAt(ind, 2));
                    row[3] = String.valueOf(modelFirst.getValueAt(ind, 3));

                    System.out.println(row[0]);

                    try {
                        preparedStatement3 = connect.getConnection().prepareStatement(DELETE_COMP);
                        preparedStatement3.setInt(1, Integer.parseInt(row[0])); // id: 1, name: Name, age: 33, email: wfew
                        preparedStatement3.executeUpdate();
                        System.out.println("try");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        System.out.println("no delete");
                    }
                    compModel.removeAll();
                    compModel.addDataComputers(connect);
                    break;
                }
            }
        }
    }
}
