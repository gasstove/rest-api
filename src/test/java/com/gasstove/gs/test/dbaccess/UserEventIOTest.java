package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserEventIO;
import com.gasstove.gs.models.UserEvent;
import com.gasstove.gs.test.TestConfiguration;

/**
 * Created by gomes on 6/11/15.
 */
public class UserEventIOTest extends AbstractIOTest<UserEvent> {

    public UserEventIOTest(){
        clath = UserEvent.class;
        io = new UserEventIO(TestConfiguration.db);
        test_id = TestConfiguration.userevent_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

}
