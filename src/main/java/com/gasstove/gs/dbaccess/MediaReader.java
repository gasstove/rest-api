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
                Media media = new Media();
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setFileName(rs.getString("file_name"));
                media.setUserId(rs.getInt("user_id"));
                media.setDateTaken(rs.getDate("date_taken"));
                medias.add(media);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medias;
    }

    public Media getMediaBasicInfo(int mId){

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
                media.setDateTaken(rs.getDate("date_taken"));
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return media;
    }

    public ArrayList<MediaEvent> getMediaForUserAndEvent(int uId,int eId){
        ArrayList<MediaEvent> mediaevents = new ArrayList<MediaEvent>();
        try {
            // query media_mapping table
            String sql = "SELECT mm.media_id, mm.num_downloads, mm.shared, mm.comment, mm.num_likes, " +
                    "mm.num_dislikes, media.type, media.file_name, media.user_id, media.date_taken " +
                    "FROM media_mapping as mm, media " +
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
                me.setMediaDateTaken(rs.getDate("date_taken"));
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
                    "FROM media_mapping as mm, media " +
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
                me.setMediaDateTaken(rs.getDate("date_taken"));
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