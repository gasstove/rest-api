package com.gasstove.gs.test.resources;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.models.UserEvent;
import com.gasstove.gs.resources.UserEventResource;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Util;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by gomes on 6/13/15.
 */
public class UserEventResourceTest extends AbstractResourceTest {

    File testDB = new File(Configuration.getDBLink());
    File testDBBackup = new File("src/main/resources/gasstoveTest_backup.db");

    public UserEventResourceTest(){
        this.clath = UserEvent.class;
        this.resource = new UserEventResource(Configuration.getDB());
        this.getAll_exp = "[{\"userId\":13,\"eventId\":1,\"role\":\"OWNER\",\"id\":1},{\"userId\":16,\"eventId\":1,\"role\":\"GUEST\",\"id\":2},{\"userId\":45,\"eventId\":1,\"role\":\"GUEST\",\"id\":3},{\"userId\":30,\"eventId\":1,\"role\":\"GUEST\",\"id\":4},{\"userId\":50,\"eventId\":1,\"role\":\"GUEST\",\"id\":5},{\"userId\":28,\"eventId\":2,\"role\":\"OWNER\",\"id\":6},{\"userId\":6,\"eventId\":2,\"role\":\"GUEST\",\"id\":7},{\"userId\":19,\"eventId\":2,\"role\":\"GUEST\",\"id\":8},{\"userId\":1,\"eventId\":2,\"role\":\"GUEST\",\"id\":9},{\"userId\":44,\"eventId\":2,\"role\":\"GUEST\",\"id\":10},{\"userId\":22,\"eventId\":3,\"role\":\"OWNER\",\"id\":11},{\"userId\":46,\"eventId\":3,\"role\":\"GUEST\",\"id\":12},{\"userId\":43,\"eventId\":3,\"role\":\"GUEST\",\"id\":13},{\"userId\":25,\"eventId\":3,\"role\":\"GUEST\",\"id\":14},{\"userId\":29,\"eventId\":4,\"role\":\"OWNER\",\"id\":15},{\"userId\":46,\"eventId\":4,\"role\":\"GUEST\",\"id\":16},{\"userId\":12,\"eventId\":4,\"role\":\"GUEST\",\"id\":17},{\"userId\":7,\"eventId\":4,\"role\":\"GUEST\",\"id\":18},{\"userId\":23,\"eventId\":4,\"role\":\"GUEST\",\"id\":19},{\"userId\":2,\"eventId\":4,\"role\":\"GUEST\",\"id\":20},{\"userId\":50,\"eventId\":4,\"role\":\"GUEST\",\"id\":21},{\"userId\":26,\"eventId\":4,\"role\":\"GUEST\",\"id\":22},{\"userId\":28,\"eventId\":5,\"role\":\"OWNER\",\"id\":23},{\"userId\":50,\"eventId\":5,\"role\":\"GUEST\",\"id\":24},{\"userId\":1,\"eventId\":5,\"role\":\"GUEST\",\"id\":25},{\"userId\":12,\"eventId\":5,\"role\":\"GUEST\",\"id\":26},{\"userId\":7,\"eventId\":5,\"role\":\"GUEST\",\"id\":27},{\"userId\":37,\"eventId\":5,\"role\":\"GUEST\",\"id\":28},{\"userId\":45,\"eventId\":5,\"role\":\"GUEST\",\"id\":29},{\"userId\":4,\"eventId\":5,\"role\":\"GUEST\",\"id\":30},{\"userId\":44,\"eventId\":5,\"role\":\"GUEST\",\"id\":31},{\"userId\":47,\"eventId\":5,\"role\":\"GUEST\",\"id\":32},{\"userId\":30,\"eventId\":5,\"role\":\"GUEST\",\"id\":33},{\"userId\":13,\"eventId\":5,\"role\":\"GUEST\",\"id\":34},{\"userId\":21,\"eventId\":5,\"role\":\"GUEST\",\"id\":35},{\"userId\":46,\"eventId\":5,\"role\":\"GUEST\",\"id\":36},{\"userId\":23,\"eventId\":5,\"role\":\"GUEST\",\"id\":37},{\"userId\":42,\"eventId\":5,\"role\":\"GUEST\",\"id\":38},{\"userId\":32,\"eventId\":5,\"role\":\"GUEST\",\"id\":39},{\"userId\":33,\"eventId\":5,\"role\":\"GUEST\",\"id\":40},{\"userId\":14,\"eventId\":5,\"role\":\"GUEST\",\"id\":41},{\"userId\":15,\"eventId\":5,\"role\":\"GUEST\",\"id\":42},{\"userId\":41,\"eventId\":5,\"role\":\"GUEST\",\"id\":43},{\"userId\":49,\"eventId\":5,\"role\":\"GUEST\",\"id\":44},{\"userId\":39,\"eventId\":5,\"role\":\"GUEST\",\"id\":45},{\"userId\":11,\"eventId\":5,\"role\":\"GUEST\",\"id\":46},{\"userId\":26,\"eventId\":5,\"role\":\"GUEST\",\"id\":47},{\"userId\":22,\"eventId\":5,\"role\":\"GUEST\",\"id\":48},{\"userId\":2,\"eventId\":5,\"role\":\"GUEST\",\"id\":49},{\"userId\":38,\"eventId\":5,\"role\":\"GUEST\",\"id\":50},{\"userId\":36,\"eventId\":6,\"role\":\"OWNER\",\"id\":51},{\"userId\":8,\"eventId\":6,\"role\":\"GUEST\",\"id\":52},{\"userId\":44,\"eventId\":6,\"role\":\"GUEST\",\"id\":53},{\"userId\":20,\"eventId\":6,\"role\":\"GUEST\",\"id\":54},{\"userId\":24,\"eventId\":6,\"role\":\"GUEST\",\"id\":55},{\"userId\":21,\"eventId\":6,\"role\":\"GUEST\",\"id\":56},{\"userId\":22,\"eventId\":6,\"role\":\"GUEST\",\"id\":57},{\"userId\":10,\"eventId\":6,\"role\":\"GUEST\",\"id\":58},{\"userId\":9,\"eventId\":6,\"role\":\"GUEST\",\"id\":59},{\"userId\":37,\"eventId\":6,\"role\":\"GUEST\",\"id\":60},{\"userId\":32,\"eventId\":6,\"role\":\"GUEST\",\"id\":61},{\"userId\":1,\"eventId\":6,\"role\":\"GUEST\",\"id\":62},{\"userId\":17,\"eventId\":6,\"role\":\"GUEST\",\"id\":63},{\"userId\":38,\"eventId\":6,\"role\":\"GUEST\",\"id\":64},{\"userId\":26,\"eventId\":6,\"role\":\"GUEST\",\"id\":65},{\"userId\":25,\"eventId\":6,\"role\":\"GUEST\",\"id\":66},{\"userId\":4,\"eventId\":6,\"role\":\"GUEST\",\"id\":67},{\"userId\":33,\"eventId\":6,\"role\":\"GUEST\",\"id\":68},{\"userId\":35,\"eventId\":6,\"role\":\"GUEST\",\"id\":69},{\"userId\":48,\"eventId\":6,\"role\":\"GUEST\",\"id\":70},{\"userId\":28,\"eventId\":6,\"role\":\"GUEST\",\"id\":71},{\"userId\":49,\"eventId\":6,\"role\":\"GUEST\",\"id\":72},{\"userId\":19,\"eventId\":6,\"role\":\"GUEST\",\"id\":73},{\"userId\":2,\"eventId\":6,\"role\":\"GUEST\",\"id\":74},{\"userId\":3,\"eventId\":6,\"role\":\"GUEST\",\"id\":75},{\"userId\":7,\"eventId\":6,\"role\":\"GUEST\",\"id\":76},{\"userId\":11,\"eventId\":6,\"role\":\"GUEST\",\"id\":77},{\"userId\":29,\"eventId\":6,\"role\":\"GUEST\",\"id\":78},{\"userId\":12,\"eventId\":7,\"role\":\"OWNER\",\"id\":79},{\"userId\":1,\"eventId\":7,\"role\":\"GUEST\",\"id\":80},{\"userId\":23,\"eventId\":7,\"role\":\"GUEST\",\"id\":81},{\"userId\":1,\"eventId\":8,\"role\":\"OWNER\",\"id\":82},{\"userId\":6,\"eventId\":8,\"role\":\"GUEST\",\"id\":83},{\"userId\":49,\"eventId\":8,\"role\":\"GUEST\",\"id\":84},{\"userId\":20,\"eventId\":8,\"role\":\"GUEST\",\"id\":85},{\"userId\":14,\"eventId\":8,\"role\":\"GUEST\",\"id\":86},{\"userId\":27,\"eventId\":8,\"role\":\"GUEST\",\"id\":87},{\"userId\":15,\"eventId\":8,\"role\":\"GUEST\",\"id\":88},{\"userId\":43,\"eventId\":8,\"role\":\"GUEST\",\"id\":89},{\"userId\":9,\"eventId\":8,\"role\":\"GUEST\",\"id\":90},{\"userId\":21,\"eventId\":8,\"role\":\"GUEST\",\"id\":91},{\"userId\":50,\"eventId\":9,\"role\":\"OWNER\",\"id\":92},{\"userId\":15,\"eventId\":9,\"role\":\"GUEST\",\"id\":93},{\"userId\":26,\"eventId\":9,\"role\":\"GUEST\",\"id\":94},{\"userId\":5,\"eventId\":9,\"role\":\"GUEST\",\"id\":95},{\"userId\":14,\"eventId\":9,\"role\":\"GUEST\",\"id\":96},{\"userId\":43,\"eventId\":9,\"role\":\"GUEST\",\"id\":97},{\"userId\":28,\"eventId\":9,\"role\":\"GUEST\",\"id\":98},{\"userId\":30,\"eventId\":9,\"role\":\"GUEST\",\"id\":99},{\"userId\":20,\"eventId\":9,\"role\":\"GUEST\",\"id\":100},{\"userId\":24,\"eventId\":9,\"role\":\"GUEST\",\"id\":101},{\"userId\":3,\"eventId\":9,\"role\":\"GUEST\",\"id\":102},{\"userId\":29,\"eventId\":9,\"role\":\"GUEST\",\"id\":103},{\"userId\":40,\"eventId\":9,\"role\":\"GUEST\",\"id\":104},{\"userId\":44,\"eventId\":9,\"role\":\"GUEST\",\"id\":105},{\"userId\":25,\"eventId\":9,\"role\":\"GUEST\",\"id\":106},{\"userId\":48,\"eventId\":9,\"role\":\"GUEST\",\"id\":107},{\"userId\":41,\"eventId\":9,\"role\":\"GUEST\",\"id\":108},{\"userId\":7,\"eventId\":9,\"role\":\"GUEST\",\"id\":109},{\"userId\":33,\"eventId\":9,\"role\":\"GUEST\",\"id\":110},{\"userId\":16,\"eventId\":9,\"role\":\"GUEST\",\"id\":111},{\"userId\":1,\"eventId\":9,\"role\":\"GUEST\",\"id\":112},{\"userId\":9,\"eventId\":9,\"role\":\"GUEST\",\"id\":113},{\"userId\":21,\"eventId\":9,\"role\":\"GUEST\",\"id\":114},{\"userId\":6,\"eventId\":9,\"role\":\"GUEST\",\"id\":115},{\"userId\":23,\"eventId\":9,\"role\":\"GUEST\",\"id\":116},{\"userId\":13,\"eventId\":9,\"role\":\"GUEST\",\"id\":117},{\"userId\":22,\"eventId\":9,\"role\":\"GUEST\",\"id\":118},{\"userId\":31,\"eventId\":9,\"role\":\"GUEST\",\"id\":119},{\"userId\":12,\"eventId\":9,\"role\":\"GUEST\",\"id\":120},{\"userId\":11,\"eventId\":9,\"role\":\"GUEST\",\"id\":121},{\"userId\":39,\"eventId\":10,\"role\":\"OWNER\",\"id\":122},{\"userId\":4,\"eventId\":10,\"role\":\"GUEST\",\"id\":123},{\"userId\":11,\"eventId\":10,\"role\":\"GUEST\",\"id\":124},{\"userId\":13,\"eventId\":10,\"role\":\"GUEST\",\"id\":125},{\"userId\":18,\"eventId\":10,\"role\":\"GUEST\",\"id\":126},{\"userId\":10,\"eventId\":10,\"role\":\"GUEST\",\"id\":127},{\"userId\":32,\"eventId\":10,\"role\":\"GUEST\",\"id\":128},{\"userId\":9,\"eventId\":10,\"role\":\"GUEST\",\"id\":129},{\"userId\":42,\"eventId\":10,\"role\":\"GUEST\",\"id\":130},{\"userId\":26,\"eventId\":10,\"role\":\"GUEST\",\"id\":131},{\"userId\":2,\"eventId\":10,\"role\":\"GUEST\",\"id\":132},{\"userId\":27,\"eventId\":10,\"role\":\"GUEST\",\"id\":133},{\"userId\":35,\"eventId\":10,\"role\":\"GUEST\",\"id\":134},{\"userId\":19,\"eventId\":10,\"role\":\"GUEST\",\"id\":135},{\"userId\":47,\"eventId\":10,\"role\":\"GUEST\",\"id\":136},{\"userId\":38,\"eventId\":10,\"role\":\"GUEST\",\"id\":137},{\"userId\":23,\"eventId\":10,\"role\":\"GUEST\",\"id\":138},{\"userId\":37,\"eventId\":10,\"role\":\"GUEST\",\"id\":139},{\"userId\":16,\"eventId\":10,\"role\":\"GUEST\",\"id\":140},{\"userId\":8,\"eventId\":10,\"role\":\"GUEST\",\"id\":141},{\"userId\":49,\"eventId\":10,\"role\":\"GUEST\",\"id\":142},{\"userId\":33,\"eventId\":10,\"role\":\"GUEST\",\"id\":143},{\"userId\":3,\"eventId\":10,\"role\":\"GUEST\",\"id\":144},{\"userId\":40,\"eventId\":10,\"role\":\"GUEST\",\"id\":145},{\"userId\":44,\"eventId\":10,\"role\":\"GUEST\",\"id\":146},{\"userId\":5,\"eventId\":10,\"role\":\"GUEST\",\"id\":147},{\"userId\":15,\"eventId\":10,\"role\":\"GUEST\",\"id\":148},{\"userId\":6,\"eventId\":10,\"role\":\"GUEST\",\"id\":149}]";
        this.getForId_exp = "{\"userId\":13,\"eventId\":1,\"role\":\"OWNER\",\"id\":1}";
        this.use_id = TestConfiguration.userevent_id.toString();
    }

    @Test
    public void test_getUsersForEvent() {
        System.out.println("test_getUsersForEvent");
        String response = ((UserEventResource)resource).getUsersForEvent(TestConfiguration.event_id.toString());
        String expected = "[{\"first\":\"Teresa\",\"last\":\"Torrain\",\"id\":13},{\"first\":\"Ed\",\"last\":\"Eggen\",\"id\":16},{\"first\":\"Maynard\",\"last\":\"Magnusson\",\"id\":30},{\"first\":\"Adelaida\",\"last\":\"Ariza\",\"id\":45},{\"first\":\"Hildegarde\",\"last\":\"Hellman\",\"id\":50}]";
        TestConfiguration.printout(response, expected, "test_getUsersForEvent");
        assertTrue(response.equals(expected));
    }

    @Test
    public void test_getEventsForUser() {
        System.out.println("test_getEventsForUser");
        String response = ((UserEventResource)resource).getEventsForUser(TestConfiguration.user_id.toString());
        String expected = "[{\"name\":\"theistic\",\"openDate\":\"1970-01-04T02:26:02.926-08:00\",\"closeDate\":\"1970-01-04T02:26:02.926-08:00\",\"ownerId\":36,\"id\":6}]";
        TestConfiguration.printout(response,expected,"test_getEventsForUser");
        assertEquals(response,expected);
    }

    @Test
    public void test_addUsersToEvent() {

        System.out.println("test_addUsersToEvent");

        // make a copy of the database. This is the easiest way to revert the
        // changes that are made by this test.
        try {
            FileUtils.copyFile(testDB,testDBBackup);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

        UserEventResource uer = (UserEventResource) resource;
        String event_id = TestConfiguration.event_id.toString();

        // get guests in event
        String users_json = uer.getUsersForEvent(event_id);
        Type type = new TypeToken<ArrayList<User>>(){}.getType();
        ArrayList<User> users = Util.getGson().fromJson(users_json,type);

        // remove a user
        User removed_user = users.get(users.size()-1);
        users.remove(removed_user);

        // add user
        UserIO uio = new UserIO(Configuration.getDB());
        User added_user =  uio.getWithId(1);
        users.add(added_user);
        uio.close();

        // update table
        uer.cloberGuestsInEvent(event_id, Util.getGson().toJson(users));

        // get guests in event
        String users_json2 = uer.getUsersForEvent(event_id);
        ArrayList<User> users2 = Util.getGson().fromJson(users_json2,type);

        // check removed_user is not there and added_user is there
        boolean found_added = false;
        for(User u : users2) {
            found_added |= u.getId()==added_user.getId();
            assertFalse(u.getId() == removed_user.getId());
        }
        assertTrue(found_added);

        // copy database back and delete backup
        try {
            FileUtils.copyFile(testDBBackup,testDB);
            FileUtils.forceDelete(testDBBackup);
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }

    }


}
