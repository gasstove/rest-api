package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaReader;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gomes on 4/6/15.
 */
public class MediaReaderTest {

    MediaReader mr = new MediaReader(TestConfiguration.db);

    @Test
    public void testGetMediasBasicInfo() {
        try {
            ArrayList<Media> list = mr.getMediasBasicInfo();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getFileName() != null);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetMedia() {
        try {
            Media m = mr.getMediaBasicInfo(TestConfiguration.media_id);
            assertTrue(m.getId() == TestConfiguration.media_id);
            assertTrue(m.getFileName().length()>0);
            assertTrue(m.getType().length()>0);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

//    @Test
//    public void testGetMediaForEvent() {
//        try {
//            ArrayList<MediaEvent> m = mr.getMediaForEvent(TestDefaults.event_id);
//            assertNotNull(m);
//            assertTrue(m.size()>0);
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//    }


    @After
    public void close(){
        mr.close();
    }

}
