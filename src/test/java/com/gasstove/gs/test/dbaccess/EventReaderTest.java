package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.models.Event;

import com.gasstove.gs.test.TestConfiguration;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EventReaderTest extends BaseReaderTest<Event> {

    public EventReaderTest(){
        io = new EventIO(TestConfiguration.db);
        test_id = TestConfiguration.event_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

    @Test
    public void test_getEventsForUser() {
        try {
            ArrayList<Event> es = ((EventIO)io).getEventsForUser(TestConfiguration.user_id);
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
