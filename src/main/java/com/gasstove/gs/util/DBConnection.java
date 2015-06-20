package com.gasstove.gs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Establish connection to db
 */
public class DBConnection {

    Connection connection = null;

    public Connection getConnection() throws SQLException {
        return this.getConnection(Configuration.getDB());
    }

    public Connection getConnection(String db) throws SQLException {
        try {
            Class.forName(Configuration.getDriverClassPath());
            connection = DriverManager.getConnection(db);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
