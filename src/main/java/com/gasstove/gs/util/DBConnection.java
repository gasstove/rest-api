package com.gasstove.gs.util;

import java.sql.*;

/**
 * Created by seanmorris on 3/22/15.
 */
public class DBConnection {

    String dbConnect = "jdbc:sqlite:src/main/resources/gasstove.db";

    Connection connection = null;

    /**
     * Establish connection to db
     *
     * @return Connection database connection
     */
    public Connection getConnection() throws SQLException {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbConnect);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
