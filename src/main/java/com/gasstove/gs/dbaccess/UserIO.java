package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.User;
import com.gasstove.gs.util.Permissions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class UserIO extends BaseIO <User> {

    public UserIO(String db) { super(db); }
    public UserIO(Connection conn){ super(conn); }

    ////////////////////////////////////////////
    // Configuration
    ////////////////////////////////////////////

    @Override
    protected User generate_from_result_set(ResultSet rs){
        User x = new User();
        try {
            x.setId(rs.getInt("id"));
            x.setFirst(rs.getString("first"));
            x.setLast(rs.getString("last"));
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
        return x;
    }

    @Override
    protected String get_table_name(){
        return "user";
    }

    @Override
    protected ArrayList<String> get_fields(){
        ArrayList<String> x = new ArrayList<String>();
        x.add("first");
        x.add("last");
        return x;
    }

    @Override
    protected int fill_prepared_statement(PreparedStatement ps,DBObject obj) throws SQLException {
        User user = (User) obj;
        int i=1;
        ps.setString(i++, user.getFirst());
        ps.setString(i++, user.getLast());
        return i;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

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
