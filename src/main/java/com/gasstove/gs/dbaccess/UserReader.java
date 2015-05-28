package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.User;
import com.gasstove.gs.util.Permissions;

import java.sql.*;
import java.util.ArrayList;

/**
 * Database reader for Users
 */
public class UserReader extends BaseReader {

    public UserReader() { super(); }
    public UserReader(Connection conn) { super(conn); }

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
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirst(rs.getString("first"));
                user.setLast(rs.getString("last"));
//                user.setContactMethod(rs.getString("contact_method"));
//                user.setIsSubscriber(rs.getBoolean("is_subscriber"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     *
     * @param uId the user id to query for
     * @return User a fully populated user object
     */
    public User getUserBasicInfo(int uId) {
        User user = new User();
        try {

            // query for user
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user where id = ?");
            stmt.setInt(1, uId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFirst(rs.getString("first"));
                user.setLast(rs.getString("last"));
//                user.setContactMethod(rs.getString("contact_method"));
//                user.setIsSubscriber(rs.getBoolean("is_subscriber"));
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return user;
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

    public ArrayList<Integer> getUserIdsForEventInRole(int eId, Permissions.Role role){
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT uem.user_id as id " +
                    "FROM event , user_event_mapping as uem " +
                    "WHERE event.id=? and uem.role=? and uem.event_id=event.id");
            stmt.setInt(1, eId);
            stmt.setString(2,role.toString().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
                ids.add(rs.getInt("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

}