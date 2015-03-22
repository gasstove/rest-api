package com.gasstove.gs.util;

import java.sql.*;

/**
 * Created by seanmorris on 3/22/15.
 */
public class DBConnection {

    //set these values to manipulate test records
    final int NUM_EVENTS_USER = 5;
    final int NUM_MEDIA_PER_EVENT_PER_USER = 5;
    final int RANDOM_YEAR_START = 2014;
    final int RANDOM_YEAR_END = 2016;
    final int NUM_ACTORS = 10;


    String dbConnect = "jdbc:sqlite:/Users/seanmorris/Development/db/gasstove.db";

    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement statement = null;
    Statement stmt = null;


    /**
     * Establish connection to db
     *
     * @return Connection database connection
     */
    public Connection getConnection(){
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(dbConnect);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return connection;

    }
}
