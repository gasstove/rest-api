package com.gasstove.gs;

import java.sql.*;

/**
 * This is the script to populate the test database
 */
public class TestData {

    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;

    public static void main(String[] args){
        TestData t = new TestData();
        Connection c = t.getCommection();

    }
    public Connection getCommection(){
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/smorris/Development/db/gasstove.db");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return connection;

    }
    public void addEvents(){
        try {
            statement = connection.createStatement();
            resultSet = statement
                    .executeQuery("INSERT ");
        }catch(SQLException e){

        }

    }
}
