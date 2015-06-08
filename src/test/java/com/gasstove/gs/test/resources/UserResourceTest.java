package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.models.User;
import com.gasstove.gs.resources.Response;
import com.gasstove.gs.resources.UserResource;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Time;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.junit.Assert.*;

/**
 * Tests methods for Network Resource (reading and hopefully writing) to/from a database.
 *
 * Uses RunWith and Concurrent annotation to run tests concurrently to test multi-threading using
 * jeeunit package: https://code.google.com/p/jeeunit/wiki/TestingConcurrently
 *
 * @author mnjuhn
 */
public class UserResourceTest {

    UserResource ur = new UserResource(TestConfiguration.db);

    //  Tests for java classes ....................................................................

    @Test
    public void test_getUsersBasicInfo() {
        String response = ur.getUsersBasicInfo();
        String expected = "[{\"first\":\"Maynard\",\"last\":\"Magnusson\",\"id\":1},{\"first\":\"Earnestine\",\"last\":\"Ecker\",\"id\":2},{\"first\":\"Shantay\",\"last\":\"Schaaf\",\"id\":3},{\"first\":\"Creola\",\"last\":\"Cropp\",\"id\":4},{\"first\":\"Yon\",\"last\":\"Yerby\",\"id\":5},{\"first\":\"Georgia\",\"last\":\"Grimsley\",\"id\":6},{\"first\":\"Ed\",\"last\":\"Eggen\",\"id\":7},{\"first\":\"Cleveland\",\"last\":\"Chevere\",\"id\":8},{\"first\":\"Cornelius\",\"last\":\"Chau\",\"id\":9},{\"first\":\"Caroline\",\"last\":\"Crooms\",\"id\":10},{\"first\":\"Janice\",\"last\":\"James\",\"id\":11},{\"first\":\"September\",\"last\":\"Sussman\",\"id\":12},{\"first\":\"Dirk\",\"last\":\"Dejesus\",\"id\":13},{\"first\":\"Cherrie\",\"last\":\"Cotner\",\"id\":14},{\"first\":\"Beatrice\",\"last\":\"Bernhardt\",\"id\":15},{\"first\":\"Kareem\",\"last\":\"Kai\",\"id\":16},{\"first\":\"Kate\",\"last\":\"Knerr\",\"id\":17},{\"first\":\"Cicely\",\"last\":\"Cromer\",\"id\":18},{\"first\":\"Leila\",\"last\":\"Longstreet\",\"id\":19},{\"first\":\"Mason\",\"last\":\"Michalak\",\"id\":20},{\"first\":\"Peggie\",\"last\":\"Pyle\",\"id\":21},{\"first\":\"Lincoln\",\"last\":\"Lacourse\",\"id\":22},{\"first\":\"Hildegarde\",\"last\":\"Hellman\",\"id\":23},{\"first\":\"Launa\",\"last\":\"Loud\",\"id\":24},{\"first\":\"Elroy\",\"last\":\"Eldredge\",\"id\":25},{\"first\":\"Dorcas\",\"last\":\"Desch\",\"id\":26},{\"first\":\"Nicol\",\"last\":\"Nay\",\"id\":27},{\"first\":\"Stepanie\",\"last\":\"Salters\",\"id\":28},{\"first\":\"Emilie\",\"last\":\"Eccles\",\"id\":29},{\"first\":\"Amalia\",\"last\":\"Adames\",\"id\":30},{\"first\":\"Takako\",\"last\":\"Troxel\",\"id\":31},{\"first\":\"Sylvia\",\"last\":\"Shehane\",\"id\":32},{\"first\":\"Tien\",\"last\":\"Tallmadge\",\"id\":33},{\"first\":\"Deetta\",\"last\":\"Daring\",\"id\":34},{\"first\":\"Candida\",\"last\":\"Cornell\",\"id\":35},{\"first\":\"Karyn\",\"last\":\"Kluth\",\"id\":36},{\"first\":\"Genia\",\"last\":\"Gast\",\"id\":37},{\"first\":\"Jeraldine\",\"last\":\"Jolin\",\"id\":38},{\"first\":\"Nicola\",\"last\":\"Narcisse\",\"id\":39},{\"first\":\"Emery\",\"last\":\"Ebron\",\"id\":40},{\"first\":\"Sung\",\"last\":\"Sebesta\",\"id\":41},{\"first\":\"Ceola\",\"last\":\"Cue\",\"id\":42},{\"first\":\"Adelaida\",\"last\":\"Ariza\",\"id\":43},{\"first\":\"Cedrick\",\"last\":\"Cacho\",\"id\":44},{\"first\":\"Alan\",\"last\":\"Artis\",\"id\":45},{\"first\":\"Bernardo\",\"last\":\"Brigmond\",\"id\":46},{\"first\":\"Herbert\",\"last\":\"Homan\",\"id\":47},{\"first\":\"Florentina\",\"last\":\"Foret\",\"id\":48},{\"first\":\"Catrice\",\"last\":\"Chapple\",\"id\":49},{\"first\":\"Teresa\",\"last\":\"Torrain\",\"id\":50}]";
        assertTrue(response.equals(expected));
    }

    @Test
    public void test_getUserBasicInfo() {
        String response = ur.getUserBasicInfo(TestConfiguration.user_id.toString());
        String expected = "{\"first\":\"Karyn\",\"last\":\"Kluth\",\"id\":36}";
        assertTrue(response.equals(expected));
    }

    @Ignore
    @Test
    public void test_insertUser() {

        try {

            // create an event
            User user = new User();
            user.setFirst("XXX");
            user.setLast("YYY");

            // insert it
            String responseStr = ur.insertUser(user.toJson());

            // get it
            Response response = (new Gson()).fromJson(responseStr,Response.class);
            User return_user = new User(response.resource);

            // delete the inserted event
            responseStr = ur.deleteUser(String.format("%d",return_user.getId()));
            assertTrue(responseStr.contains("User successfully deleted"));

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

    @Ignore
    @Test
    public void test_deleteUser() {
    }

    @Ignore
    @Test
    public void test_getUsersForEvent() {
    }

    @Ignore
    @Test
    public void test_addUsersToEvent() {

        try {

            // Hit URL to get all networks and save response text
            HttpURLConnection conn =
                    TestConfiguration.sendRequest("/users/event/5", "POST", "BLABLA");

            String responseStatus = conn.getResponseMessage();
            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");

            // check to ensure we get ok message for response and that it contains a network name and description
//            assertEquals(TestDefaults.expectedResponseStatus,responseStatus);
//            assertTrue(responseJSON.length() > 0);

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }


    }

    //  Tests that use the http service .........................................................

//    @Test
//    public void testGetUsersHttp() {
//
//        try {
//            // Hit URL to get all networks and save response text
//            HttpURLConnection conn =
//                    TestConfiguration.sendRequest("/users/", "GET", "");
//
//            String responseStatus = conn.getResponseMessage();
//            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");
//
//            // check to ensure we get ok message for response and that it contains a network name and description
//            assertEquals(TestDefaults.expectedResponseStatus,responseStatus);
//            assertTrue(responseJSON.length() > 0);
//
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//
//    }
//
//    @Test
//    public void testGetUserHttp() {
//
//        try {
//            // Hit URL to get all networks and save response text
//            HttpURLConnection conn =
//                    TestConfiguration.sendRequest("/users/"+ TestDefaults.user_id, "GET", "");
//
//            String responseStatus = conn.getResponseMessage();
//            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");
//
//            // check to ensure we get ok message for response and that it contains a network name and description
//            assertEquals(TestDefaults.expectedResponseStatus,responseStatus);
//            assertTrue(responseJSON.length() > 0);
//
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//
//    }

}
