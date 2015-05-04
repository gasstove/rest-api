package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.resources.EventResource;
import com.gasstove.gs.test.util.TestConfiguration;
import com.gasstove.gs.test.util.TestDefaults;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests methods for Network Resource (reading and hopefully writing) to/from a database.
 *
 * Uses RunWith and Concurrent annotation to run tests concurrently to test multi-threading using
 * jeeunit package: https://code.google.com/p/jeeunit/wiki/TestingConcurrently
 *
 * @author mnjuhn
 */
public class EventResourceTest {

    //  Tests for java classes ....................................................................

    @Test
    public void testGetEvents() {
        EventResource er = new EventResource();
        String response = er.getEventsBasicInfo();
        assertTrue(response.length() > 0);
    }

    @Test
    public void testGetEvent() {
        EventResource er = new EventResource();
        String response = er.getEventBasicInfo(TestDefaults.event_id.toString());
        assertTrue(response.length() > 0);
    }

    //  Tests that use the http service .........................................................

    @Test
    public void testGetEventsHttp() {

        try {
            // Hit URL to get all networks and save response text
            HttpURLConnection conn =
                    TestConfiguration.sendRequest("/events/", "GET", "");

            String responseStatus = conn.getResponseMessage();
            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");

            // check to ensure we get ok message for response and that it contains a network name and description
            assertEquals(TestDefaults.expectedResponseStatus,responseStatus);
            assertTrue(responseJSON.length() > 0);

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

    @Test
    public void testGetEventHttp() {

        try {
            // Hit URL to get all networks and save response text
            HttpURLConnection conn = TestConfiguration.sendRequest("/events/"+ TestDefaults.event_id, "GET", "");

            String responseStatus = conn.getResponseMessage();
            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");

            // check to ensure we get ok message for response and that it contains a network name and description
            assertEquals(TestDefaults.expectedResponseStatus, responseStatus);
            assertTrue(responseJSON.length() > 0);

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

    //  post tests .........................................................

    @Test
    public void testInsertDeleteEvent(){
        try {

            // create an event
            Event event = new Event();
            event.setName("test");
            event.setOpenDate(new Date(0));
            event.setCloseDate(new Date(1));
            event.setJoinAllowAuto(false);
            event.setJoinAllowByAccept(false);
            event.setJoinInvitation(false);

            // insert
            Gson gson = new Gson();
            HttpURLConnection conn = TestConfiguration.sendRequest("/events/", "POST", gson.toJson(event));


            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");



        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }


    }

}
