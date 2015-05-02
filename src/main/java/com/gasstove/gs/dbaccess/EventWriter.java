package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.Event;
import org.omg.CORBA_2_3.portable.OutputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class EventWriter extends WriterBase {

    public EventWriter(Connection dbConn) {
        super(dbConn);
    }

    public int insert(DBObject object) throws Exception {

        Event event = (Event) object;

        String sql = "INSERT into event(id,name,open_date,close_date,join_invitation,join_allow_by_accept,join_allow_auto) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1, event.getId());
        statement.setString(2, event.getName());
        statement.setDate(3, new java.sql.Date(event.getOpenDate().getTime()));
        statement.setDate(4, new java.sql.Date(event.getCloseDate().getTime()));
        statement.setBoolean(5, false);     // join_invitation
        statement.setBoolean(6, false);     // join_allow_by_accept
        statement.setBoolean(7, false);     // join_allow_auto
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Insert failed for event " + + event.getId());

        // return new id (IS THIS CORRECT?)
        ResultSet rs = dbConn.createStatement().executeQuery("SELECT Max(id) from event");
        return rs.next() ? rs.getInt(1) : -1;
    }

    public void update(DBObject object) throws Exception{
        Event event = (Event) object;

        String sql = "UPDATE event SET " +
                            "name=?," +
                            "open_date=?," +
                            "close_date=?," +
                            "join_invitation=?," +
                            "join_allow_by_accept=?," +
                            "join_allow_auto=? " +
                            "WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setString(1,event.getName());
        statement.setDate(2,new java.sql.Date(event.getOpenDate().getTime()));
        statement.setDate(3,new java.sql.Date(event.getCloseDate().getTime()));
        statement.setBoolean(4,false);     // join_invitation
        statement.setBoolean(5,false);     // join_allow_by_accept
        statement.setBoolean(6,false);     // join_allow_auto
        statement.setInt(7,event.getId());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Update failed for event " + + event.getId());
    }

    public void delete(DBObject object) throws Exception {
        String sql = "DELETE from event WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1, ((Event) object).getId());
        statement.executeUpdate();
    }

}
