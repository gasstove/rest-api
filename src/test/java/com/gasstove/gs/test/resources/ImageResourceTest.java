package com.gasstove.gs.test.resources;

import com.gasstove.gs.test.util.TestConfiguration;
import com.googlecode.jeeunit.concurrent.Concurrent;
import com.googlecode.jeeunit.concurrent.ConcurrentParameterized;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
public class ImageResourceTest {
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
    public ImageResourceTest() throws Exception {
    }


    /**
     * A fake network is set up before each test.
     */
    @Before
    public void setup() {
        // Setup server request
        System.out.println("Testing Image Resource");
    }


    /**
     * This tests the get networks method. The method should return a list of networks.
     * We don't bother parsing back the response from JSON in this case.
     */
    @Test
    public void testGetImages() {

        try {
            // Hit URL to get all networks and save response text
            HttpURLConnection conn =
                    TestConfiguration.sendRequest("/images/", "GET", "");

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


}
