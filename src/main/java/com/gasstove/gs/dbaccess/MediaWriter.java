package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.Media;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by gomes on 5/2/15.
 */
public class MediaWriter extends WriterBase {

    public MediaWriter(Connection dbConn) {
        super(dbConn);
    }

    public int insert(DBObject object) throws Exception {

        Media media = (Media) object;

        String sql = "INSERT into media(id,type,file_name,user_id,date_taken) VALUES(?,?,?,?,?)";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1, media.getId());
        statement.setString(2, media.getType());
        statement.setString(3, media.getFileName());
        statement.setInt(4, media.getUserId());
        statement.setDate(5, new Date(media.getDateTaken().getTime()));
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Insert failed for media " + + media.getId());

        // return new id (IS THIS CORRECT?)
        ResultSet rs = dbConn.createStatement().executeQuery("SELECT Max(id) from media");
        return rs.next() ? rs.getInt(1) : -1;
    }

    public void update(DBObject object) throws Exception{
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
        statement.setDate(4,new Date(media.getDateTaken().getTime()));
        statement.setInt(5,media.getId());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Update failed for media " + + media.getId());
    }

    public void delete(DBObject object) throws Exception {
        String sql = "DELETE from media WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1, ((Media) object).getId());
        statement.executeUpdate();
    }

}
