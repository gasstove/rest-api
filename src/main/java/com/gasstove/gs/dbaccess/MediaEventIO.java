package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.util.Time;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class MediaEventIO extends AbstractIO<MediaEvent> {

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
            x.setId(rs.getInt("id"));
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
    protected int fill_prepared_statement(PreparedStatement ps,AbstractObject obj) throws SQLException {
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


    // TODO: write test
    public final boolean deleteForEventId(int event_id) throws Exception{
        String sql = "DELETE from media_event_mapping WHERE event_id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,event_id);
        return statement.executeUpdate()==1;
    }

    // TODO: write test
    public final boolean deleteForMediaId(int media_id) throws Exception{
        String sql = "DELETE from media_event_mapping WHERE media_id=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1,media_id);
        return statement.executeUpdate()==1;
    }

}
