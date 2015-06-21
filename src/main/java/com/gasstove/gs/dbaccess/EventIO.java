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
        Event x = new Event();
        try {
            x.setId(rs.getInt("id"));
            x.setName(rs.getString("name"));
            x.setOpenDate(new Time(rs.getInt("open_date")));
            x.setCloseDate(new Time(rs.getInt("close_date")));
            x.setJoinAllowAuto(rs.getBoolean("join_allow_auto"));
            x.setJoinAllowByAccept(rs.getBoolean("join_allow_by_accept"));
            x.setJoinInvitation(rs.getBoolean("join_invitation"));
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
        return x;
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
            UserEventIO userEventIO = new UserEventIO(conn);
            e.setOwnerId(userEventIO.getUserIdsForEventInRole(e.getId(), Permissions.Role.OWNER));
        }

        return e;
    }

    @Override
    public ArrayList<Event> getAll() {
        ArrayList<Event> E = super.getAll();

        // add owner id
        UserEventIO userEventIO = new UserEventIO(conn);
        for(Event e : E)
            e.setOwnerId( userEventIO.getUserIdsForEventInRole( e.getId(), Permissions.Role.OWNER));
        return E;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

}
