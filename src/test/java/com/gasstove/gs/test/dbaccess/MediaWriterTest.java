package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaReader;
import com.gasstove.gs.dbaccess.MediaWriter;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.util.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
            conn = new DBConnection().getConnection();

            // create a media
            media = new Media();
            media.setId(-1);
            media.setDateTaken(new Date(0));
            media.setFileName("x");
            media.setType("x");
            media.setUserId(-1);

            // remove it from the db
            MediaWriter mw = new MediaWriter(conn);
            mw.delete(media);

        } catch (Exception e) {
            cleanup();
            e.printStackTrace();
        }
    }

    @After
    public void cleanup(){
        try {
            if(conn!=null)
                conn.close();
        } catch (SQLException ee) {
            ee.printStackTrace();
        }
    }

    @Test
    public void testCrud(){

        try {

            // create the MediaWriter
            MediaWriter mw = new MediaWriter(conn);
            mw.insert(media);

            // check it is there
            MediaReader mr = new MediaReader(conn);
            Media m = mr.getMediaBasicInfo(media.getId());
            assertNotNull(m);
            assertEquals(m.getFileName(),"x");

            // modify the event
            media.setFileName("xx");

            // update
            mw.update(media);

            // check it worked
            m = mr.getMediaBasicInfo(media.getId());
            assertEquals(m.getFileName(),"xx");

            // remove it
            mw.delete(media);

            // check it is not there
            m = mr.getMediaBasicInfo(media.getId());
            assertEquals(m.getId(),-1);     // ie a non-existent event

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
