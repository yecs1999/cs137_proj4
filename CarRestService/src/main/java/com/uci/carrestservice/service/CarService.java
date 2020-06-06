package com.uci.carrestservice.service;

import com.uci.carrestservice.db.DatabaseConnector;
import com.uci.carrestservice.db.DatabaseUtils;
import com.uci.carrestservice.model.Order;
import com.uci.carrestservice.model.Car;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarService {


    private final static String CAR_INFO_BY_PID = "SELECT * FROM cardata "
            + "left join carimages on carimages.pid = cardata.pid where cardata.pid = ?";
    private final static String CAR_ROW = "Select * from"
                            + " cardata left join carimages on carimages.pid = cardata.pid "
                            + "where category = ? limit 1 offset ?";

    public static Car getCarById(String id) {
        //Get a new connection object before going forward with the JDBC invocation.
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveCarByPid(connection, CAR_INFO_BY_PID, id);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Car car = new Car(resultSet.getString("main_img"),
                            resultSet.getString("sub_img"),
                            resultSet.getString("int_img"),
                            resultSet.getString("category"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getString("trim"),
                            resultSet.getString("color"),
                            resultSet.getString("year"),
                            resultSet.getString("odo"),
                            resultSet.getString("gearbox"),
                            resultSet.getString("engine"),
                            resultSet.getString("price"),
                            resultSet.getString("location"),
                            resultSet.getString("description")
                    );
                    return car;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {

                    // We will always close the connection once we are done interacting with the Database.
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
    public static Car getCarByRowAndCat(String category, int row) {
        Connection connection = DatabaseConnector.getConnection();
        ResultSet resultSet = DatabaseUtils.retrieveCarRowByCat(connection, CAR_ROW, category, row);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Car car = new Car(resultSet.getString("main_img"),
                            resultSet.getString("sub_img"),
                            resultSet.getString("int_img"),
                            resultSet.getString("category"),
                            resultSet.getString("make"),
                            resultSet.getString("model"),
                            resultSet.getString("trim"),
                            resultSet.getString("color"),
                            resultSet.getString("year"),
                            resultSet.getString("odo"),
                            resultSet.getString("gearbox"),
                            resultSet.getString("engine"),
                            resultSet.getString("price"),
                            resultSet.getString("location"),
                            resultSet.getString("description")
                    );
                    return car;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean AddOrder(Order order) {

        String sql = "INSERT INTO orders(model, fullname, phone, email, method, country, address, card, cvv)" + 
                     "VALUES (?,?,?,?,?,?,?,?,?)";
        Connection connection = DatabaseConnector.getConnection();
        return DatabaseUtils.insertIntoOrders(connection, sql, 
                order.getModel(), 
                order.getFullName(),
                order.getPhone(),
                order.getEmail(),
                order.getMethod(),
                order.getCountry(),
                order.getFullAddress(),
                order.getCard(),
                order.getCVV()
                );

    }

}
