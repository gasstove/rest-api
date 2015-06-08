package com.gasstove.gs.dbaccess;

import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by gomes on 5/21/15.
 */
public class BaseReader {

    protected Connection conn;

    public BaseReader(String dbfile){
        try {
            conn = (new DBConnection()).getConnection(dbfile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BaseReader() {
        this(Configuration.dbConnect);
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
