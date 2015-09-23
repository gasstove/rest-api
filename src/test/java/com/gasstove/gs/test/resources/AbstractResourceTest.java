package com.gasstove.gs.test.resources;

import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.Factory;
import com.gasstove.gs.resources.AbstractResource;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.test.TestConfiguration;
import com.gasstove.gs.util.Response;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

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

    protected Configuration.FORMAT format;
    protected String callback = "testCallback";

    @Parameterized.Parameters
    public static Collection<Object[]> getFormat(){
        return Arrays.asList(new Object[][]{
                {Configuration.FORMAT.jsonp},
                {Configuration.FORMAT.json}
        });
    }

    public AbstractResourceTest(){
        Configuration.profile = Configuration.PROFILE.test;
    }

    @Test
    public void test_getAll() {
        System.out.println("test_getAll for " + resource.getClass().getSimpleName() + ", format=" + format);
        String response = resource.getAll(format,callback);
        TestConfiguration.printout(response, getAll_exp, "test_getAll");
        assertEquals(getAll_exp, response);
    }

    @Test
    public void test_getForId() {
        System.out.println("test_getForId for " + resource.getClass().getSimpleName()+ ", format=" + format);
        String response = resource.getForId(use_id,format,callback);
        TestConfiguration.printout(response, getForId_exp, "test_getForId");
        assertEquals(getForId_exp, response);
    }

    @Test
    public void test_insertdelete(){

        try {

            // create an event
            AbstractObject obj = Factory.generate_random(clath);

            // insert it
            String responseStr = resource.insertOrUpdate(obj.formatJson());

            // get it
            Response response = (new Gson()).fromJson(responseStr,Response.class);

            // put it into a DBObject
            AbstractObject return_obj = (AbstractObject) clath.newInstance();
            return_obj.populate_from_Json(response.resource);

            // delete it
            responseStr = resource.delete(String.format("%d",return_obj.getId()));
            assertTrue(responseStr.contains("successfully deleted"));

        } catch (Exception exp) {
            exp.printStackTrace();
            fail();
        }
    }
}
