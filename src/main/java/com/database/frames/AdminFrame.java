package com.database.frames;

import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;
import com.database.templates.TabbedPaneTemplate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdminFrame extends JFrame implements ActionListener {

    private  final  Color[]  colors = {Color.cyan, Color.orange};
    private  final  String   TEMPL_label   = "Метка %d";
    private  final  String   TEMPL_dynamic = "Динамическая метка %d";
    private  final  String   TEMPL_button  = "Кнопка %d";
    private  final  String   TEMPL_tab     = "Вкладка %d";

    Container container = getContentPane();
    JLabel dbLabel = new JLabel("DBNAME");
    JLabel nameLabel = new JLabel("NAME");
    JLabel priceLabel = new JLabel("PRICE");

    JTextField dbTextField=new JTextField();
    JTextField nameField=new JTextField();
    JTextField priceField=new JTextField();

    JButton addButton=new JButton("ADD");
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
       // new TabbedPaneTemplate();

    }
    public void setLayoutManager()
    {
        container.setLayout(null);
    }
    public void setLocationAndSize()
    {
        dbLabel.setBounds(50,150,100,30);
        nameLabel.setBounds(50,220,100,30);
        priceLabel.setBounds(50,290,100,30);

        dbTextField.setBounds(150,150,150,30);
        nameField.setBounds(150,220,150,30);
        priceField.setBounds(150,290,150,30);

        resetButton.setBounds(50,350,100,30);
        deleteButton.setBounds(200,350,100,30);
        addButton.setBounds(350,350,100,30);

        tabsLeft.setBounds(350,350,800,800);
    }
    public void addComponentsToContainer()
    {
        container.add(dbLabel);
        container.add(nameLabel);
        container.add(priceLabel);

        container.add(dbTextField);
        container.add(nameField);
        container.add(priceField);

        container.add(resetButton);
        container.add(deleteButton);
        container.add(addButton);


        ConnectionDb connect = new ConnectionDb();

        // super("Пример панели с вкладками JTabbedPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Создание вкладок
        for (int i = 1; i < colors.length + 1; i++) {
            JPanel panel = new JPanel();
            // Подкрашиваем панель
            panel.setBackground(colors[i - 1]);
            // Размещение метки во вкладке
            panel.add(new JLabel(String.format(TEMPL_label, i)));
            // Добавление вкладки
            tabsLeft.addTab(String.format(TEMPL_tab, i), panel);


            if (i == 1) {
                BookTableModel bookTableModel1 = new BookTableModel();
                JTable bookTable1 = new JTable(bookTableModel1);
                JScrollPane bookTableScrollPage1 = new JScrollPane(bookTable1); // что прокрутить
                bookTableScrollPage1.setPreferredSize(new Dimension(400, 200)); // размер табл

                // Добавление вкладки
                tabsLeft.addTab("smartphones", panel);
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

    }

    public void addNewGoods() {



    }


}
