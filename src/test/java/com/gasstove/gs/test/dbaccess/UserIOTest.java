package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Permissions;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by gomes on 6/11/15.
 */
public class UserIOTest extends AbstractIOTest<User> {

    public UserIOTest(){
        clath = User.class;
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
            assertEquals(us.size(), 5);

            ArrayList<String> exp = new ArrayList<String>();
            exp.add("{\"first\":\"Launa\",\"last\":\"Loud\",\"id\":33}");
            exp.add("{\"first\":\"Catrice\",\"last\":\"Chapple\",\"id\":9}");
            exp.add("{\"first\":\"Takako\",\"last\":\"Troxel\",\"id\":21}");
            exp.add("{\"first\":\"Cornelius\",\"last\":\"Chau\",\"id\":12}");
            exp.add("{\"first\":\"Ceola\",\"last\":\"Cue\",\"id\":2}");

            for(int i=0;i<us.size();i++)
                assertEquals(us.get(i).toJson(),exp.get(i));
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_getUserIdsForEventInRole() {

        // GUEST
        ArrayList<Integer> ids = ((UserIO)io).getUserIdsForEventInRole(TestConfiguration.event_id, Permissions.Role.GUEST);
        assertNotNull(ids);
        assertEquals(ids.size(),4);
        Integer [] exp = {9,21,12,2};
        for(int i=0;i<ids.size();i++)
            assertEquals(ids.get(i),exp[i]);

        // OWNER
        ids = ((UserIO)io).getUserIdsForEventInRole(TestConfiguration.event_id, Permissions.Role.OWNER);
        assertNotNull(ids);
        assertEquals(ids.size(),1);
        assertEquals((int) ids.get(0),33);

    }

}
