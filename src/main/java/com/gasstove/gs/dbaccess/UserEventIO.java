package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.*;

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
//        UserEvent x = new UserEvent();
//        try {
//        } catch (SQLException exp) {
//            exp.printStackTrace();
//            return null;
//        }
//        return x;
        return null;
    }

    @Override
    protected String get_table_name(){
        return "user_event_mapping";
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

}
