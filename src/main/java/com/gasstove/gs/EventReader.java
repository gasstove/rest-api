package com.gasstove.gs;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by seanmorris on 3/22/15.
 */
public class EventReader {

    Statement stmt = null;
    ResultSet resultSet = null;

    public ArrayList<Event> getEvents(){
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();
        ArrayList<Event> events = new ArrayList<Event>();
        String sql = "Select * FROM event";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);


            while(rs.next()){
                Event e = new Event();
                e.setName(rs.getString("name"));
                events.add(e);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }
}
