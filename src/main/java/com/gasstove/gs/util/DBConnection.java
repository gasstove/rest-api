package com.gasstove.gs.util;

import java.sql.*;

/**
 * Created by seanmorris on 3/22/15.
 */
public class DBConnection {


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
            connection = DriverManager.getConnection(Configuration.dbConnect);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return connection;
    }
}
