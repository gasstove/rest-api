package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.util.Time;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class MediaIO extends AbstractIO<Media> {

    public MediaIO(){};
    public MediaIO(String db) { super(db); }
    public MediaIO(Connection conn){ super(conn); }

    ////////////////////////////////////////////
    // Configuration
    ////////////////////////////////////////////

    @Override
    protected Media generate_from_result_set(ResultSet rs){
        Media x = new Media();
        try {
            x.setId(rs.getInt("id"));
            x.setType(rs.getString("type"));
            x.setFileName(rs.getString("file_name"));
            x.setUserId(rs.getInt("user_id"));
            x.setDateTaken(new Time(rs.getInt("date_taken")));
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
        return x;
    }

    @Override
    protected String get_table_name(){
        return "media";
    }

    @Override
    protected ArrayList<String> get_fields(){
        ArrayList<String> x = new ArrayList<String>();
        x.add("type");
        x.add("file_name");
        x.add("user_id");
        x.add("date_taken");
        return x;
    }

    @Override
    protected int fill_prepared_statement(PreparedStatement ps,AbstractObject obj) throws SQLException {
        Media media = (Media) obj;
        int i=1;
        ps.setString(i++, media.getType());
        ps.setString(i++, media.getFileName());
        ps.setInt(i++, media.getUserId());
        ps.setDate(i++,media.getDateTaken().toSqlDate());
        return i;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////



}
