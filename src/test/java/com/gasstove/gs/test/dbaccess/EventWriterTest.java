package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.dbaccess.EventWriter;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.DBConnection;
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
            event.setOpenDate(new Date(0));
            event.setCloseDate(new Date(1));
            event.setJoinAllowAuto(false);
            event.setJoinAllowByAccept(false);
            event.setJoinInvitation(false);

            // remove it from the db
            EventWriter ew = new EventWriter(conn);
            ew.delete(event.getId());

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
            EventWriter ew = new EventWriter(conn);
            ew.insert(event);

            // check it is there
            EventReader er = new EventReader(conn);
            Event e = er.getEventBasicInfo(event.getId());
            assertNotNull(e);
            assertEquals(e.getName(),"test");

            // modify the event
            event.setName("testMod");

            // update
            ew.update(event);

            // check it worked
            e = er.getEventBasicInfo(event.getId());
            assertEquals(e.getName(),"testMod");

            // remove it
            assertTrue( ew.delete(event.getId()) );

            // check it is not there
            e = er.getEventBasicInfo(event.getId());
            assertEquals(e.getId(),-1);     // ie a non-existent event

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
