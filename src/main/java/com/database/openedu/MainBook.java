package com.database.openedu;

import com.database.DBConnection;
import com.database.openedu.BookTableModel;
import com.database.openedu.ConnectionDb;

import javax.swing.*;
import java.awt.*;

public class MainBook {

    public static void main(String[] args) {

        ConnectionDb connect = new ConnectionDb();
        connect.setNameDataDb("Users");

        JFrame frame = new JFrame("FRAME");
        frame.setSize(new Dimension(600,400));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");


        BookTableModel bookTableModel = new BookTableModel();
        JTable bookTable = new JTable(bookTableModel);
        JScrollPane bookTableScrollPage = new JScrollPane(bookTable); // что прокрутить
        bookTableScrollPage.setPreferredSize(new Dimension(400,400)); // размер табл

        bookTableModel.addData(connect);

//        String[]str = new String[4];
//        str[0] = "0";
//        str[1] = "name of book";
//        str[2] = "678";
//        str[3] = "description of book";
//
//        for (int i = 0; i < 10; i++)
//            bookTableModel.addData(str);
        //bookTableModel.getValueAt(1,2);

        // лучше добавлять на панель
        frame.add(bookTableScrollPage, new GridBagConstraints(0, 0, 3, 1, 1,1,
                GridBagConstraints.NORTH, GridBagConstraints.BOTH,
                new Insets(1, 1, 1, 1), 0, 0));

        frame.add(addButton, new GridBagConstraints(0, 1, 1, 1, 1,1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(1, 1, 1, 1), 0, 0));

        frame.add(deleteButton, new GridBagConstraints(1, 1, 1, 1, 1,1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(1, 1, 1, 1), 0, 0));

        frame.add(clearButton, new GridBagConstraints(2, 1, 1, 1, 1,1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(1, 1, 1, 1), 0, 0));


        frame.setVisible(true);
        frame.pack();

    }
}
