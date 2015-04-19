package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserReader;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.util.TestDefaults;
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
        try {
            UserReader ar = new UserReader();
            ArrayList<User> list = ar.getUsersBasicInfo();
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
            UserReader ar = new UserReader();
            User a = ar.getUserFull(TestDefaults.user_id);
            assertTrue(a.getFirst().length() > 0);
            assertTrue(a.getId() == TestDefaults.user_id);
            assertNotNull(a.getEvents());
            assertNotNull(a.getLast());
            assertNotNull(a.getContactMethod());
            assertNotNull(a.isSubscriber());
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetUsersForEvent() {
        try {
            UserReader ar = new UserReader();
            ArrayList<User> us = ar.getUsersForEvent(TestDefaults.event_id);
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
}

