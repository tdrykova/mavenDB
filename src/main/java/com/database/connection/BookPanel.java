package com.database.connection;

import javax.swing.*;

public class BookPanel extends JPanel {

    private ConnectionDb connect;
    private GoodsTableModel goodsTableModel = new GoodsTableModel();
    private JTable bookTable = new JTable(goodsTableModel);

    private JButton addButton = new JButton("Add");
    private JButton deleteButton = new JButton("Delete");
    private JButton clearButton = new JButton("Clear");

    public BookPanel(ConnectionDb connect) {
        this.connect = connect;
    }

    public void init() {

    }
}
