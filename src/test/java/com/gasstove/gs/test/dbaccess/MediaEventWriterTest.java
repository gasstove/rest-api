package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.test.TestConfiguration;

public class MediaEventWriterTest extends BaseWriterTest {

    public MediaEventWriterTest(){
        clath = MediaEvent.class;
        io = new MediaEventIO(TestConfiguration.db);
    }

}