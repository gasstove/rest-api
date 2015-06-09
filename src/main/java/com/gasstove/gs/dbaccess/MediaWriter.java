package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.Media;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by gomes on 5/2/15.
 */
public class MediaWriter extends BaseWriter {

    public MediaWriter(String db) throws SQLException {
        super(db);
    }

    public MediaWriter(Connection dbConn) {
        super(dbConn);
    }

    /** insert row in media table
     *
     */
    public int insert(DBObject object) throws Exception {

        Media media = (Media) object;

        String sql = "INSERT into media(type,file_name,user_id,date_taken) VALUES(?,?,?,?)";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setString(1, media.getType());
        statement.setString(2, media.getFileName());
        statement.setInt(3, media.getUserId());
        statement.setDate(4,media.getDateTaken().toSqlDate());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Insert failed for media " + + media.getId());

        // return new id (IS THIS CORRECT?)
        ResultSet rs = dbConn.createStatement().executeQuery("SELECT Max(id) from media");
        return rs.next() ? rs.getInt(1) : -1;
    }

    /** update row in media table
     */
    public int update(DBObject object) throws Exception{
        Media media = (Media) object;

        String sql = "UPDATE media SET " +
                        "type=?," +
                        "file_name=?," +
                        "user_id=?," +
                        "date_taken=? " +
                        "WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setString(1,media.getType());
        statement.setString(2,media.getFileName());
        statement.setInt(3,media.getUserId());
        statement.setDate(4,media.getDateTaken().toSqlDate());
        statement.setInt(5,media.getId());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Update failed for media " + + media.getId());

        return media.getId();
    }

    /** delete media
     *      + delete row from media table
     *      + delete row from media_event_mapping
     */
    public boolean delete(int id) throws Exception {

        boolean success = true;

        // delete row from media table
        String sql = "DELETE from media WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1,id);
        success &= statement.executeUpdate()==1;

        // delete row from media_event_mapping
        sql = "DELETE from media_event_mapping WHERE media_id=?";
        statement = dbConn.prepareStatement(sql);
        statement.setInt(1,id);
        success &= statement.executeUpdate()==1;

        return success;

    }

}
