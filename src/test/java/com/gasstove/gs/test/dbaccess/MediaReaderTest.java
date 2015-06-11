package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaIO;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.test.TestConfiguration;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by gomes on 4/6/15.
 */
public class MediaReaderTest extends BaseReaderTest<Media> {

    public MediaReaderTest(){
        io = new MediaIO(TestConfiguration.db);
        test_id = TestConfiguration.media_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

    @Ignore
    @Test
    public void test_getMediaForUserAndEvent() {

    }

    @Ignore
    @Test
    public void test_getSharedMediaForEvent() {

    }


}
