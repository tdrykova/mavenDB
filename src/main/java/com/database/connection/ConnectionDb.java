package com.database.connection;

import java.sql.*;

public class ConnectionDb {
    private String url = "jdbc:mysql://localhost:3306/mydbtest";
    private String username = "rroot";
    private String password = "rroot";
    private String host;
    private String dataDb;
    private String root;

   // private Properties properties = new Properties();
    private Connection mySqlConnection;

    public ConnectionDb() {
        try {
            mySqlConnection = DriverManager.getConnection(url, username, password);
//            if (!mySqlConnection.isClosed()) {
//                System.out.println("Соединение с БД установлено");
//            }
//            mySqlConnection.close();
//            if (mySqlConnection.isClosed()) {
//                System.out.println("Соединение с БД закрыто");
//            }
        } catch (SQLException e) {
            System.out.println("Не удалось загрузить драйвер");
        }

    }

    public Connection getMySqlConnection() {
        return mySqlConnection;
    }

    public void setMySqlConnection(Connection mySqlConnection) {
        this.mySqlConnection = mySqlConnection;
    }

    public Connection getConnection() {
        try {
            mySqlConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mySqlConnection;
    }

    //    public ConnectionDb(String password, String host, String root) {
//        this.password = password;
//        this.host = host;
//        this.root = root;
//    }
//
//    public ConnectionDb(String url, Properties properties) {
//        this.url = url;
//        this.properties = properties;
//    }

    public void setNameDataDb(String dataDb) {
        this.dataDb = dataDb;
    }

//    public void initProperties() {
//        this.url = "jdbc:mysql://" + this.host + "/" + this.dataDb;
//
//        properties.setProperty("user", this.root);
//        properties.setProperty("password", this.password);
//        properties.setProperty("useUnicode", "true");
//        properties.setProperty("characterEncoding", "UTF-8");
//
//        System.out.println(url);
//    }

//    public Connection getConnection() {
//        return connection;
//    }

    public void sqlQuery(String query) {
        try {
            Statement statement = mySqlConnection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Query is failed");
        }
    }

    public void finish() {
        try {
            mySqlConnection.close();
        } catch (SQLException e) {
            System.out.println("Connection isn't closed");
        }
    }

    public ResultSet resultSetQuery(String query) {
        try {
           Statement statement = mySqlConnection.createStatement();
           ResultSet res = statement.executeQuery(query);
           return res;
        } catch (SQLException e) {
            System.out.println("ResultSet isn't returned");
        }
        return null;
    }
}
