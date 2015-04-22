package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.models.Event;

import com.gasstove.gs.test.util.TestDefaults;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests methods for EventReader
 *
 */
public class EventReaderTest {

    @Test
    public void testGetEventsBasicInfo() {
        try {
            EventReader er = new EventReader();
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
            EventReader er = new EventReader();
            Event e = er.getEventBasicInfo(TestDefaults.event_id);
            assertTrue(e.getName().length() > 0);
            assertTrue(e.getId() == TestDefaults.event_id);
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
            EventReader er = new EventReader();
            ArrayList<Event> es = er.getEventsForUser(TestDefaults.user_id);
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

}
