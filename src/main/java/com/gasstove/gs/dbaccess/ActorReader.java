package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.Actor;
import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * These are all the methods needed to get data for Actors objects.
 *
 * Created by seanmorris on 3/22/15.
 */
public class ActorReader {

    private Connection conn;

    public ActorReader() {
        try {
            conn = (new DBConnection()).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ActorReader(Connection conn) {
        this.conn = conn;
    }

    /**
     * Returns a list of all the actors in the db.
     * Provides for each actor: id, first, last
     *
     * @return ArrayList<Actors> a list of actor objects
     */
    public ArrayList<Actor> getActorsBasicInfo() {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM actor");
            while (rs.next()) {
                Actor a = new Actor();
                a.setId(rs.getInt("id"));
                a.setFirst(rs.getString("first"));
                a.setLast(rs.getString("last"));
                actors.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return actors;
    }

    /**
     * Get an an by id and return a fully populated actror object that includes
     * the list of events the actor is in
     *
     * @param aId the actor id to query for
     * @return Actor a fully populated actor object
     */
    public Actor getActorFull(int aId) {
        Actor a = new Actor();
        try {

            // query for actor
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM actor where id = ?");
            stmt.setInt(1, aId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                a.setId(rs.getInt("id"));
                a.setFirst(rs.getString("first"));
                a.setLast(rs.getString("last"));
                a.setContactMethod(rs.getString("contact_method"));
                a.setIsSubscriber(rs.getBoolean("is_subscriber"));
            }

            // use EventReader to query events
            EventReader er = new EventReader(conn);
            a.setEvents(er.getEventsForActor(a.getId()));

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return a;
    }

    public String getActorNameWithId(int aId) {
        String name = "";
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT first,last FROM actor WHERE id = ?");
            stmt.setInt(1, aId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                name = rs.getString("first") + " " + rs.getString("last");
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return name;
    }

    /**
     * Get an actor by id and event id. The Actor will be populated with event and
     * the events media for the actor
     *
     * @param aId the actor id to query for
     * @return Actor a fully populated actor object
     */
//    public Actor getActorEventMedia(int aId, int eId){
//        String sqlA = "Select * FROM actor where id = ?";
//        String sqlAE = "Select event.id as eid, * FROM event, actor_event_mapping aem WHERE aem.actor_id=? and aem.event_id = ?";
//        String sqlEM = "Select media.id as mid, * FROM media, " +
//                                "media_mapping mmm," +
//                                "actor_event_mapping aem " +
//                                "WHERE aem.id=mm.actor.event_mapping_id and mm.media_id = media.id";
//        Actor a = new Actor();
//        try {
//            Connection conn = db.getConnection();
//            PreparedStatement stmt = conn.prepareStatement(sqlA);
//            stmt.setInt(1,aId);
//
//            ResultSet rs = stmt.executeQuery();
//            if(rs.next()){
//                a.setId(rs.getInt("id"));
//                a.setFirst(rs.getString("first"));
//                a.setLast(rs.getString("last"));
//                a.setContactMethod(rs.getString("contact_method"));
//                a.setIsSubscriber(rs.getBoolean("is_subscriber"));
//
//            }
//
//            stmt = conn.prepareStatement(sqlA);
//            stmt.setInt(1,aId);
//            rs = stmt.executeQuery();
//            ArrayList<Event> events = new ArrayList<Event>();
//            while(rs.next()) {
//                Event e = new Event();
//                e.setId(rs.getInt("eid"));
//                e.setName(rs.getString("name"));
//                e.setOpenDate(rs.getDate("open_date"));
//                e.setCloseDate(rs.getDate("close_date"));
//                e.setJoinAllowAuto(rs.getBoolean("join_allow_auto"));
//                e.setJoinAllowByAccept(rs.getBoolean("join_allow_by_accept"));
//                e.setJoinInvitation(rs.getBoolean("join_invitation"));
//                events.add(e);
//            }
//            a.setEvents(events);
//
//        } catch (SQLException sq) {
//            sq.printStackTrace();
//        }
//        return a;
//    }

}
