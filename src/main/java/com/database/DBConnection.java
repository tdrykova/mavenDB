package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final String USERNAME = "rroot";
    private static final String PASSWORD = "rroot";

    private Connection connection = null;

    public DBConnection() {
       // Connection connection;

         try {
             // lesson 4
             // Driver driver = new FabricMySQLDriver();
             // DriverManager.registerDriver(driver);

           connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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

         } catch (SQLException e) {
             System.out.println("Не удалось загрузить драйвер");
         }

    }

    public Connection getConnection() {
        return connection;
    }
}
