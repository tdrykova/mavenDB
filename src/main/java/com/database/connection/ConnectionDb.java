package com.database.connection;

import java.sql.*;

public class ConnectionDb {
    private final String url = "jdbc:mysql://localhost:3306/mydbtest";
    private final String username = "rroot";
    private final String password = "rroot";

    private Connection mySqlConnection;

    public ConnectionDb() {
        try {
            mySqlConnection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println("Failed to load driver");
        }
    }

    public Connection getConnection() {
        try {
            mySqlConnection = DriverManager.getConnection(url, username, password);
            if (!mySqlConnection.isClosed()) {
                System.out.println("Connection with db is established");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mySqlConnection;
    }

    public void finish() {
        try {
            mySqlConnection.close();
            System.out.println("Connection is closed");
        } catch (SQLException e) {
            System.out.println("Connection isn't closed");
        }
    }

    public ResultSet resultSetQuery(String query) {
        try {
           Statement statement = mySqlConnection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("ResultSet isn't returned");
        }
        return null;
    }
}
