package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.models.User;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserWriterTest extends BaseWriterTest {

    public UserWriterTest(){
        clath = User.class;
        io = new UserIO(TestConfiguration.db);
    }

}
