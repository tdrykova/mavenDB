package com.database;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookTableModel extends AbstractTableModel {
    // TODO обновление данных
    // TODO удаление
    private static final long serialVersion = 2134212342322L;

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
            case 2: return "age";
            case 3: return "email";
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

    public void addData(ConnectionDb connect) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
}
