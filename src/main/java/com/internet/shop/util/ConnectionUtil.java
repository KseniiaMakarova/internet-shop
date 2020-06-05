package com.internet.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String USER = "b380e413ca3b54";
    private static final String PASSWORD = "f1137036";
    private static final String URL = "jdbc:mysql://eu-cdbr-west-03.cleardb.net:3306/"
                    + "heroku_2a0596eefac98c7?serverTimezone=UTC";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Can not find MySQL Driver", e);
        }
    }

    private ConnectionUtil() {

    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("Can not establish connection to DB", e);
        }
    }
}
