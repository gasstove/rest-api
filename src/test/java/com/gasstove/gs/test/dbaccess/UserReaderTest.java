package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserReader;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Tests methods for EventReader
 *
 */
public class UserReaderTest {

    public UserReaderTest() throws Exception {

    }

    @Test
    public void testGetUsersBasicInfo() {
        UserReader ur = new UserReader(TestConfiguration.db);
        try {
            ArrayList<User> list = ur.getUsersBasicInfo();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getFirst() != null);

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        } finally {
            ur.close();
        }

    }

    @Test
    public void testGetUserFull() {

        UserReader ur = new UserReader(TestConfiguration.db);
        try {
            User a = ur.getUserBasicInfo(TestConfiguration.user_id);
            assertTrue(a.getFirst().length() > 0);
            assertTrue(a.getId() == TestConfiguration.user_id);
//            assertNotNull(a.getEventsBasicInfo());
            assertNotNull(a.getLast());
//            assertNotNull(a.getContactMethod());
//            assertNotNull(a.isSubscriber());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        } finally {
            ur.close();
        }
    }

    @Test
    public void testGetUsersForEvent() {
        UserReader ur = new UserReader(TestConfiguration.db);
        try {
            ArrayList<User> us = ur.getUsersForEvent(TestConfiguration.event_id);
            assertNotNull(us);
            assertTrue(us.size()>0);
            User u = us.get(0);
            assertTrue(u.getFirst().length() > 0);
            assertNotNull(u.getLast());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        } finally {
            ur.close();
        }
    }
}

