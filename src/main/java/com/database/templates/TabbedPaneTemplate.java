package com.database.templates;

import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TabbedPaneTemplate extends JFrame {

    private  final  Color[]  colors = {Color.cyan, Color.orange};
    private  final  String   TEMPL_label   = "Метка %d";
    private  final  String   TEMPL_dynamic = "Динамическая метка %d";
    private  final  String   TEMPL_button  = "Кнопка %d";
    private  final  String   TEMPL_tab     = "Вкладка %d";
    public TabbedPaneTemplate()
    {
        ConnectionDb connect = new ConnectionDb();

       // super("Пример панели с вкладками JTabbedPane");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Левая панель с вкладками
        JTabbedPane tabsLeft = new JTabbedPane(JTabbedPane.BOTTOM,
                JTabbedPane.SCROLL_TAB_LAYOUT);
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

//                btnAdd.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//
//                        TableModel modelFirst = bookTable1.getModel();
//                        int indexs[] = bookTable1.getSelectedRows();
//
//                        Object[] row = new Object[5];
//
//                        modelSecond = (DefaultTableModel)defTable.getModel();
//
//                        for (int i = 0; i < indexs.length; i++) {
//                            //  countAddGoods++;
//                            row[0] = modelFirst.getValueAt(indexs[i], 0);
//                            // row[0] = countAddGoods;
//                            row[1] = modelFirst.getValueAt(indexs[i], 1);
//                            row[2] = Integer.parseInt(countLabel.getText());
//                            row[3] = modelFirst.getValueAt(indexs[i], 3);
//                            row[4] = Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3]));
//
////                        String item = String.valueOf(row[0]) + "   " + String.valueOf(row[1]) + "   " + String.valueOf(row[2])
////                                + "   " + String.valueOf(row[3]) + "   "  + String.valueOf(row[4]);
//                            if (countLabel.getText() != "0") {
//                                modelSecond.addRow(row);
//                            }
//
//                        }
//                        countLabel.setText("0");
//                    }});

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

//                btnAdd.addActionListener(new ActionListener() {
//                    public void actionPerformed(ActionEvent e) {
//
//                        TableModel modelFirst = bookTable1.getModel();
//                        int[] indexs = bookTable1.getSelectedRows();
//
//                        Object[] row = new Object[5];
//
//                        modelSecond = (DefaultTableModel)defTable.getModel();
//
//                        for (int i = 0; i < indexs.length; i++) {
//                            //     countAddGoods++;
//                            //       row[0] = countAddGoods;
//                            row[0] = modelFirst.getValueAt(indexs[i], 0);
//                            row[1] = modelFirst.getValueAt(indexs[i], 1);
//                            row[2] = Integer.parseInt(countLabel.getText());
//                            row[3] = modelFirst.getValueAt(indexs[i], 3);
//                            row[4] = Integer.parseInt(countLabel.getText()) * Integer.parseInt(String.valueOf(row[3]));
//
////                            String item = String.valueOf(row[0]) + "   " + String.valueOf(row[1]) + "   " + String.valueOf(row[2])
////                                    + "   " + String.valueOf(row[3]) + "   "  + String.valueOf(row[4]);
//                            if (countLabel.getText() != "0") {
//                                modelSecond.addRow(row);
//                            }
//                        }
//                        countLabel.setText("0");
//                    }});

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

//        // Правая панель с вкладками
       JTabbedPane tabsRight = new JTabbedPane(JTabbedPane.TOP);
//        // Создание вкладок
//        for (int i = 1; i < colors.length; i++) {
//            // Создание и подкрашивание панели
//            JPanel panel = new JPanel();
//            panel.setBackground(colors[colors.length - i]);
//            // Создание кнопки в панели
//            panel.add(new JButton(String.format(TEMPL_button, i)));
//            // Добавление панели во вкладку
//            tabsRight.addTab("<html><i>Вкладка №" + i,
//                    new ImageIcon("images/copy.png"), panel, "Нажмите " + i);
//        }


        // Определение табличного расположения компонентов
        getContentPane().setLayout(new GridLayout());
        // Добавление вкладок в панель содержимого
        getContentPane().add(tabsLeft);
        getContentPane().add(tabsRight);
        // Вывод окна на экран
        setSize(600, 250);
        setVisible(true);
    }

}
