package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.BaseIO;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class BaseReaderTest<T> {

    BaseIO io;
    int test_id;

    @Test
    public void test_getAll() {
        try {

            System.out.println("test_getAll for " + io.getClass().getName());
            ArrayList<T> list = io.getAll();
            assertNotNull(list);
            assertTrue(list.size() > 0);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @Test
    public void testGetOne() {
        try {
            T e = (T) io.getWithId(test_id);
            assertNotNull(e);
        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }

    @After
    public void close(){
        io.close();
    }

}
