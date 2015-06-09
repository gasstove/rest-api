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
        String expected = "[{\"first\":\"Kareem\",\"last\":\"Kai\",\"id\":1},{\"first\":\"Ceola\",\"last\":\"Cue\",\"id\":2},{\"first\":\"Amalia\",\"last\":\"Adames\",\"id\":3},{\"first\":\"Sung\",\"last\":\"Sebesta\",\"id\":4},{\"first\":\"Shantay\",\"last\":\"Schaaf\",\"id\":5},{\"first\":\"Tien\",\"last\":\"Tallmadge\",\"id\":6},{\"first\":\"Yon\",\"last\":\"Yerby\",\"id\":7},{\"first\":\"Candida\",\"last\":\"Cornell\",\"id\":8},{\"first\":\"Catrice\",\"last\":\"Chapple\",\"id\":9},{\"first\":\"Florentina\",\"last\":\"Foret\",\"id\":10},{\"first\":\"Sylvia\",\"last\":\"Shehane\",\"id\":11},{\"first\":\"Cornelius\",\"last\":\"Chau\",\"id\":12},{\"first\":\"Teresa\",\"last\":\"Torrain\",\"id\":13},{\"first\":\"Emery\",\"last\":\"Ebron\",\"id\":14},{\"first\":\"Jeraldine\",\"last\":\"Jolin\",\"id\":15},{\"first\":\"Ed\",\"last\":\"Eggen\",\"id\":16},{\"first\":\"Karyn\",\"last\":\"Kluth\",\"id\":17},{\"first\":\"Dorcas\",\"last\":\"Desch\",\"id\":18},{\"first\":\"Cleveland\",\"last\":\"Chevere\",\"id\":19},{\"first\":\"Beatrice\",\"last\":\"Bernhardt\",\"id\":20},{\"first\":\"Takako\",\"last\":\"Troxel\",\"id\":21},{\"first\":\"Elroy\",\"last\":\"Eldredge\",\"id\":22},{\"first\":\"Leila\",\"last\":\"Longstreet\",\"id\":23},{\"first\":\"Georgia\",\"last\":\"Grimsley\",\"id\":24},{\"first\":\"Alan\",\"last\":\"Artis\",\"id\":25},{\"first\":\"Deetta\",\"last\":\"Daring\",\"id\":26},{\"first\":\"Nicol\",\"last\":\"Nay\",\"id\":27},{\"first\":\"Emilie\",\"last\":\"Eccles\",\"id\":28},{\"first\":\"Stepanie\",\"last\":\"Salters\",\"id\":29},{\"first\":\"Maynard\",\"last\":\"Magnusson\",\"id\":30},{\"first\":\"Cherrie\",\"last\":\"Cotner\",\"id\":31},{\"first\":\"Lincoln\",\"last\":\"Lacourse\",\"id\":32},{\"first\":\"Launa\",\"last\":\"Loud\",\"id\":33},{\"first\":\"Caroline\",\"last\":\"Crooms\",\"id\":34},{\"first\":\"Mason\",\"last\":\"Michalak\",\"id\":35},{\"first\":\"Janice\",\"last\":\"James\",\"id\":36},{\"first\":\"Dirk\",\"last\":\"Dejesus\",\"id\":37},{\"first\":\"Herbert\",\"last\":\"Homan\",\"id\":38},{\"first\":\"Nicola\",\"last\":\"Narcisse\",\"id\":39},{\"first\":\"Creola\",\"last\":\"Cropp\",\"id\":40},{\"first\":\"Kate\",\"last\":\"Knerr\",\"id\":41},{\"first\":\"Peggie\",\"last\":\"Pyle\",\"id\":42},{\"first\":\"Cicely\",\"last\":\"Cromer\",\"id\":43},{\"first\":\"Cedrick\",\"last\":\"Cacho\",\"id\":44},{\"first\":\"Adelaida\",\"last\":\"Ariza\",\"id\":45},{\"first\":\"Bernardo\",\"last\":\"Brigmond\",\"id\":46},{\"first\":\"Earnestine\",\"last\":\"Ecker\",\"id\":47},{\"first\":\"September\",\"last\":\"Sussman\",\"id\":48},{\"first\":\"Genia\",\"last\":\"Gast\",\"id\":49},{\"first\":\"Hildegarde\",\"last\":\"Hellman\",\"id\":50}]";
        assertTrue(response.equals(expected));
    }

    @Test
    public void test_getUserBasicInfo() {
        String response = ur.getUserBasicInfo(TestConfiguration.user_id.toString());
        String expected = "{\"first\":\"Janice\",\"last\":\"James\",\"id\":36}";
        assertTrue(response.equals(expected));
    }

    @Ignore
    @Test
    public void test_insertdeleteUser() {

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

    @Test
    public void test_getUsersForEvent() {
        String response = ur.getUsersForEvent(TestConfiguration.event_id.toString());
        String expected = "[{\"first\":\"Launa\",\"last\":\"Loud\",\"id\":33},{\"first\":\"Catrice\",\"last\":\"Chapple\",\"id\":9},{\"first\":\"Takako\",\"last\":\"Troxel\",\"id\":21},{\"first\":\"Cornelius\",\"last\":\"Chau\",\"id\":12},{\"first\":\"Ceola\",\"last\":\"Cue\",\"id\":2}]";
        assertTrue(response.equals(expected));
    }

    @Ignore
    @Test
    public void test_addUsersToEvent() {

        // create new event


        // add users


        // delete event



        String userarray = "";



        ur.addUsersToEvent(TestConfiguration.event_id.toString(),userarray);



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
