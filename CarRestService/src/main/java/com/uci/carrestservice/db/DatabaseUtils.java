package com.uci.carrestservice.db;

import java.sql.*;


public class DatabaseUtils {


    public static ResultSet retrieveQueryNoParams(Connection connection, final String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            return resultSet;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public static ResultSet retrieveCarByPid(Connection connection, final String sql, String param) {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,  param);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;


    }
    public static ResultSet retrieveCarRowByCat(Connection connection, final String sql, String category, int row) {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            
            statement.setString(1, category);
            statement.setInt(2, row);
            
            ResultSet resultSet = statement.executeQuery();
            return resultSet;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;


    }

    public static boolean insertIntoOrders(Connection connection, String sql, String... params) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);


            int i = 1;
            for (String param : params) {
                System.out.println(param+"\n");
                preparedStatement.setString(i++, param);

            }

            return preparedStatement.executeUpdate() > 0 ;

        } catch (SQLException e) {
            System.out.println("SQL Error here?? " + e.toString());
            return false;
        }
    }
}
