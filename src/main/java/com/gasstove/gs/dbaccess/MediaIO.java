package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.util.Time;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class MediaIO extends BaseIO <Media> {

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
    protected int fill_prepared_statement(PreparedStatement ps,DBObject obj) throws SQLException {
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

    public ArrayList<MediaEvent> getMediaForUserAndEvent(int uId,int eId){
        ArrayList<MediaEvent> mediaevents = new ArrayList<MediaEvent>();
        try {
            // query media_event_mapping table
            String sql = "SELECT mm.media_id, mm.num_downloads, mm.shared, mm.comment, mm.num_likes, " +
                    "mm.num_dislikes, media.type, media.file_name, media.user_id, media.date_taken " +
                    "FROM media_event_mapping as mm, media " +
                    "WHERE mm.event_id =? " +
                    "AND media.id = mm.media_id " +
                    "AND media.user_id=? ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,eId);
            stmt.setInt(2,uId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                MediaEvent me = new MediaEvent();
                me.setUserId(uId);
                me.setEventId(eId);
                me.setShared(rs.getBoolean("shared"));
                me.setMediaId(rs.getInt("media_id"));
                me.setComment(rs.getString("comment"));
                me.setNumDownloads(rs.getInt("num_downloads"));
                me.setNumDislikes(rs.getInt("num_dislikes"));
                me.setNumLikes(rs.getInt("num_likes"));
                me.setMediaType(rs.getString("type"));
                me.setMediaDateTaken(new Time(rs.getInt("date_taken")));
                me.setMediaFileName(rs.getString("file_name"));
                mediaevents.add(me);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return mediaevents;
    }

    public ArrayList<MediaEvent> getSharedMediaForEvent(int eId){
        ArrayList<MediaEvent> mediaevents = new ArrayList<MediaEvent>();
        try {
            String sql = "SELECT mm.media_id, mm.num_downloads, mm.comment, mm.num_likes, mm.num_dislikes, " +
                    "media.type, media.file_name, media.user_id, media.date_taken " +
                    "FROM media_event_mapping as mm, media " +
                    "WHERE mm.event_id = ? " +
                    "AND mm.shared = 1 " +
                    "AND media.id = mm.media_id ";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,eId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                MediaEvent me = new MediaEvent();
                me.setShared(true);
                me.setEventId(eId);
                me.setMediaId(rs.getInt("media_id"));
                me.setComment(rs.getString("comment"));
                me.setNumDownloads(rs.getInt("num_downloads"));
                me.setNumDislikes(rs.getInt("num_dislikes"));
                me.setNumLikes(rs.getInt("num_likes"));
                me.setMediaType(rs.getString("type"));
                me.setMediaDateTaken(new Time(rs.getInt("date_taken")));
                me.setMediaFileName(rs.getString("file_name"));
                me.setUserId(rs.getInt("user_id"));
                mediaevents.add(me);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return mediaevents;
    }


}
