package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.DBConnection;
import com.gasstove.gs.util.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.fail;

/**
 * Created by gomes on 6/8/15.
 */
public class MediaEventWriterTest {


    Connection conn;
    MediaEvent mediaevent;

    @Before
    public void setup() {
        try {

            // open db
            conn = new DBConnection().getConnection(TestConfiguration.db);

            // create a mediaevent
            mediaevent = new MediaEvent();
            mediaevent.setNumLikes(100);
            mediaevent.setNumDislikes(100);
            mediaevent.setNumDownloads(20);
            mediaevent.setMediaId(2);
            mediaevent.setEventId(4);
            mediaevent.setShared(true);
            mediaevent.setComment("hello");


        } catch (Exception e) {
            cleanup();
            fail();
            e.printStackTrace();
        }
    }

    @After
    public void cleanup() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException ee) {
            ee.printStackTrace();
            fail();
        }
    }

    @Ignore
    @Test
    public void testCrud() {

    }

}