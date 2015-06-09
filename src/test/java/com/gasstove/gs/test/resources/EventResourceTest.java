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
        String expected = "[{\"name\":\"leakier\",\"ownerId\":33,\"openDate\":\"1969-12-13T15:56:16.814-08:00\",\"closeDate\":\"1969-12-13T15:56:16.814-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":1},{\"name\":\"herolike\",\"ownerId\":25,\"openDate\":\"1969-12-28T15:45:24.737-08:00\",\"closeDate\":\"1969-12-28T15:45:24.737-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":2},{\"name\":\"redisbursement\",\"ownerId\":43,\"openDate\":\"1969-12-23T10:26:27.448-08:00\",\"closeDate\":\"1969-12-23T10:26:27.448-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":3},{\"name\":\"obedientiary\",\"ownerId\":35,\"openDate\":\"1969-12-15T07:50:47.613-08:00\",\"closeDate\":\"1969-12-15T07:50:47.613-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":4},{\"name\":\"donalda\",\"ownerId\":15,\"openDate\":\"1970-01-09T02:49:19.889-08:00\",\"closeDate\":\"1970-01-09T02:49:19.889-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":5},{\"name\":\"theistic\",\"ownerId\":47,\"openDate\":\"1970-01-16T15:03:58.831-08:00\",\"closeDate\":\"1970-01-16T15:03:58.831-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":6},{\"name\":\"holt\",\"ownerId\":41,\"openDate\":\"1970-01-10T08:45:40.880-08:00\",\"closeDate\":\"1970-01-10T08:45:40.880-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":7},{\"name\":\"dubiety\",\"ownerId\":13,\"openDate\":\"1970-01-14T04:56:53.100-08:00\",\"closeDate\":\"1970-01-14T04:56:53.100-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":8},{\"name\":\"dahlgren\",\"ownerId\":23,\"openDate\":\"1969-12-10T11:02:21.533-08:00\",\"closeDate\":\"1969-12-10T11:02:21.533-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":9},{\"name\":\"heteropteran\",\"ownerId\":10,\"openDate\":\"1970-01-02T13:29:20.559-08:00\",\"closeDate\":\"1970-01-02T13:29:20.559-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":10}]";
//        System.out.println("test_getEventsBasicInfo");
//        System.out.println(response);

        assertTrue(response.equals(expected));
    }

    @Test
    public void test_getEventBasicInfo() {
        String response = er.getEventBasicInfo(TestConfiguration.event_id.toString());
        String expected = "{\"name\":\"leakier\",\"ownerId\":33,\"openDate\":\"1969-12-13T15:56:16.814-08:00\",\"closeDate\":\"1969-12-13T15:56:16.814-08:00\",\"joinInvitation\":true,\"joinAllowByAccept\":true,\"joinAllowAuto\":true,\"id\":1}";
//        System.out.println("test_getEventBasicInfo");
//        System.out.println(response);
        assertTrue(response.equals(expected));
    }

    @Test
    public void test_getEventsForUser() {
        String response = er.getEventsForUser(TestConfiguration.user_id.toString());
        String expected = "[{\"name\":\"holt\",\"ownerId\":41,\"openDate\":\"1970-01-10T08:45:40.880-08:00\",\"closeDate\":\"1970-01-10T08:45:40.880-08:00\",\"id\":7}]";
//        System.out.println("test_getEventsForUser");
//        System.out.println(response);
        assertTrue(response.equals(expected));
    }

    @Ignore
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
