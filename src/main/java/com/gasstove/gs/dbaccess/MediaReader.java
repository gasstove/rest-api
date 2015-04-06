package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.MediaCard;
import com.gasstove.gs.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by gomes on 4/6/15.
 */
public class MediaReader {

    /**
     * This returns a list of all the media in the db. Eventually it will need to be filtered
     *
     * @return ArrayList<Media> a list of media objects
     */
    public static ArrayList<Media> getMedias(){
        DBConnection db = new DBConnection();
        ArrayList<Media> medias = new ArrayList<Media>();
        String sql = "Select * FROM media";
        try {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
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


    // TODO: add "owner" to media table and get it directly
    public static Media getMedia(int mId){

        Media media = new Media();
        try {
            DBConnection db = new DBConnection();
            Connection conn = db.getConnection();

            // query media table
            String sqlM = "Select * FROM media where id = ?";
            PreparedStatement stmt = conn.prepareStatement(sqlM);
            stmt.setInt(1,mId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                media.setId(rs.getInt("id"));
                media.setType(rs.getString("type"));
                media.setFileName(rs.getString("file_name"));
            }

            // query media_mapping table
            String strMM = "Select * FROM media_mapping where media_id = ?";
            stmt = conn.prepareStatement(strMM);
            stmt.setInt(1,mId);
            rs = stmt.executeQuery();
            while(rs.next()) {
                MediaCard mc = new MediaCard();

                mc.media = media;
                mc.numDownloads = rs.getInt("num_downloads");
                mc.shared = rs.getBoolean("shared");

                // query actor_event_mapping table
                int actor_event_id = rs.getInt("actor_event_mapping_id");
                String sqlAE = "Select * FROM actor_event_mapping where id = ?";
                stmt = conn.prepareStatement(sqlAE);
                stmt.setInt(1,actor_event_id);
                ResultSet rs2 = stmt.executeQuery();
                if(rs2.next()){
                    // This is silly, media has a single owner, and should know it directly
                    media.setOwner( ActorReader.getActor(rs2.getInt("actor_id")));
                    mc.event = EventReader.getEvent(rs2.getInt("event_id"));
                }

                media.addCard(mc);
            }

        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return media;
    }

}
