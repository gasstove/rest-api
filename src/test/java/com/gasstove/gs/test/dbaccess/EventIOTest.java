package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.test.TestConfiguration;

/**
 * Created by gomes on 6/11/15.
 */
public class EventIOTest extends AbstractIOTest<Event> {

    public EventIOTest(){
        clath = Event.class;
        io = new EventIO(TestConfiguration.db);
        test_id = TestConfiguration.event_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////


}
