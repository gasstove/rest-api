package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.*;
import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class UserEventIO extends AbstractIO<UserEvent> {

    public UserEventIO(){};
    public UserEventIO(String db) { super(db); }
    public UserEventIO(Connection conn){ super(conn); }

    ////////////////////////////////////////////
    // Configuration
    ////////////////////////////////////////////

    @Override
    protected UserEvent generate_from_result_set(ResultSet rs){
        UserEvent x = new UserEvent();
        try {
            x.setId(rs.getInt("id"));
            x.setUserId(rs.getInt("user_id"));
            x.setEventId(rs.getInt("event_id"));
            x.setRole(Permissions.Role.valueOf(rs.getString("role").toUpperCase()));
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
        return x;
    }

    @Override
    protected String get_table_name(){
        return "user_event_mapping";
    }

    @Override
    protected ArrayList<String> get_fields(){
        ArrayList<String> x = new ArrayList<String>();
        x.add("event_id");
        x.add("user_id");
        x.add("role");
        return x;
    }

    @Override
    protected int fill_prepared_statement(PreparedStatement ps,AbstractObject obj) throws SQLException {
        UserEvent userevent = (UserEvent) obj;
        int i=1;
        ps.setInt(i++, userevent.getEventId());
        ps.setInt(i++, userevent.getUserId());
        ps.setString(i++,userevent.getRole().toString().toLowerCase());
        return i;
    }

    ////////////////////////////////////////////
    // additional readers / writers
    ////////////////////////////////////////////

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
    
    public ArrayList<Event> getEventsForUser(int uId) {
        ArrayList<Event> events = new ArrayList<Event>();
        UserEventIO userEventIO = new UserEventIO(conn);
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
                e.setOwnerId(userEventIO.getUserIdsForEventInRole(e.getId(),Permissions.Role.OWNER));
                events.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
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

    public final boolean deleteForEventAndUserIds(int event_id, int user_id) throws Exception{
        String sql = "DELETE from user_event_mapping WHERE event_id=? AND user_id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,event_id);
        statement.setInt(2,user_id);
        return statement.executeUpdate()==1;
    }

    // TODO: write test
    public final boolean deleteForEventId(int event_id) throws Exception{
        String sql = "DELETE from user_event_mapping WHERE event_id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,event_id);
        return statement.executeUpdate()==1;
    }


}
