package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.Actor;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * This are all the methods needed to get data for Event objects.
 *
 * Created by seanmorris on 3/22/15.
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
                e.setOpenDate(rs.getDate("open_date"));
                e.setCloseDate(rs.getDate("close_date"));
                events.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Get an event by id and return a fully populated event object that includes
     * the list of actors in the event.
     *
     * @param eId the event id to query for
     * @return Event a fully populated event object
     */
    public Event getEventFull(int eId) {
        Event e = new Event();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM event WHERE id = ?");
            stmt.setInt(1, eId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                e.setId(rs.getInt("id"));
                e.setName(rs.getString("name"));
                e.setOpenDate(rs.getDate("open_date"));
                e.setCloseDate(rs.getDate("close_date"));
                e.setJoinAllowAuto(rs.getBoolean("join_allow_auto"));
                e.setJoinAllowByAccept(rs.getBoolean("join_allow_by_accept"));
                e.setJoinInvitation(rs.getBoolean("join_invitation"));
            }

            stmt = conn.prepareStatement("SELECT actor.id, first, last, contact_method, is_subscriber FROM actor, actor_event_mapping aem WHERE aem.event_id=? AND aem.actor_id = actor.id");
            stmt.setInt(1, eId);
            rs = stmt.executeQuery();
            ArrayList<Actor> actors = new ArrayList<Actor>();
            while (rs.next()) {
                Actor a = new Actor();
                a.setId(rs.getInt("id"));
                a.setFirst(rs.getString("first"));
                a.setLast(rs.getString("last"));
                a.setContactMethod(rs.getString("contact_method"));
                a.setIsSubscriber(rs.getBoolean("is_subscriber"));
                actors.add(a);
            }
            e.setUsers(actors);

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return e;
    }

    public ArrayList<Event> getEventsForActor(int aId) {
        ArrayList<Event> events = new ArrayList<Event>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT event.id as eid, * FROM event, actor_event_mapping aem WHERE aem.actor_id=? AND aem.event_id = event.id");
            stmt.setInt(1, aId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("name"));
                e.setOpenDate(rs.getDate("open_date"));
                e.setCloseDate(rs.getDate("close_date"));
                events.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

}