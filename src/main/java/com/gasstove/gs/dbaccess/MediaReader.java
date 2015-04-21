package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by gomes on 4/6/15.
 */
public class MediaReader {

    private Connection conn;

    public MediaReader() {
        try {
            conn = (new DBConnection()).getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MediaReader(Connection conn) {
        this.conn = conn;
    }

    /**
     * Returns a list of all the medias in the db.
     * Provides for each media: id, type, file_name
     *
     * @return ArrayList<Media> a list of media objects
     */
    public ArrayList<Media> getMediasBasicInfo() {
        ArrayList<Media> medias = new ArrayList<Media>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * FROM media");
            while (rs.next()) {
                Media m = new Media();
                m.setId(rs.getInt("id"));
                m.setType(rs.getString("type"));
                m.setFileName(rs.getString("file_name"));
                medias.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medias;
    }


    public Media getMedia(int mId){

        Media media = new Media();
        try {

            // query media table
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM media WHERE id = ?");
            stmt.setInt(1,mId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setFileName(rs.getString("file_name"));
                media.setUserId(rs.getInt("user_id"));
            }

            // query media_mapping table
            stmt = conn.prepareStatement("SELECT * FROM media_mapping WHERE media_id = ?");
            stmt.setInt(1,mId);
            rs = stmt.executeQuery();
            while(rs.next()) {
                MediaEvent me = new MediaEvent();
                me.setMediaId(mId);
                me.setComment(rs.getString("comment"));
                me.setShared(rs.getBoolean("shared"));
                me.setNumDownloads(rs.getInt("num_downloads"));
                me.setEventId(rs.getInt("event_id"));
                me.setNumDislikes(rs.getInt("num_dislikes"));
                me.setNumLikes(rs.getInt("num_likes"));
                media.add_media_event(me);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return media;
    }

    public ArrayList<MediaEvent> getMediaForEvent(int eId){
        ArrayList<MediaEvent> medias = new ArrayList<MediaEvent>();

        try{
            // query user_event_mapping table for usereventids
            String sql = "SELECT media.id as media_id, media.type as media_type, media.file_name as media_file_name, media.user_id as user_id, mm.num_downloads as num_downloads, mm.shared as shared, mm.comment as comment, mm.num_likes as num_likes, mm.num_dislikes as num_dislikes";
            sql += " FROM media_mapping mm, media";
            sql += " WHERE  mm.event_id=?";
            sql += " AND media.id=mm.media_id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,eId);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                MediaEvent m = new MediaEvent();
                m.setMediaId(rs.getInt("media_id"));
                m.setUserId(rs.getInt("user_id"));
                m.setEventId(eId);
                m.setMediaType(rs.getString("media_type"));
                m.setMediaFileName(rs.getString("media_file_name"));
                m.setNumDislikes(rs.getInt("num_dislikes"));
                m.setNumDownloads(rs.getInt("num_downloads"));
                m.setNumLikes(rs.getInt("num_likes"));
                m.setShared(rs.getBoolean("shared"));
                m.setComment(rs.getString("comment"));
                medias.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medias;
    }

}