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
    private MediaReader ar = new MediaReader();
    /**
     * The constructor sets up database for so we can set up a Model DataBase Access
     * reader and writer;
     *
     * @throws Exception This happens if the database set up has trouble
     */
    public MediaReaderTest() throws Exception {

    }

    @Test
    public void testGetMedias() {
        try {
            ArrayList<Media> list = ar.getMedias();
            assertTrue(list.size() > 0);
            assertTrue(list.get(0).getFileName() != null);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }

    }

//    @Test
//    public void testGetActor() {
//
//        try {
//
//            Media a = ar.getMedia(122);
//            assertTrue(a.getFirst().length() > 0);
//            assertTrue(a.getId() == 122);
//            assertNotNull(a.getEvents());
//            assertNotNull(a.getLast());
//            assertNotNull(a.getContactMethod());
//            assertNotNull(a.isSubscriber());
//
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            fail();
//        }
//
//    }

}
