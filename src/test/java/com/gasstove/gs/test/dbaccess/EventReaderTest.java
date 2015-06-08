package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.models.Event;

import com.gasstove.gs.test.TestConfiguration;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests methods for EventReader
 *
 */
public class EventReaderTest {

    EventReader er = new EventReader(TestConfiguration.db);

    @Test
    public void testGetEventsBasicInfo() {
        try {
            ArrayList<Event> list = er.getEventsBasicInfo();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getName() != null);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetEventFull() {
        try {
            Event e = er.getEventBasicInfo(TestConfiguration.event_id);
            assertTrue(e.getName().length() > 0);
            assertTrue(e.getId() == TestConfiguration.event_id);
            assertNotNull(e.getCloseDate());
            assertNotNull(e.getOpenDate());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetEventsForUser() {
        try {
            ArrayList<Event> es = er.getEventsForUser(TestConfiguration.user_id);
            assertNotNull(es);
            assertTrue(es.size()>0);
            Event e = es.get(0);
            assertTrue(e.getName().length() > 0);
            assertNotNull(e.getCloseDate());
            assertNotNull(e.getOpenDate());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @After
    public void close(){
        er.close();
    }

}
