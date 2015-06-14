package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaIO;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.test.TestConfiguration;

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



}
