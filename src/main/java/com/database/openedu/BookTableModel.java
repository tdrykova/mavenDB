package com.database.openedu;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookTableModel extends AbstractTableModel {
    // TODO обновление данных
    // TODO удаление
   // private static final long serialVersion = 2134212342322L;

    private int columnCount = 4;
    private ArrayList<String []> dataArrayList; // массив строк

    public BookTableModel() {
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
//
//    public void removeAll(int row) {
//        dataArrayList.remove(row);
//        fireTableRowsDeleted(row,row);
//    }

    public void removeAll(){
        int size = dataArrayList.size();
        dataArrayList.clear();
        fireTableRowsDeleted(0, size);
    }

    public void deleteData(String []row) {
        String []rowTable = new String[getColumnCount()];
        rowTable = row;
        dataArrayList.remove(rowTable);
    }


    public void deleteData(int row) {
        String []rowTable = new String[getColumnCount()];
        dataArrayList.remove(row);
    }

    public void addDataUsers(ConnectionDb connect) {
        ResultSet result = connect.resultSetQuery("SELECT * FROM users");

            try {
                while (result.next()) {
                    String id = result.getString("id");
                    String name = result.getString("name");
                    String age = result.getString("age");
                    String email = result.getString("email");

                    String []row = {id, name, age, email};

                    addData(row);
                }
                // do smth else
                result.close();
            } catch (SQLException e) {
                System.out.println("add data isn't worked");
            }
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
            // do smth else
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
            // do smth else
            result.close();
        } catch (SQLException e) {
            System.out.println("add data isn't worked");
        }
    }
}
