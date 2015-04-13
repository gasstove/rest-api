package com.gasstove.gs.test.resources;

import com.gasstove.gs.test.util.TestConfiguration;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.net.HttpURLConnection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests methods for Network Resource (reading and hopefully writing) to/from a database.
 *
 * Uses RunWith and Concurrent annotation to run tests concurrently to test multi-threading using
 * jeeunit package: https://code.google.com/p/jeeunit/wiki/TestingConcurrently
 *
 * @author mnjuhn
 */
public class MediaResourceTest {
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
    public MediaResourceTest() throws Exception {
    }

//    @Before
//    public void setup() {
//        // Setup server request
//        System.out.println("Testing Image Resource");
//    }

    @Test
    public void testGetMedias() {

        try {
            // Hit URL to get all networks and save response text
            HttpURLConnection conn =
                    TestConfiguration.sendRequest("/medias/", "GET", "");

            this.responseStatus = conn.getResponseMessage();
            this.responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");

            // check to ensure we get ok message for response and that it contains a network name and description
            assertEquals(expectedResponseStatus, this.responseStatus);

            System.out.println(this.responseJSON);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

//    @Test
//    public void testGetMedia() {
//
//        try {
//            // Hit URL to get all networks and save response text
//            HttpURLConnection conn =
//                    TestConfiguration.sendRequest("/medias/7295", "GET", "");
//
//            this.responseStatus = conn.getResponseMessage();
//            this.responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");
//
//            // check to ensure we get ok message for response and that it contains a network name and description
//            assertEquals(expectedResponseStatus, this.responseStatus);
//
//            assertTrue(this.responseJSON.length() > 0);
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//
//    }

}
