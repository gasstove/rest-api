package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.Factory;
import com.gasstove.gs.resources.AbstractResource;
import com.gasstove.gs.util.Response;
import com.gasstove.gs.test.TestConfiguration;
import com.google.gson.Gson;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by gomes on 6/11/15.
 */
public abstract class AbstractResourceTest {

    protected Class clath;
    protected AbstractResource resource;
    protected String getAll_exp;            // expected return value of getAll
    protected String use_id;                // object id to use in getForId test
    protected String getForId_exp;          // expected return value of getForId(use_id)

    @Test
    public void test_getAll() {
        System.out.println("test_getAll for " + resource.getClass().getSimpleName() );
        String response = resource.getAll();
        TestConfiguration.printout(response, getAll_exp, "test_getAll");
        assertEquals(getAll_exp, response);
    }

    @Test
    public void test_getForId() {
        System.out.println("test_getForId for " + resource.getClass().getSimpleName() );
        String response = resource.getForId(use_id);
        TestConfiguration.printout(response, getForId_exp, "test_getForId");
        assertEquals(getForId_exp, response);
    }

    @Test
    public void test_insertdelete(){
        try {

            // create an event
            AbstractObject obj = Factory.generate_random(clath);

            // insert it
            String responseStr = resource.insertOrUpdate(obj.toJson());

            // get it
            Response response = (new Gson()).fromJson(responseStr,Response.class);

            // put it into a DBObject
            AbstractObject return_obj = (AbstractObject) clath.newInstance();
            return_obj.populate_from_Json(response.resource);

            // delete it
            responseStr = resource.delete(String.format("%d",return_obj.getId()));
            assertTrue(responseStr.contains("Object successfully deleted"));

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }
}
