package com.lucianbc.onechat.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SuppressWarnings("WeakerAccess")
class BaseDbDao {
    static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    static {
        System.out.println("Loading db driver");
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/pao";

    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    protected Connection connection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD);
    }
}
