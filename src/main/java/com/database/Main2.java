package com.database;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Calendar;

public class Main2 {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "rroot";
    private static final String PASSWORD = "rroot";

    private static final String INSERT_NEW = "INSERT INTO users VALUES (?, ?, ?, ?)";
    private static final String GET_ALL = "SELECT * FROM users";
   // private static final String DELETE = "SELECT * FROM users WHERE id = ?";
    private static final String DELETE = "DELETE FROM users WHERE id = ?";

    public static void main(String[] args) {

        Connection connection;
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;

        try {

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // ------------------------------------
//            preparedStatement = connection.prepareStatement(INSERT_NEW);
//            preparedStatement.setInt(1,1);
//            preparedStatement.setString(2,"Name");
//            preparedStatement.setInt(3, 33);
//            preparedStatement.setString(4,"wfew");
//            preparedStatement.execute();

            //preparedStatement.setDate(4, new Date(Calendar.getInstance().getTimeInMillis()));
           // preparedStatement.setBlob(4, new FileInputStream("image.png"));

            //--------------------------------
            // ПУНКТ 2
            preparedStatement2 = connection.prepareStatement(GET_ALL);
            ResultSet res2 = preparedStatement2.executeQuery();

            while (res2.next()) {
                int id = res2.getInt("id");
                String name = res2.getString("name");
                int age = res2.getInt("age");
                String email = res2.getString("email");

                System.out.println("id: " + id + ", name: " + name + "," +
                        " age: " + age + ", email: " + email);
            }
            //----------------------------------------------------------
            // УДАЛЕНИЕ 1 ой строки из ПУНКТА 2
            preparedStatement3 = connection.prepareStatement(DELETE);
            preparedStatement3.setInt(1, 1); // id: 1, name: Name, age: 33, email: wfew
            preparedStatement3.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Не удалось загрузить драйвер");
        }

    }
}

