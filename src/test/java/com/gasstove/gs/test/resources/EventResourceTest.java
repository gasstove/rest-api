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
        System.out.println(response);
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

    @Test
    public void testInsertDeleteEvent(){
        try {
            // create an event
//            String eventstr = "name=dahlgrendf&openDate=Wed%20Dec%2010%201969%2009%3A05%3A32%20GMT-0800%20(PST)&closeDate=Wed%20Dec%2010%201969%2009%3A05%3A32%20GMT-0800%20(PST)&doGuestList=0&doRequestAccept=0&doOpenToPublic=0";

            String eventstr = "name=dahlgrendfg&openDate=Wed%20Dec%2010%201969%2009%3A05%3A32%20GMT-0800%20(PST)&closeDate=Wed%20Dec%2010%201969%2009%3A05%3A32%20GMT-0800%20(PST)&joinInvitation=0&joinAllowByAccept=0&joinAllowAuto=0";

//            String eventstr = "{\"openDate\":\"1969-12-31T16:00:00.000-08:00\",\"closeDate\":\"1969-12-31T16:00:00.001-08:00\",\"joinInvitation\":false,\"joinAllowByAccept\":false,\"joinAllowAuto\":false,\"id\":-1}";


            Event event = new Event(eventstr);

//            Event event = new Event();
//            event.setName("test");
//            event.setOpenDate(new Time(0));
//            event.setCloseDate(new Time(1));
//            event.setJoinAllowAuto(false);
//            event.setJoinAllowByAccept(false);
//            event.setJoinInvitation(false);

            System.out.println(event.toJson());



            // insert it
//            EventResource er = new EventResource();
//            er.insertEvent(event.toJson());
//
//
//            HttpURLConnection conn = TestConfiguration.sendRequest("/events/", "POST", event.toJson());
//            String responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");
//
//            Response response = (new Gson()).fromJson(responseJSON,Response.class);
//
//            System.out.println("after insert: " + response.resource);
//
//            // delete the inserted event
//            Event insertevent = new Event(response.resource);
//
//            System.out.println("insertevent");
//            System.out.println(insertevent);

//            conn = TestConfiguration.sendRequest("/events/" + insertevent.getId(), "DELETE", "");
//            responseJSON = IOUtils.toString(conn.getInputStream(), "UTF-8");

//            System.out.println("after delete: " + responseJSON);


        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }


    }

}
