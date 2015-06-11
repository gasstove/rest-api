package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests methods for EventReader
 *
 */
public class UserReaderTest extends BaseReaderTest<User> {

    public UserReaderTest(){
        io = new UserIO(TestConfiguration.db);
        test_id = TestConfiguration.user_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

    @Test
    public void test_getUsersForEvent() {
        try {
            ArrayList<User> us = ((UserIO)io).getUsersForEvent(TestConfiguration.event_id);
            assertNotNull(us);
            assertTrue(us.size()>0);
            User u = us.get(0);
            assertTrue(u.getFirst().length() > 0);
            assertNotNull(u.getLast());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Ignore
    @Test
    public void test_getUserIdsForEventInRole() {

    }

}

