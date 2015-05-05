package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaReader;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.test.util.TestDefaults;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gomes on 4/6/15.
 */
public class MediaReaderTest {

    @Test
    public void testGetMediasBasicInfo() {
        MediaReader mr = new MediaReader();
        try {
            ArrayList<Media> list = mr.getMediasBasicInfo();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getFileName() != null);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        } finally {
            mr.close();
        }
    }

    @Test
    public void testGetMedia() {
        MediaReader mr = new MediaReader();
        try {
            Media m = mr.getMediaBasicInfo(TestDefaults.media_id);
            assertTrue(m.getId() == TestDefaults.media_id);
            assertTrue(m.getFileName().length()>0);
            assertTrue(m.getType().length()>0);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        } finally {
            mr.close();
        }
    }

//    @Test
//    public void testGetMediaForEvent() {
//            MediaReader mr = new MediaReader();
//        try {
//            ArrayList<MediaEvent> m = mr.getMediaForEvent(TestDefaults.event_id);
//            assertNotNull(m);
//            assertTrue(m.size()>0);
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        } finally {
//    mr.close();
//}
//    }

}
