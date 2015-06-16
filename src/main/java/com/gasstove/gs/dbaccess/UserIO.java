package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class UserIO extends AbstractIO<User> {

    public UserIO(){};
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
    protected int fill_prepared_statement(PreparedStatement ps,AbstractObject obj) throws SQLException {
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
