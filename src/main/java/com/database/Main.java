package com.database;

import java.sql.*;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "rroot";
    private static final String PASSWORD = "rroot";

    public static void main(String[] args) {
        Connection connection;

        // try {
        // lesson 4
        // Driver driver = new FabricMySQLDriver();
        // DriverManager.registerDriver(driver);

//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            if (!connection.isClosed()) {
//                System.out.println("Соединение с БД установлено");
//            }
//            connection.close();
//            if (connection.isClosed()) {
//                System.out.println("Соединение с БД закрыто");
//            }
//        } catch (SQLException e) {
//            System.out.println("Не удалось загрузить драйвер");
//        }

        try (Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD); Statement statement = connection1.createStatement()) {
            // System.out.println(connection1.isClosed());
            statement.execute("INSERT INTO users(name, age, email) VALUES ('name', 20, 'email')");
        } catch (SQLException e) {
            System.out.println("Request is failed");
        }
    }
}

