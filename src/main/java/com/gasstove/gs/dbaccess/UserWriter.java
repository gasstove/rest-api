package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserWriter  extends BaseWriter {

    public UserWriter(Connection dbConn) {
        super(dbConn);
    }

    public int insert(DBObject object) throws Exception {

        User user = (User) object;

        String sql = "INSERT into user(first,last) VALUES(?,?)";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setString(1, user.getFirst());
        statement.setString(2, user.getLast());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Insert failed for user " + + user.getId());

        // return new id (IS THIS CORRECT?)
        ResultSet rs = dbConn.createStatement().executeQuery("SELECT Max(id) from user");
        return rs.next() ? rs.getInt(1) : -1;
    }

    public int update(DBObject object) throws Exception{
        User user = (User) object;

        String sql = "UPDATE user SET " +
                "first=?," +
                "last=? " +
                "WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setString(1,user.getFirst());
        statement.setString(2,user.getLast());
        statement.setInt(3,user.getId());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Update failed for user " + + user.getId());

        return user.getId();
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE from user WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1,id);
        int r = statement.executeUpdate();
        return r==1;
    }

}
