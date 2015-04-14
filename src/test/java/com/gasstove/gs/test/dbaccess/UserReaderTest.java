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

    /**
     * The constructor sets up database for so we can set up a Model DataBase Access
     * reader and writer;
     *
     * @throws Exception This happens if the database set up has trouble
     */
    public UserReaderTest() throws Exception {

    }

    /**
     * This tests the get networks method. The method should return a list of networks.
     * We don't bother parsing back the response from JSON in this case.
     */
    @Test
    public void testGetUsers() {
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

    /**
     * This tests the get event method. The method should return am event
     */
    @Test
    public void testGetUser() {

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

}
