package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.MediaEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class MediaEventIO extends BaseIO<MediaEvent> {

    public MediaEventIO(){};
    public MediaEventIO(String db) { super(db); }
    public MediaEventIO(Connection conn){ super(conn); }

    ////////////////////////////////////////////
    // Configuration
    ////////////////////////////////////////////

    @Override
    protected MediaEvent generate_from_result_set(ResultSet rs){
        MediaEvent x = new MediaEvent();
        try {
            x.setMediaId(rs.getInt("media_id"));
            x.setEventId(rs.getInt("event_id"));
            x.setNumDownloads(rs.getInt("num_downloads"));
            x.setShared(rs.getBoolean("shared"));
            x.setComment(rs.getString("comment"));
            x.setNumLikes(rs.getInt("num_likes"));
            x.setNumDislikes(rs.getInt("num_dislikes"));
        } catch (SQLException exp) {
            exp.printStackTrace();
            return null;
        }
        return x;
    }

    @Override
    protected String get_table_name(){
        return "media_event_mapping";
    }

    @Override
    protected ArrayList<String> get_fields(){
        ArrayList<String> x = new ArrayList<String>();
        x.add("media_id");
        x.add("event_id");
        x.add("num_downloads");
        x.add("shared");
        x.add("comment");
        x.add("num_likes");
        x.add("num_dislikes");
        return x;
    }

    @Override
    protected int fill_prepared_statement(PreparedStatement ps,DBObject obj) throws SQLException {
        MediaEvent mediaevent = (MediaEvent) obj;
        int i=1;
        ps.setInt(i++, mediaevent.getMediaId());
        ps.setInt(i++, mediaevent.getEventId());
        ps.setInt(i++, mediaevent.getNumDownloads());
        ps.setBoolean(i++, mediaevent.getShared());
        ps.setString(i++, mediaevent.getComment() );
        ps.setInt(i++, mediaevent.getNumLikes());
        ps.setInt(i++, mediaevent.getNumDislikes());
        return i;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////


}
