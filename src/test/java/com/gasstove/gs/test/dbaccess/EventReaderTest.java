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

    /**
     * The constructor sets up database for so we can set up a Model DataBase Access
     * reader and writer;
     *
     * @throws Exception This happens if the database set up has trouble
     */
    public EventReaderTest() throws Exception {

    }

    /**
     * This tests the get networks method. The method should return a list of networks.
     * We don't bother parsing back the response from JSON in this case.
     */
    @Test
    public void testGetEvents() {
        try {
            ArrayList<Event> list = EventReader.getEventsBasicInfo();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getName() != null);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    /**
     * This tests the get event method. The method should return am event
     */
    @Test
    public void testGetEvent() {

        try {
            Event e = EventReader.getEventFull(TestDefaults.event_id);
            assertTrue(e.getName().length() > 0);
            assertTrue(e.getId() == TestDefaults.event_id);
            assertNotNull(e.getUsers());
            assertNotNull(e.getCloseDate());
            assertNotNull(e.getOpenDate());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

}
