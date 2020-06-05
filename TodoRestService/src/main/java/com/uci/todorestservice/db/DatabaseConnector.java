package com.uci.todorestservice.db;

import java.sql.*;


public class DatabaseConnector {


    private DatabaseConnector() {

    }

    public static Connection getConnection() {

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection("jdbc:mariadb://192.168.0.209/db4lecture",
                    "root", "mytest");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
