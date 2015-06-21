package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.AbstractIO;
import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.Factory;
import com.gasstove.gs.util.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by gomes on 6/11/15.
 */
public abstract class AbstractIOTest<T> {

    Class clath;
    AbstractIO io;
    int test_id;

    public AbstractIOTest(){
        Configuration.profile = Configuration.PROFILE.test;
    }

    ///////////////////////////////////////////////////////
    // READER
    ///////////////////////////////////////////////////////

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

    ///////////////////////////////////////////////////////
    // WRITER
    ///////////////////////////////////////////////////////

    @Test
    public void testCrud(){

        try {

            // create an object
            AbstractObject obj1 = Factory.generate_random(clath);

            // insert into database, get the id
            obj1.setId(io.insert(obj1));

            // check it is there
            assertNotNull( io.getWithId(obj1.getId()) );

            // modify it
            Factory.modify(obj1);

            // update
            io.update(obj1);

            // check it worked
            AbstractObject obj2 = (AbstractObject) io.getWithId(obj1.getId());
            assertTrue( obj1.shallowEquals(obj2) );

            // remove it
            io.delete(obj1.getId());

            // check it is not there
            assertNull(io.getWithId(obj1.getId()));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            if(io!=null)
                io.close();
        }

    }

}
