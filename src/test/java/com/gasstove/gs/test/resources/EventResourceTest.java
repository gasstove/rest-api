package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.resources.EventResource;
import com.gasstove.gs.resources.Response;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Time;
import com.google.gson.Gson;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertSame;
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

    EventResource er = new EventResource(TestConfiguration.db);

    //  Tests for java classes ....................................................................

    @Test
    public void test_getEventsBasicInfo() {
        String response = er.getEventsBasicInfo();
        String expected = "[{\"name\":\"donalda\",\"ownerId\":23,\"openDate\":\"1970-01-01T04:58:28.270-08:00\",\"closeDate\":\"1970-01-01T04:58:28.270-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":1},{\"name\":\"heteropteran\",\"ownerId\":14,\"openDate\":\"1969-12-27T20:11:17.521-08:00\",\"closeDate\":\"1969-12-27T20:11:17.521-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":2},{\"name\":\"holt\",\"ownerId\":35,\"openDate\":\"1969-12-12T18:59:56.544-08:00\",\"closeDate\":\"1969-12-12T18:59:56.544-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":3},{\"name\":\"dubiety\",\"ownerId\":25,\"openDate\":\"1970-01-13T02:34:07.290-08:00\",\"closeDate\":\"1970-01-13T02:34:07.290-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":4},{\"name\":\"herolike\",\"ownerId\":43,\"openDate\":\"1969-12-13T05:23:31.467-08:00\",\"closeDate\":\"1969-12-13T05:23:31.467-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":5},{\"name\":\"redisbursement\",\"ownerId\":32,\"openDate\":\"1969-12-21T08:04:02.941-08:00\",\"closeDate\":\"1969-12-21T08:04:02.941-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":6},{\"name\":\"dahlgren\",\"ownerId\":35,\"openDate\":\"1969-12-19T04:33:18.348-08:00\",\"closeDate\":\"1969-12-19T04:33:18.348-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":7},{\"name\":\"theistic\",\"ownerId\":43,\"openDate\":\"1970-01-25T05:45:31.359-08:00\",\"closeDate\":\"1970-01-25T05:45:31.359-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":8},{\"name\":\"obedientiary\",\"ownerId\":20,\"openDate\":\"1969-12-29T05:26:32.998-08:00\",\"closeDate\":\"1969-12-29T05:26:32.998-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":9},{\"name\":\"leakier\",\"ownerId\":15,\"openDate\":\"1970-01-06T12:30:28.380-08:00\",\"closeDate\":\"1970-01-06T12:30:28.380-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":10}]";
        assertTrue(response.equals(expected));
    }

    @Test
    public void test_getEventBasicInfo() {
        String response = er.getEventBasicInfo(TestConfiguration.event_id.toString());
        String expected = "{\"name\":\"donalda\",\"ownerId\":23,\"openDate\":\"1970-01-01T04:58:28.270-08:00\",\"closeDate\":\"1970-01-01T04:58:28.270-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":1}";
        assertTrue(response.equals(expected));
    }

    @Test
    public void test_getEventsForUser() {
        String response = er.getEventsForUser(TestConfiguration.user_id.toString());
        String expected = "[{\"name\":\"donalda\",\"ownerId\":23,\"openDate\":\"1970-01-01T04:58:28.270-08:00\",\"closeDate\":\"1970-01-01T04:58:28.270-08:00\",\"id\":1}]";
        assertTrue(response.equals(expected));
    }

    @Test
    public void test_insertdeleteEvent(){
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


}
