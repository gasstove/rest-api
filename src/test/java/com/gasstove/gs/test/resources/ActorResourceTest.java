package com.gasstove.gs.test.resources;

import com.gasstove.gs.test.util.TestConfiguration;
import com.gasstove.gs.test.util.TestDefaults;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
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
public class ActorResourceTest {

    // standard response values per test
    String responseStatus;
    String responseJSON;
    private final String expectedResponseStatus = "OK";
    private final int expectedResponseCode = 200;

    /**
     * The constructor sets up database for so we can set up a Model DataBase Access
     * reader and writer; these are used to insert and delete records directly to
     * facilitate testing of the API.
     *
     * @throws Exception This happens if the database set up has trouble
     */
    public ActorResourceTest() throws Exception {
    }

    /**
     * This tests the getActors resource. The method should return a list of actors.
     *
     */
    @Test
    public void testGetActors() {

        try {
            // Hit URL to get all networks and save response text
            HttpURLConnection conn =
                    TestConfiguration.sendRequest("/actors/", "GET", "");

            this.responseStatus = conn.getResponseMessage();
            this.responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");

            // check to ensure we get ok message for response and that it contains a network name and description
            assertEquals(expectedResponseStatus, this.responseStatus);

            assertTrue(this.responseJSON.length() > 0);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

    /**
     * This tests the get actor method. The method should return an actor
     */
    @Test
    public void testGetActor() {

        try {
            // Hit URL to get all networks and save response text
            HttpURLConnection conn =
                    TestConfiguration.sendRequest("/actors/"+ TestDefaults.actor_id, "GET", "");

            this.responseStatus = conn.getResponseMessage();
            this.responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");

            // check to ensure we get ok message for response and that it contains a network name and description
            assertEquals(expectedResponseStatus, this.responseStatus);

            assertTrue(this.responseJSON.length() > 0);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

}
