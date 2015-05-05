package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.dbaccess.EventWriter;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.DBConnection;
import com.gasstove.gs.util.Time;
import org.junit.After;
import org.junit.Before;
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
            conn = new DBConnection().getConnection();

            // create an event
            event = new Event();
            event.setName("test");
            event.setOpenDate(new Time(0));
            event.setCloseDate(new Time(1));
            event.setJoinAllowAuto(false);
            event.setJoinAllowByAccept(false);
            event.setJoinInvitation(false);

//            // remove it from the db, ignore exception
//            try {
//                EventWriter ew = new EventWriter(conn);
//                ew.delete(event.getId());
//            } catch (Exception e){
//            }

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

    @Test
    public void testCrud(){

        try {

            // create the event
            EventWriter ew = new EventWriter(conn);
            int eventId = ew.insert(event);
            event.setId(eventId);

            // check it is there
            EventReader er = new EventReader(conn);
            Event e = er.getEventBasicInfo(event.getId());
            assertNotNull(e);
            assertEquals("test",e.getName());

            // modify the event
            event.setName("testMod");

            // update
            ew.update(event);

            // check it worked
            Event e2 = er.getEventBasicInfo(event.getId());
            assertEquals("testMod",e2.getName());

            // remove it
            assertTrue( ew.delete(event.getId()) );

            // check it is not there
            e = er.getEventBasicInfo(event.getId());
            assertEquals(-1,e.getId());     // ie a non-existent event

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}
