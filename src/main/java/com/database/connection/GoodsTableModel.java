package com.database.connection;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GoodsTableModel extends AbstractTableModel {

    private int columnCount = 4;
    private ArrayList<String []> dataArrayList; // массив строк таблицы

    public GoodsTableModel() {
        dataArrayList = new ArrayList<String []>();
        for (int i = 0; i < dataArrayList.size(); i++) {
            dataArrayList.add(new String[getColumnCount()]);
        }
    }

    @Override
    public int getRowCount() {
        return dataArrayList.size(); // сколько данных в коллекции ArrayList
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0: return "id";
            case 1: return "name";
            case 2: return "count";
            case 3: return "price";
        }
        return "";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String []rows = dataArrayList.get(rowIndex); // строка
        return rows[columnIndex];
    }

    // добавление строки = один массив(мн-во столбцов) - один элемент таблицы
    public void addData(String []row) {
        String []rowTable = new String[getColumnCount()];
        rowTable = row;
        dataArrayList.add(rowTable);
    }

    public void removeAll(){
        int size = dataArrayList.size();
        dataArrayList.clear();
        fireTableRowsDeleted(0, size);
    }

    public void addDataSmartPhones(ConnectionDb connect) {
        ResultSet result = connect.resultSetQuery("SELECT * FROM smartphones");

        try {
            while (result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");
                String count = result.getString("count");
                String email = result.getString("price");

                String []row = {id, name, count, email};
                addData(row);
            }
            result.close();
        } catch (SQLException e) {
            System.out.println("add data isn't worked");
        }
    }

    public void addDataComputers(ConnectionDb connect) {
        ResultSet result = connect.resultSetQuery("SELECT * FROM computers");

        try {
            while (result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");
                String count = result.getString("count");
                String price = result.getString("price");

                String []row = {id, name, count, price};
                addData(row);
            }
            result.close();
        } catch (SQLException e) {
            System.out.println("add data isn't worked");
        }
    }

    public void addDataTv(ConnectionDb connect) {
        ResultSet result = connect.resultSetQuery("SELECT * FROM tv");

        try {
            while (result.next()) {
                String id = result.getString("id");
                String name = result.getString("name");
                String count = result.getString("count");
                String email = result.getString("price");

                String []row = {id, name, count, email};
                addData(row);
            }
            result.close();
        } catch (SQLException e) {
            System.out.println("add data isn't worked");
        }
    }
}
