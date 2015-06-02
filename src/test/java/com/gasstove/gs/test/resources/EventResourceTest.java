package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.resources.EventResource;
import com.gasstove.gs.resources.Response;
import com.gasstove.gs.test.util.TestConfiguration;
import com.gasstove.gs.test.util.TestDefaults;
import com.gasstove.gs.util.Time;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;

import java.net.HttpURLConnection;

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

//    //  Tests that use the http service .........................................................
//
//    @Test
//    public void testGetEventsHttp() {
//
//        try {
//            // Hit URL to get all networks and save response text
//            HttpURLConnection conn =
//                    TestConfiguration.sendRequest("/events/", "GET", "");
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
//    public void testGetEventHttp() {
//
//        try {
//            // Hit URL to get all networks and save response text
//            HttpURLConnection conn = TestConfiguration.sendRequest("/events/"+ TestDefaults.event_id, "GET", "");
//
//            String responseStatus = conn.getResponseMessage();
//            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");
//
//            // check to ensure we get ok message for response and that it contains a network name and description
//            assertEquals(TestDefaults.expectedResponseStatus, responseStatus);
//            assertTrue(responseJSON.length() > 0);
//
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//
//    }

    //  post tests .........................................................

    @Ignore
    @Test
    public void testInsertDeleteEvent(){
        try {

            // create an event
            Event event = new Event();
            event.setName("testevent");
            event.setOpenDate(new Time(0));
            event.setCloseDate(new Time(0));
            event.setJoinAllowAuto(false);
            event.setJoinAllowByAccept(false);
            event.setJoinInvitation(false);
            event.setOwnerId(1);

            // insert it
            EventResource er = new EventResource();
            String responseStr = er.insertEvent(event.toJson());

            // get it
            Response response = (new Gson()).fromJson(responseStr,Response.class);

            Event return_event = new Event(response.resource);

            // delete the inserted event
            responseStr = er.deleteEvent(String.format("%d",return_event.getId()));

            assertTrue(responseStr.contains("Event successfully deleted"));


        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }


    }

}
