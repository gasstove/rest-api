package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.test.TestConfiguration;

/**
 * Created by gomes on 6/11/15.
 */
public class MediaEventIOTest extends AbstractIOTest<MediaEvent> {

    public MediaEventIOTest(){
        clath = MediaEvent.class;
        io = new MediaEventIO(TestConfiguration.db);
        test_id = TestConfiguration.mediaevent_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

}
