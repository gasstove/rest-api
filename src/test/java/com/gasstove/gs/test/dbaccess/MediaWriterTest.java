package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaIO;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.test.TestConfiguration;

/**
 * Created by gomes on 5/2/15.
 */
public class MediaWriterTest extends BaseWriterTest {

    public MediaWriterTest(){
        clath = Media.class;
        io = new MediaIO(TestConfiguration.db);
    }

}
