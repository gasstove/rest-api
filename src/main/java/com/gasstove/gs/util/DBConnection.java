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
        return this.getConnection(Configuration.dbConnect);
    }

    public Connection getConnection(String dbfile) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
