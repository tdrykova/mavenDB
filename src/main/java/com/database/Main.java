package com.database;

import java.sql.*;

public class Main {

    public static void main(String[] args) {

        DBConnection connect = new DBConnection();

      //  String query = "SELECT * FROM usersnew";
        try {
            Statement statement = connect.getConnection().createStatement();
            //statement.executeQuery("SELECT * FROM usersnew");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM usersnew");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user);
//                int id = resultSet.getInt(1);
//                System.out.println(id); выводит колво запесей
            }

        } catch (SQLException e) {
            System.out.println("Connection is lost");
        }


        //lesson 5
        //Connection connection;

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

       // try (Connection connection1 = DriverManager.getConnection(URL, USERNAME, PASSWORD); Statement statement = connection1.createStatement()) {
            // System.out.println(connection1.isClosed());
            //statement.execute("INSERT INTO users(name, age, email) VALUES ('name', 20, 'email')");
            //-------------------------------------------
//            statement.execute("INSERT INTO users(name, age, email) VALUES ('name', 20, 'email')");
//            int res = statement.executeUpdate("UPDATE users SET name = 'Steve', age = 55 where id = 3");
//            System.out.println(res);
            //-------------------------------------------
//            ResultSet res2 = statement.executeQuery("SELECT * FROM users");
//            System.out.println(res2);
            //-------------------------------------------
//            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('name', 21, 'email')");
//            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('name', 22, 'email')");
//            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('name', 23, 'email')");
           // statement.executeBatch();
// -----------------------------
         //  statement.clearBatch();

//            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('name', 24, 'email')");
//            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('name', 25, 'email')");
//            statement.addBatch("INSERT INTO users(name, age, email) VALUES ('name', 26, 'email')");
//            statement.executeBatch();
// -----------------------------
           // System.out.println(statement.isClosed());
// -----------------------------
//            statement.getConnection();
//           // statement.close();
//
//        } catch (SQLException e) {
//            System.out.println("Request is failed");
//        }
    }
}

