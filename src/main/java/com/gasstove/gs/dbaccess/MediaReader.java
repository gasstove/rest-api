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

    /**
     * Returns a list of all the medias in the db.
     * Provides for each media: id, type, file_name
     * @return ArrayList<Media> a list of media objects
     */
    public static ArrayList<Media> getMediasBasicInfo(){
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


//    public static Media getMedia(int mId){
//
//        Media media = new Media();
//        try {
//            DBConnection db = new DBConnection();
//            Connection conn = db.getConnection();
//
//            // query media table
//            String sqlM = "Select * FROM media where id = ?";
//            PreparedStatement stmt = conn.prepareStatement(sqlM);
//            stmt.setInt(1,mId);
//            ResultSet rs = stmt.executeQuery();
//            if(rs.next()){
//                media.setId(rs.getInt("id"));
//                media.setType(rs.getString("type"));
//                media.setFileName(rs.getString("file_name"));
//            }
//
//            // query media_mapping table
//            String strMM = "Select * FROM media_mapping where media_id = ?";
//            stmt = conn.prepareStatement(strMM);
//            stmt.setInt(1,mId);
//            rs = stmt.executeQuery();
//            while(rs.next()) {
//                MediaEvent mc = new MediaEvent();
//
//                mc.numDownloads = rs.getInt("num_downloads");
//                mc.shared = rs.getBoolean("shared");
//
//                // query actor_event_mapping table
//                int actor_event_id = rs.getInt("actor_event_mapping_id");
//                String sqlAE = "Select * FROM actor_event_mapping where id = ?";
//                stmt = conn.prepareStatement(sqlAE);
//                stmt.setInt(1,actor_event_id);
//                ResultSet rs2 = stmt.executeQuery();
//                if(rs2.next()){
//                    // This is silly, media has a single owner, and should know it directly
//                    media.setOwnerName(ActorReader.getActorNameWithId(rs2.getInt("actor_id")));
//                    mc.event_id = rs2.getInt("event_id");
//                }
//
//                media.addCard(mc);
//            }
//
//        } catch (SQLException sq) {
//            sq.printStackTrace();
//        }
//        return media;
//    }

//    public static ArrayList<Media> getMediaForEvent(int eId){
//        ArrayList<Media> medias = new ArrayList<Media>();
//
//        try{
//
//            DBConnection db = new DBConnection();
//            Connection conn = db.getConnection();
//
//            // query actor_event_mapping table for actoreventids
//            String sql = "Select media.id as media_id, media.type as media_type, media.file_name as media_file_name, actor.id as actor_id, actor.first as actor_first, actor.last as actor_last ";
//            sql += "FROM actor_event_mapping aem , media_mapping mm, media, actor ";
//            sql += "WHERE  aem.event_id=? ";
//            sql += "AND aem.id=mm.actor_event_mapping_id ";
//            sql += "AND media.id=mm.media_id ";
//            sql += "AND actor.id=aem.actor_id ";
//
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            stmt.setInt(1,eId);
//            ResultSet rs = stmt.executeQuery();
//            while(rs.next()){
//                Media m = new Media();
//                m.setType(rs.getString("media_type"));
//                m.setFileName(rs.getString("media_file_name"));
//                m.setId(rs.getInt("media_id"));
//                m.setOwnerName(rs.getString("actor_first")+" "+rs.getString("actor_last"));
//                m.setOwnerId(rs.getInt("actor_id"));
//                medias.add(m);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return medias;
//    }


}
