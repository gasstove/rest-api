package com.gasstove.gs.test.dbaccess;

import com.gasstove.gs.dbaccess.BaseIO;
import com.gasstove.gs.models.DBObject;
import com.gasstove.gs.models.Factory;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gomes on 6/9/15.
 */
public abstract class BaseWriterTest {

    Class clath;
    BaseIO io;

    @Test
    public void testCrud(){

        try {

            // create an object
            DBObject obj1 = Factory.generate_random(clath);

            // insert into database, get the id
            obj1.setId(io.insert(obj1));

            // check it is there
            assertNotNull( io.getWithId(obj1.getId()) );

            // modify it
            Factory.modify(obj1);

            // update
            io.update(obj1);

            // check it worked
            DBObject obj2 = (DBObject) io.getWithId(obj1.getId());
            assertTrue( obj1.shallowEquals(obj2) );

            // remove it
            assertTrue( io.delete(obj1.getId()) );

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
