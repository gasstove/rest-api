package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Time;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class EventIO extends AbstractIO<Event> {

    public EventIO(){};
    public EventIO(String db) { super(db); }
    public EventIO(Connection conn){ super(conn); }

    ////////////////////////////////////////////
    // Configuration
    ////////////////////////////////////////////

    @Override
    protected Event generate_from_result_set(ResultSet rs){
        Event e = new Event();
        try {
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setOpenDate(new Time(rs.getInt("open_date")));
            e.setCloseDate(new Time(rs.getInt("close_date")));
            e.setJoinAllowAuto(rs.getBoolean("join_allow_auto"));
            e.setJoinAllowByAccept(rs.getBoolean("join_allow_by_accept"));
            e.setJoinInvitation(rs.getBoolean("join_invitation"));
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
        return e;
    }

    @Override
    protected String get_table_name(){
        return "event";
    }

    @Override
    protected ArrayList<String> get_fields(){
        ArrayList<String> x = new ArrayList<String>();
        x.add("name");
        x.add("open_date");
        x.add("close_date");
        x.add("join_invitation");
        x.add("join_allow_by_accept");
        x.add("join_allow_auto");
        return x;
    }

    @Override
    protected int fill_prepared_statement(PreparedStatement ps,AbstractObject obj) throws SQLException {
        Event x = (Event) obj;
        int i=1;
        ps.setString(  i++ , x.getName());
        ps.setDate(    i++ , x.getOpenDate().toSqlDate() );
        ps.setDate(    i++ , x.getCloseDate().toSqlDate() );
        ps.setBoolean( i++ , x.isJoinInvitation());
        ps.setBoolean( i++ , x.isJoinAllowByAccept());
        ps.setBoolean( i++ , x.isJoinAllowAuto());
        return i;
    }

    ////////////////////////////////////////////
    // overrides
    ////////////////////////////////////////////

    @Override
    public Event getWithId(int id) {
        Event e = super.getWithId(id);

        if(e!=null) {
            // add owner id
            UserIO userIO = new UserIO(conn);
            e.setOwnerId(userIO.getUserIdsForEventInRole(e.getId(), Permissions.Role.OWNER));
        }

        return e;
    }

    @Override
    public ArrayList<Event> getAll() {
        ArrayList<Event> E = super.getAll();

        // add owner id
        UserIO userIO = new UserIO(conn);
        for(Event e : E)
            e.setOwnerId( userIO.getUserIdsForEventInRole( e.getId(), Permissions.Role.OWNER));
        return E;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

    public ArrayList<Event> getEventsForUser(int uId) {
        ArrayList<Event> events = new ArrayList<Event>();
        UserIO ur = new UserIO(conn);
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
                e.setOwnerId(ur.getUserIdsForEventInRole(e.getId(),Permissions.Role.OWNER));
                events.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

}
