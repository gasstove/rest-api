package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.ActorReader;
import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.models.Actor;
import com.gasstove.gs.models.Event;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests methods for EventReader
 *
 */
public class ActorReaderTest {

    /**
     * The constructor sets up database for so we can set up a Model DataBase Access
     * reader and writer;
     *
     * @throws Exception This happens if the database set up has trouble
     */
    public ActorReaderTest() throws Exception {

    }

    /**
     * This tests the get networks method. The method should return a list of networks.
     * We don't bother parsing back the response from JSON in this case.
     */
    @Test
    public void testGetActors() {
        try {

            ArrayList<Actor> list = ActorReader.getActors();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getFirst() != null);

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

    /**
     * This tests the get event method. The method should return am event
     */
    @Test
    public void testGetActor() {

        try {

            Actor a = ActorReader.getActor(122);
            assertTrue(a.getFirst().length() > 0);
            assertTrue(a.getId() == 122);
            assertNotNull(a.getEvents());
            assertNotNull(a.getLast());
            assertNotNull(a.getContactMethod());
            assertNotNull(a.isSubscriber());

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

}
