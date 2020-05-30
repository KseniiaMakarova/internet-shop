package com.internet.shop.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
    private static final String USER = "root";
    private static final String PASSWORD = "********";
    private static final String URL =
            "jdbc:mysql://localhost:3306/internet_shop?serverTimezone=UTC";

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
