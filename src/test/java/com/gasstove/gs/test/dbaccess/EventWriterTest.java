package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.test.TestConfiguration;

public class EventWriterTest extends BaseWriterTest {

    public EventWriterTest(){
        clath = Event.class;
        io = new EventIO(TestConfiguration.db);
    }

}
