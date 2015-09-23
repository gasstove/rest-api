package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.AbstractIO;
import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Response;
import com.gasstove.gs.util.Util;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Provides getAll, getWithId, and insert, update, delete functionality.
 */
public class AbstractResource {

    protected String db;
    protected Class ioclass;
    protected Class objclass;

    public AbstractResource(String db){
        this.db = db;
    }

    ////////////////////////////////////////////////////////////
    // '/'
    ////////////////////////////////////////////////////////////

    /**
     *  Returns all entries in a table
     */
//    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll(@QueryParam("gaswrapper") String callback) {
        return getAllX(Configuration.FORMAT.jsonp,callback);
    }

    /** insert or update table row
     */
//    @Path("/")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String insertOrUpdate(String objStr,@QueryParam("gaswrapper") String callback)  {
        return this.insertOrUpdateX(objStr,Configuration.FORMAT.jsonp,callback);
    }

    ////////////////////////////////////////////////////////////
    // '/#'
    ////////////////////////////////////////////////////////////

    /** get table row with id
     */
    @Path("/{id: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getForId(@PathParam("id") String id,@QueryParam("gaswrapper") String callback) {
        return this.getForIdX(id,Configuration.FORMAT.jsonp,callback);
    }

    /** delete table row
     */
    @Path("/{id: [0-9]+}")
    @DELETE
    public String delete(@PathParam("id") String id,@QueryParam("gaswrapper") String callback) {
        return this.deleteX(id,Configuration.FORMAT.jsonp,callback);
    }

    protected AbstractIO get_connection() throws Exception {
        AbstractIO io = (AbstractIO) ioclass.newInstance();
        io.connect(db);
        return io;
    }

    ////////////////////////////////////////////////////////////
    // overloading
    ////////////////////////////////////////////////////////////

    public String getAllX(Configuration.FORMAT response_format,String callback) {
        String returnStr = "";
        AbstractIO io = null;
        try {
            io = get_connection();
            ArrayList<AbstractObject> objs = io.getAll();
            returnStr = Util.formatArray(objs,response_format,callback);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnStr = (new Response(false, exp.getMessage(), null)).format(response_format,callback);
        } finally {
            if(io!=null)
                io.close();
        }
        return returnStr;
    }

    public String getAllX() {
        return this.getAllX(Configuration.FORMAT.json,"");
    }

    public String insertOrUpdateX(String objStr,Configuration.FORMAT response_format,String callback)  {

        AbstractObject get_obj, return_obj;
        Response response;

        AbstractIO io = null;

        try {

            // generate an object
            get_obj = (AbstractObject) objclass.newInstance();
            get_obj.populate_from_Json(objStr);

            // connect
            io = this.get_connection();

            // insert or update
            int objId = get_obj.getId()<0  ? io.insert(get_obj) : io.update(get_obj);

            // check success
            if(objId<0) throw new Exception("Insert|update failed");

            // query and send it back
            return_obj = (AbstractObject) io.getWithId(objId);

            response = new Response(true, "New object successfully saved", return_obj.formatJson());

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new object, " + e.getMessage(), null);

        } finally {
            if(io!=null)
                io.close();
        }

        return response.format(response_format,callback);
    }

    public String insertOrUpdateX(String objStr) {
        return this.insertOrUpdateX(objStr,Configuration.FORMAT.json,"");
    }

    public String getForIdX(String id,Configuration.FORMAT response_format,String callback) {
        String returnString = "";
        AbstractIO io = null;
        try {
            io = get_connection();
            AbstractObject obj = (AbstractObject) io.getWithId(Integer.parseInt(id));
            returnString = obj.format(response_format,callback);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnString = (new Response(false, exp.getMessage(), null)).format(response_format,callback);
        } finally {
            if(io!=null)
                io.close();
        }
        return returnString;
    }

    public String getForIdX(String id) {
        return this.getForIdX(id,Configuration.FORMAT.json,"");
    }

    public String deleteX(String id,Configuration.FORMAT response_format, String callback) {

        Response response;

        AbstractIO io = null;

        try {

            // connect
            io = this.get_connection();

            // write
            io.delete(Integer.parseInt(id));

            // check success
            response = new Response(true, "Object successfully deleted",null);

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Object deletion failed",null) ;

        } finally {
            if(io!=null)
                io.close();
        }

        return response.format(response_format,callback);
    }

    public String deleteX(String id) {
        return deleteX(id,Configuration.FORMAT.json,"");
    }

}
