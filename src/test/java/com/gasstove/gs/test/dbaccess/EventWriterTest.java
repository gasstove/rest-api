package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.DBConnection;
import com.gasstove.gs.util.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class EventWriterTest {

    Connection conn;
    Event event;

    @Before
    public void setup(){
        try {

            // open db
            conn = new DBConnection().getConnection(TestConfiguration.db);

            // create an event
            event = new Event();
            event.setName("test");
            event.setOpenDate(new Time(0));
            event.setCloseDate(new Time(1));
            event.setJoinAllowAuto(false);
            event.setJoinAllowByAccept(false);
            event.setJoinInvitation(false);
            event.setOwnerId(1);

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
            fail();
        }
    }

    @Ignore
    @Test
    public void testCrud(){

        try {

            // create the event
            EventIO eventIO = new EventIO(conn);
            int eventId = eventIO.insert(event);
            event.setId(eventId);

            // check it is there
            Event e = eventIO.getWithId(event.getId());
            assertNotNull(e);
            assertEquals("test",e.getName());

//            // add a user
//            User user = new User();
//            user.setFirst("AAA");
//            user.setLast("BBB");
//            int userId = (new UserWriter(conn)).insert(user);

            // Extract a user
            User user = (new UserIO(conn)).getUserBasicInfo(TestConfiguration.user_id);

            // create media for this user
            Media media = new Media();
            media.setUserId(TestConfiguration.user_id);






            // modify the event
            event.setName("testMod");

            // update
            eventIO.update(event);

            // check it worked
            Event e2 = eventIO.getWithId(event.getId());
            assertEquals("testMod",e2.getName());

            // remove it
            assertTrue( eventIO.delete(event.getId()) );

            // check it is not there
            e = eventIO.getWithId(event.getId());
            assertEquals(-1,e.getId());     // ie a non-existent event

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}
