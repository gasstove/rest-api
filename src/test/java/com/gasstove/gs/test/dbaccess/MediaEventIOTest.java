package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Configuration;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by gomes on 6/11/15.
 */
public class MediaEventIOTest extends AbstractIOTest<MediaEvent> {

    public MediaEventIOTest(){
        clath = MediaEvent.class;
        io = new MediaEventIO(Configuration.getDB());
        test_id = TestConfiguration.mediaevent_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

    @Test
    public void test_getMediaForUserAndEvent() {

        String callback = "FAKECALLBACK_FIXTHISINAPI!!!"; // TODO FIX THIS

        try {
            int user_id = 45;
            ArrayList<MediaEvent> arr = ((MediaEventIO)io).getMediaForUserAndEvent(
                    user_id,
                    TestConfiguration.event_id );
            assertNotNull(arr);
            assertEquals(2,arr.size());

            String response, expected;
            response = arr.get(0).format(Configuration.FORMAT.json,callback);
            expected = "{\"mediaId\":34,\"eventId\":1,\"numDownloads\":5375,\"shared\":false,\"numLikes\":0,\"numDislikes\":0,\"mediaType\":\"audio\",\"mediaFileName\":\"media_1109\",\"userId\":45,\"mediaDateTaken\":\"1969-12-15T18:35:32.594-08:00\",\"id\":-1}";
            TestConfiguration.printout(response,expected,"test_getMediaForUserAndEvent");
            assertEquals(expected,response);

            response = arr.get(1).format(Configuration.FORMAT.json,callback);
            expected = "{\"mediaId\":271,\"eventId\":1,\"numDownloads\":4664,\"shared\":false,\"numLikes\":0,\"numDislikes\":0,\"mediaType\":\"photo\",\"mediaFileName\":\"media_1032\",\"userId\":45,\"mediaDateTaken\":\"1969-12-11T05:37:18.495-08:00\",\"id\":-1}";
            TestConfiguration.printout(response,expected,"test_getMediaForUserAndEvent");
            assertEquals(expected,response);

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_getSharedMediaForEvent() {

        String callback = "FAKECALLBACK_FIXTHISINAPI!!!"; // TODO FIX THIS

        ArrayList<MediaEvent> arr = ((MediaEventIO)io).getSharedMediaForEvent(TestConfiguration.event_id);
        assertNotNull(arr);
        assertEquals(10,arr.size());
        String response = arr.get(0).format(Configuration.FORMAT.json,callback);
        String expected = "{\"mediaId\":92,\"eventId\":1,\"numDownloads\":1448,\"shared\":true,\"numLikes\":0,\"numDislikes\":0,\"mediaType\":\"photo\",\"mediaFileName\":\"media_1269\",\"userId\":50,\"mediaDateTaken\":\"1970-01-24T18:58:27.146-08:00\",\"id\":-1}";
        TestConfiguration.printout(response,expected,"test_getSharedMediaForEvent");
        assertEquals(expected,response);
    }

}
