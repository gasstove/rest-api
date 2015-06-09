package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaReader;
import com.gasstove.gs.dbaccess.MediaWriter;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.DBConnection;
import com.gasstove.gs.util.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by gomes on 5/2/15.
 */
public class MediaWriterTest {

    Connection conn;
    Media media;

    @Before
    public void setup(){
        try {

            // open db
            conn = new DBConnection().getConnection(TestConfiguration.db);

            // create a media
            media = new Media();
            media.setId(-1);
            media.setDateTaken(new Time(0));
            media.setFileName("x");
            media.setType("x");
            media.setUserId(-1);

            // remove it from the db
            MediaWriter mw = new MediaWriter(conn);
            mw.delete(media.getId());

        } catch (Exception e) {
            cleanup();
            e.printStackTrace();
            fail();
        }
    }

    @After
    public void cleanup(){
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException ee) {
            ee.printStackTrace();
            fail();
        }
    }

    @Ignore
    @Test
    public void testCrud(){

        try {

            // create the MediaWriter
            MediaWriter mw = new MediaWriter(conn);
            int mediaId = mw.insert(media);
            media.setId(mediaId);

            // check it is there
            MediaReader mr = new MediaReader(conn);
            Media m = mr.getMediaBasicInfo(media.getId());
            assertNotNull(m);
            assertEquals("x",m.getFileName());

            // modify the event
            media.setFileName("xx");

            // update
            mw.update(media);

            // check it worked
            Media m2 = mr.getMediaBasicInfo(media.getId());
            assertEquals("xx",m2.getFileName());

            // remove it
            assertTrue(mw.delete(media.getId()));

            // check it is not there
            m = mr.getMediaBasicInfo(media.getId());
            assertEquals(-1,m.getId());     // ie a non-existent event

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

    }

}
