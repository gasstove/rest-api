package com.gasstove.gs.dbaccess;

import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserWriter  extends WriterBase {

    public UserWriter(Connection dbConn) {
        super(dbConn);
    }

    public int insert(DBObject object) throws Exception {

        User user = (User) object;

        String sql = "INSERT into user(id,first,last,is_subscriber,contact_method) VALUES(?,?,?,?,?)";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1, user.getId());
        statement.setString(2, user.getFirst());
        statement.setString(3, user.getLast());
        statement.setBoolean(4, user.isSubscriber());
        statement.setString(5, user.getContactMethod());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Insert failed for user " + + user.getId());

        // return new id (IS THIS CORRECT?)
        ResultSet rs = dbConn.createStatement().executeQuery("SELECT Max(id) from user");
        return rs.next() ? rs.getInt(1) : -1;
    }

    public void update(DBObject object) throws Exception{
        User user = (User) object;

        String sql = "UPDATE user SET " +
                "first=?," +
                "last=?," +
                "is_subscriber=?," +
                "contact_method=? " +
                "WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setString(1,user.getFirst());
        statement.setString(2,user.getLast());
        statement.setBoolean(3,user.isSubscriber());
        statement.setString(4,user.getContactMethod());
        statement.setInt(5,user.getId());
        int r = statement.executeUpdate();

        if(r!=1)
            throw new Exception("Update failed for user " + + user.getId());
    }

    public boolean delete(int id) throws Exception {
        String sql = "DELETE from user WHERE id=?";
        PreparedStatement statement = dbConn.prepareStatement(sql);
        statement.setInt(1,id);
        int r = statement.executeUpdate();
        return r==1;
    }

}
