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

import static org.junit.Assert.*;

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
            uw.delete(user.getId());

        } catch (Exception e) {
            cleanup();
            fail();
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
            int userId = uw.insert(user);
            user.setId(userId);

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
            User u2 = ur.getUserBasicInfo(user.getId());
            assertEquals(u2.getFirst(), "xx");

            // remove it
            assertTrue(uw.delete(user.getId()));

            // check it is not there
            u = ur.getUserBasicInfo(user.getId());
            assertEquals(u.getId(), -1);     // ie a non-existent event

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}
