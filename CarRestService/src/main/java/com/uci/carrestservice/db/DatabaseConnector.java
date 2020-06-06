package com.uci.carrestservice.db;

import java.sql.*;


public class DatabaseConnector {


    private DatabaseConnector() {

    }

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Original
            // Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cars?useSSL=false", 
                    "root", "1234");
            
            // Original
            // return DriverManager.getConnection("jdbc:mariadb://192.168.0.209/db4lecture",
            //        "root", "mytest");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }


}
