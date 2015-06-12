package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by gomes on 6/11/15.
 */
public class EventIOTest extends AbstractIOTest<Event> {

    public EventIOTest(){
        clath = Event.class;
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
            assertEquals(es.size(),1);
            String expected = "{\"name\":\"holt\",\"openDate\":\"1970-01-10T08:45:40.880-08:00\",\"closeDate\":\"1970-01-10T08:45:40.880-08:00\",\"ownerId\":41,\"id\":7}";
            assertEquals(es.get(0).toJson(), expected);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

}
