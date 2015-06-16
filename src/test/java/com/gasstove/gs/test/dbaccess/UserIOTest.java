package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.TestConfiguration;

/**
 * Created by gomes on 6/11/15.
 */
public class UserIOTest extends AbstractIOTest<User> {

    public UserIOTest(){
        clath = User.class;
        io = new UserIO(TestConfiguration.db);
        test_id = TestConfiguration.user_id;
    }

    ////////////////////////////////////////////
    // additional readers
    ////////////////////////////////////////////

}
