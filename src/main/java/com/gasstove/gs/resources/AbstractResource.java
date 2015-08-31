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
    protected Configuration.FORMAT response_format = Configuration.FORMAT.jsonp; // TODO: PASS THIS IN

//    @Context
//    Request req;

//    @Context
//    HttpHeaders headers;

    // @Context
    // private ServletContext context;

    public AbstractResource(String db){
        this.db = db;
    }

    ////////////////////////////////////////////////////////////
    // '/'
    ////////////////////////////////////////////////////////////

    /**
     *  Returns all entries in a table
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getAll() {
        String returnStr = "";
        AbstractIO io = null;
        try {
            io = get_connection();
            ArrayList<AbstractObject> objs = io.getAll();
            returnStr = Util.formatArray(objs,response_format);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnStr = (new Response(false, exp.getMessage(), null)).format(response_format);
        } finally {
            if(io!=null)
                io.close();
        }
        return returnStr;
    }

    /** insert or update table row
     */
    @Path("/")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String insertOrUpdate(String objStr)  {

        AbstractObject get_obj, return_obj;
        Response response;

        AbstractIO io = null;

        /** check headers
         List<String> authHeaders = this.headers.getRequestHeader("Authorization");
         List<String> dbHeader = this.headers.getRequestHeader("DB");
         if (authHeaders==null || dbHeader==null)
         return Response.JSONMessage(false, "Error Saving New Scenario, Invalid Authentication Header", null);
         */

        /** authenticate
         String encodedUserPass = authHeaders.get(0);
         String dbName = dbHeader.get(0);
         OraDatabaseWeb db = Authentication.authenticate(encodedUserPass, dbName);
         */

        /**
         Authentication.User user = Authentication.getUserInfoFromHeader(encodedUserPass, dbName);
         */

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

            response = new Response(true, "New object successfully saved", return_obj.format(Configuration.FORMAT.json));

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new object, " + e.getMessage(), null);

        } finally {
            if(io!=null)
                io.close();
        }

        return response.format(response_format);
    }

    ////////////////////////////////////////////////////////////
    // '/#'
    ////////////////////////////////////////////////////////////

    /** get table row with id
     */
    @Path("/{id: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getForId(@PathParam("id") String id) {
        String returnString = "";
        AbstractIO io = null;
        try {
            io = get_connection();
            AbstractObject obj = (AbstractObject) io.getWithId(Integer.parseInt(id));
            returnString = obj.format(response_format);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnString = (new Response(false, exp.getMessage(), null)).format(response_format);
        } finally {
            if(io!=null)
                io.close();
        }
        return returnString;
    }

    /** delete table row
     */
    @Path("/{id: [0-9]+}")
    @DELETE
    public String delete(@PathParam("id") String id) {
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

        return response.format(response_format);
    }

    protected AbstractIO get_connection() throws Exception {
        AbstractIO io = (AbstractIO) ioclass.newInstance();
        io.connect(db);
        return io;
    }

}
