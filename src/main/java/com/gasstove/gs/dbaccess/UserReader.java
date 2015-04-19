package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.User;
import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * These are all the methods needed to get data for Users objects.
 *
 * Created by seanmorris on 3/22/15.
 */
public class UserReader {

    private Connection conn;

    public UserReader() {
        try {
            conn = (new DBConnection()).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used if someone with a valid connection wants to use the UserReader
     * @param conn
     */
    public UserReader(Connection conn) {
        this.conn = conn;
    }

    /**
     * Returns a list of all the users in the db.
     * Provides for each user: id, first, last
     *
     * @return ArrayList<Users> a list of user objects
     */
    public ArrayList<User> getUsersBasicInfo() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setFirst(rs.getString("first"));
                a.setLast(rs.getString("last"));
                users.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Get an an by id and return a fully populated actror object that includes
     * the list of events the user is in
     *
     * @param aId the user id to query for
     * @return User a fully populated user object
     */
    public User getUserFull(int aId) {
        User a = new User();
        try {

            // query for user
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user where id = ?");
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
            a.setEvents(er.getEventsForUser(a.getId()));

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return a;
    }

    public ArrayList<User> getUsersForEvent(int eId){
        ArrayList<User> users = new ArrayList<User>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT user.id, first, last FROM user, user_event_mapping aem WHERE aem.event_id=? AND aem.user_id = user.id");
            stmt.setInt(1, eId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setFirst(rs.getString("first"));
                a.setLast(rs.getString("last"));
                users.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}