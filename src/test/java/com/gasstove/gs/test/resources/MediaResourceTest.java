package com.gasstove.gs.test.resources;

import com.gasstove.gs.resources.MediaResource;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Tests methods for Network Resource (reading and hopefully writing) to/from a database.
 *
 * Uses RunWith and Concurrent annotation to run tests concurrently to test multi-threading using
 * jeeunit package: https://code.google.com/p/jeeunit/wiki/TestingConcurrently
 *
 * @author mnjuhn
 */
public class MediaResourceTest {

    //  Tests for java classes ..................................................................

    @Test
    public void test_getMediasBasicInfo() {
        MediaResource mr = new MediaResource(TestConfiguration.db);
        String responseJSON = mr.getMediasBasicInfo();
        assertTrue(responseJSON.length() > 0);
    }

    @Test
    public void test_getMediaBasicInfo() {
        MediaResource mr = new MediaResource(TestConfiguration.db);
        String responseJSON = mr.getMediaBasicInfo(TestConfiguration.media_id.toString());
        assertTrue(responseJSON.length() > 0);
    }

    @Ignore
    @Test
    public void test_getSharedMediaForEvent() {
    }

    @Ignore
    @Test
    public void test_getMediaForUserAndEvent() {
    }

//    //  Tests that use the http service .........................................................
//
//    @Test
//    public void testGetMediasHttp() {
//
//        try {
//            // Hit URL to get all networks and save response text
//            HttpURLConnection conn = TestConfiguration.sendRequest("/medias/","GET","");
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
//    public void testGetMediaHttp() {
//
//        try {
//            // Hit URL to get all networks and save response text
//            HttpURLConnection conn = TestConfiguration.sendRequest("/medias/1", "GET", "");
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
