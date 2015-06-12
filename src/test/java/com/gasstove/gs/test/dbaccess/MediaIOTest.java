package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaIO;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by gomes on 6/11/15.
 */
public class MediaIOTest extends AbstractIOTest<Media> {

    public MediaIOTest(){
        clath = Media.class;
        io = new MediaIO(TestConfiguration.db);
        test_id = TestConfiguration.media_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

    @Test
    public void test_getMediaForUserAndEvent() {
        try {
            int user_id = 2;
            ArrayList<MediaEvent> arr = ((MediaIO)io).getMediaForUserAndEvent(
                                                user_id,
                                                TestConfiguration.event_id );
            assertNotNull(arr);
            assertEquals(arr.size(),2);
            String exp0 = "{\"mediaId\":75,\"eventId\":1,\"numDownloads\":2543,\"shared\":true,\"numLikes\":0,\"numDislikes\":0,\"mediaType\":\"audio\",\"mediaFileName\":\"media_1165\",\"userId\":2,\"mediaDateTaken\":\"1970-01-16T06:46:43.587-08:00\",\"id\":-1}";
            String exp1 = "{\"mediaId\":97,\"eventId\":1,\"numDownloads\":2607,\"shared\":true,\"numLikes\":0,\"numDislikes\":0,\"mediaType\":\"video\",\"mediaFileName\":\"media_1056\",\"userId\":2,\"mediaDateTaken\":\"1970-01-05T10:39:35.214-08:00\",\"id\":-1}";
            assertEquals(arr.get(0).toJson(), exp0);
            assertEquals(arr.get(1).toJson(), exp1);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void test_getSharedMediaForEvent() {
        ArrayList<MediaEvent> arr = ((MediaIO)io).getSharedMediaForEvent(TestConfiguration.event_id );
        assertNotNull(arr);
        assertEquals(arr.size(),14);
        String exp0 = "{\"mediaId\":5,\"eventId\":1,\"numDownloads\":6693,\"shared\":true,\"numLikes\":0,\"numDislikes\":0,\"mediaType\":\"video\",\"mediaFileName\":\"media_1305\",\"userId\":21,\"mediaDateTaken\":\"1970-01-05T17:55:08.228-08:00\",\"id\":-1}";
        assertEquals(arr.get(0).toJson(), exp0);
    }

}
