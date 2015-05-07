package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.*;
import com.gasstove.gs.util.Time;

import java.sql.*;
import java.util.ArrayList;

/**
 * Database reader for Events
 */
public class EventReader {

    private Connection conn;

    public EventReader() {
        try {
            conn = (new DBConnection()).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public EventReader(Connection conn) {
        this.conn = conn;
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of all the events in the db.
     * Provides for each event: id, name
     *
     * @return ArrayList<Event> a list of event objects
     */
    public ArrayList<Event> getEventsBasicInfo() {
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM event");
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setOpenDate(new Time(rs.getInt("open_date")));
                e.setCloseDate(new Time(rs.getInt("close_date")));
                e.setJoinAllowAuto(rs.getBoolean("join_allow_auto"));
                e.setJoinAllowByAccept(rs.getBoolean("join_allow_by_accept"));
                e.setJoinInvitation(rs.getBoolean("join_invitation"));
                events.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     *
     * @param eId the event id to query for
     * @return Event a fully populated event object
     */
    public Event getEventBasicInfo(int eId) {
        Event e = new Event();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM event WHERE id = ?");
            stmt.setInt(1, eId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setOpenDate(new Time(rs.getInt("open_date")));
                e.setCloseDate(new Time(rs.getInt("close_date")));
                e.setJoinAllowAuto(rs.getBoolean("join_allow_auto"));
                e.setJoinAllowByAccept(rs.getBoolean("join_allow_by_accept"));
                e.setJoinInvitation(rs.getBoolean("join_invitation"));
            }
//            UserReader ar = new UserReader(conn);
//            e.setUsers(ar.getUsersForEvent(eId));
//
//            MediaReader mr = new MediaReader(conn);
//            e.setMedias(mr.getMediaForEvent(eId));

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return e;
    }

    public ArrayList<Event> getEventsForUser(int uId) {
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT event.id as eid, * FROM event, user_event_mapping aem WHERE aem.user_id=? AND aem.event_id = event.id");
            stmt.setInt(1, uId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("name"));
                e.setOpenDate(new Time(rs.getInt("open_date")));
                e.setCloseDate(new Time(rs.getInt("close_date")));
                events.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

}