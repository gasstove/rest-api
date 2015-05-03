package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserReader;
import com.gasstove.gs.dbaccess.UserWriter;
import com.gasstove.gs.models.User;
import com.gasstove.gs.util.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by gomes on 5/2/15.
 */
public class UserWriterTest {

    Connection conn;
    User user;

    @Before
    public void setup(){
        try {

            // open db
            conn = new DBConnection().getConnection();

            // create a user
            user = new User();
            user.setId(-1);
            user.setFirst("x");
            user.setLast("x");
            user.setContactMethod("x");
            user.setIsSubscriber(true);

            // remove it from the db
            UserWriter uw = new UserWriter(conn);
            uw.delete(user);

        } catch (Exception e) {
            cleanup();
            e.printStackTrace();
        }
    }

    @After
    public void cleanup(){
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException ee) {
            ee.printStackTrace();
        }
    }

    @Test
    public void testCrud(){

        try {

            // create the event
            UserWriter uw = new UserWriter(conn);
            uw.insert(user);

            // check it is there
            UserReader ur = new UserReader(conn);
            User u = ur.getUserBasicInfo(user.getId());
            assertNotNull(u);
            assertEquals(u.getFirst(),"x");

            // modify the event
            user.setFirst("xx");

            // update
            uw.update(user);

            // check it worked
            u = ur.getUserBasicInfo(user.getId());
            assertEquals(u.getFirst(), "xx");

            // remove it
            uw.delete(user);

            // check it is not there
            u = ur.getUserBasicInfo(user.getId());
            assertEquals(u.getId(), -1);     // ie a non-existent event

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
