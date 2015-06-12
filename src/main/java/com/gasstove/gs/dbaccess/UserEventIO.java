package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.*;
import com.gasstove.gs.util.Permissions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class UserEventIO extends BaseIO <UserEvent> {

    public UserEventIO(String db) { super(db); }
    public UserEventIO(Connection conn){ super(conn); }

    ////////////////////////////////////////////
    // Configuration
    ////////////////////////////////////////////

    @Override
    protected UserEvent generate_from_result_set(ResultSet rs){
        UserEvent x = new UserEvent();
        try {
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
    protected int fill_prepared_statement(PreparedStatement ps,DBObject obj) throws SQLException {
        UserEvent userevent = (UserEvent) obj;
        int i=1;
        ps.setInt(i++, userevent.getEventId());
        ps.setInt(i++, userevent.getUserId());
        ps.setString(i++,userevent.getRole().toString().toLowerCase());
        return i;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

}
