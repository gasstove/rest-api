package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventWriter extends BaseWriter {

    public EventWriter(Connection dbConn) {
        super(dbConn);
    }

    public int insert(DBObject object) throws Exception {

        Event event = (Event) object;

        System.out.println("EVENT INSERT");
        System.out.println(event);

        String sql = "INSERT into event( name , " +
                                        "open_date," +
                                        "close_date," +
                                        "join_invitation," +
                                        "join_allow_by_accept," +
                                        "join_allow_auto) " +
                                        "VALUES(?,?,?,?,?,?)";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        int i=1;
        statement.setString(    i++ , event.getName());
        statement.setDate(      i++ , event.getOpenDate().toSqlDate() );
        statement.setDate(      i++ , event.getCloseDate().toSqlDate() );
        statement.setBoolean(   i++ , event.isJoinInvitation());
        statement.setBoolean(   i++ , event.isJoinAllowByAccept());
        statement.setBoolean(   i++ , event.isJoinAllowAuto());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Insert failed for event " + + event.getId());

        // return new id (IS THIS CORRECT?)
        ResultSet rs = dbConn.createStatement().executeQuery("SELECT Max(id) from event");
        return rs.next() ? rs.getInt(1) : -1;
    }

    public int update(DBObject object) throws Exception{
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
        int i=1;
        statement.setString(    i++ , event.getName());
        statement.setDate(      i++ , event.getOpenDate().toSqlDate());
        statement.setDate(      i++ , event.getCloseDate().toSqlDate());
        statement.setBoolean(   i++ , event.isJoinInvitation());
        statement.setBoolean(   i++ , event.isJoinAllowByAccept());
        statement.setBoolean(   i++ , event.isJoinAllowAuto());
        statement.setInt(       i++ , event.getId());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Update failed for event " + + event.getId());

        return event.getId();
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE from event WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1,id);
        int r = statement.executeUpdate();
        return r==1;
    }

}
