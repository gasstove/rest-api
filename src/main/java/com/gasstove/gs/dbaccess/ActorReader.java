package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.Actor;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * These are all the methods needed to get data for Actors objects.
 *
 * Created by seanmorris on 3/22/15.
 */
public class ActorReader {

    Statement stmt = null;

    /**
     * This returns a list of all the actors in the db. Eventually it will need to be filtered
     *
     * @return ArrayList<Actors> a list of actor objects
     */
    public ArrayList<Actor> getActors(){
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        String sql = "Select * FROM actor";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Actor a = new Actor();
                a.setId(rs.getInt("id"));
                a.setFirst(rs.getString("first"));
                a.setLast(rs.getString("last"));
                a.setIsSubscriber(rs.getBoolean("is_subscriber"));
                a.setContactMethod(rs.getString("contact_method"));
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
    public Actor getActor(int aId){
        DBConnection db = new DBConnection();
        Connection conn = db.getConnection();
        String sqlE = "Select * FROM actor where id = ?";
        String sqlA = "Select event.id as eid, * FROM event, actor_event_mapping aem WHERE aem.actor_id=? and aem.event_id = event.id";
        Actor a = new Actor();
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlE);
            stmt.setInt(1,aId);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                a.setId(rs.getInt("id"));
                a.setFirst(rs.getString("first"));
                a.setLast(rs.getString("last"));
                a.setContactMethod(rs.getString("contact_method"));
                a.setIsSubscriber(rs.getBoolean("is_subscriber"));

            }

            stmt = conn.prepareStatement(sqlA);
            stmt.setInt(1,aId);
            rs = stmt.executeQuery();
            ArrayList<Event> events = new ArrayList<Event>();
            while(rs.next()) {
                Event e = new Event();
                e.setId(rs.getInt("eid"));
                e.setName(rs.getString("name"));
                e.setOpenDate(rs.getDate("open_date"));
                e.setCloseDate(rs.getDate("close_date"));
                e.setJoinAllowAuto(rs.getBoolean("join_allow_auto"));
                e.setJoinAllowByAccept(rs.getBoolean("join_allow_by_accept"));
                e.setJoinInvitation(rs.getBoolean("join_invitation"));
                events.add(e);
            }
            a.setEvents(events);

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return a;
    }

}
