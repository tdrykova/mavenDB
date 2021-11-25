package com.database.openedu;

import javax.swing.*;

public class BookPanel extends JPanel {

    private ConnectionDb connect;
    private BookTableModel bookTableModel = new BookTableModel();
    private JTable bookTable = new JTable(bookTableModel);

    private JButton addButton = new JButton("Add");
    private JButton deleteButton = new JButton("Delete");
    private JButton clearButton = new JButton("Clear");

    public BookPanel(ConnectionDb connect) {
        this.connect = connect;
    }

    public void init() {

    }
}
