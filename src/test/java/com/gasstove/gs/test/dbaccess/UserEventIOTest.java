package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserEventIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.models.UserEvent;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Permissions;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by gomes on 6/11/15.
 */
public class UserEventIOTest extends AbstractIOTest<UserEvent> {

    public UserEventIOTest(){
        clath = UserEvent.class;
        io = new UserEventIO(TestConfiguration.db);
        test_id = TestConfiguration.userevent_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

    @Test
    public void test_getUsersForEvent() {
        try {
            ArrayList<User> us = ((UserEventIO)io).getUsersForEvent(TestConfiguration.event_id);
            assertNotNull(us);
            assertEquals(us.size(), 5);

            ArrayList<String> exp = new ArrayList<String>();
            exp.add("{\"first\":\"Teresa\",\"last\":\"Torrain\",\"id\":13}");
            exp.add("{\"first\":\"Ed\",\"last\":\"Eggen\",\"id\":16}");
            exp.add("{\"first\":\"Maynard\",\"last\":\"Magnusson\",\"id\":30}");
            exp.add("{\"first\":\"Adelaida\",\"last\":\"Ariza\",\"id\":45}");
            exp.add("{\"first\":\"Hildegarde\",\"last\":\"Hellman\",\"id\":50}");
            String response,expected;
            for(int i=0;i<us.size();i++) {
                expected = exp.get(i);
                response = us.get(i).toJson();
                TestConfiguration.printout(response,expected,"test_getUsersForEvent");
                assertEquals(expected,response);
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_getUserIdsForEventInRole() {

        // GUEST
        ArrayList<Integer> ids = ((UserEventIO)io).getUserIdsForEventInRole(TestConfiguration.event_id, Permissions.Role.GUEST);
        assertNotNull(ids);
        assertEquals(ids.size(),4);
        Integer [] exp = {16,30,45,50};
        for(int i=0;i<ids.size();i++)
            assertEquals(ids.get(i), exp[i]);

        // OWNER
        ids = ((UserEventIO)io).getUserIdsForEventInRole(TestConfiguration.event_id, Permissions.Role.OWNER);
        assertNotNull(ids);
        assertEquals(ids.size(),1);
        assertEquals(13,(int) ids.get(0));

    }

}
