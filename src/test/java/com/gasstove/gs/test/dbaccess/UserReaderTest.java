package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests methods for EventReader
 *
 */
public class UserReaderTest {

    UserIO userIO = new UserIO(TestConfiguration.db);

    @Test
    public void testGetUsersBasicInfo() {
        try {
            ArrayList<User> list = userIO.getAll();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getFirst() != null);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetUserFull() {
        try {
            User a = userIO.getUserBasicInfo(TestConfiguration.user_id);
            assertTrue(a.getFirst().length() > 0);
            assertTrue(a.getId() == TestConfiguration.user_id);
//            assertNotNull(a.getEventsBasicInfo());
            assertNotNull(a.getLast());
//            assertNotNull(a.getContactMethod());
//            assertNotNull(a.isSubscriber());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetUsersForEvent() {
        try {
            ArrayList<User> us = userIO.getUsersForEvent(TestConfiguration.event_id);
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

    @After
    public void close(){
        userIO.close();
    }

}

