package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaReader;
import com.gasstove.gs.models.Media;
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
    public void testGetMedias() {
        try {
            ArrayList<Media> list = MediaReader.getMediasBasicInfo();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getFileName() != null);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

//    @Test
//    public void testGetMedia() {
//        try {
//            Media m = MediaReader.getMedia(7295);
//            assertTrue(m.getId() == 7295);
//            assertTrue(m.getFileName().length()>0);
//            assertTrue(m.getType().length()>0);
//            assertNotNull(m.getOwnerName());
//            assertNotNull(m.getCards());
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//    }
//
//    @Test
//    public void testGetMediaForEvent() {
//        try {
//            ArrayList<Media> m = MediaReader.getMediaForEvent(745);
//            assertNotNull(m);
//            assertTrue(m.size()>0);
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//    }

}
