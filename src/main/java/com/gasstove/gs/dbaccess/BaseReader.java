package com.gasstove.gs.dbaccess;

import com.gasstove.gs.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gomes on 5/21/15.
 */
public class BaseReader {

    protected Connection conn;

    public BaseReader() {
        try {
            conn = (new DBConnection()).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BaseReader(Connection conn) {
        this.conn = conn;
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
