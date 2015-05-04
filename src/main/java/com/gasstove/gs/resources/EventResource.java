package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.dbaccess.EventWriter;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.DBConnection;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Restful Jersey based servlet for Image Resource
 * <p/>
 * Reference:
 * http://docs.oracle.com/cd/E19226-01/820-7627/giepu/index.html
 */

@Path("/events")
public class EventResource {


//    @Context
//    Request req;

    @Context
    HttpHeaders headers;


//    @Context
//    private ServletContext context;

    /** Returns ids and names of all events.
     *
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEventsBasicInfo() {
        String returnJSON;
        EventReader er = null;
        try {
            er = new EventReader();
            ArrayList<Event> events = er.getEventsBasicInfo();
            Gson gson = new Gson();
            returnJSON = gson.toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
            er.close();
        }
        return returnJSON;
    }

    /**
     * Restful method to return event object
     *
     * @param eventId   The id of the event to be loaded
     * @return JSON representation of Event object
     */
    @Path("/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEventBasicInfo(@PathParam("eventId") String eventId) {
        String returnJSON;
        EventReader er = null;
        try {
            er = new EventReader();
            Event event = er.getEventBasicInfo(Integer.parseInt(eventId));
            Gson gson = new Gson();
            returnJSON = gson.toJson(event);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
            er.close();
        }
        return returnJSON;
    }

    @Path("/user/{userId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEventsForUser(@PathParam("userId") String userId) {
        String returnJSON = "";
        EventReader er = null;
        try {
            er = new EventReader();
            ArrayList<Event> events = er.getEventsForUser(Integer.parseInt(userId));
            Gson gson = new Gson();
            returnJSON = gson.toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
            er.close();
        }
        return returnJSON;
    }

    @Path("/")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    public String insertEvent(String eventString)  {

        Connection conn = null;
        String returnJSON = "";

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

            // connect to db
            conn = (new DBConnection()).getConnection();

            // tools
            Gson gson = new Gson();
            EventWriter eventWriter = new EventWriter(conn);

            // insert
            Event event = gson.fromJson(eventString,Event.class);

            int eventId = eventWriter.insert(event);

            // check insert succeeded
            if(eventId<0)
                throw new Exception("Insert returned id=-1");

            // serialize it and send it back to client
            EventReader eventReader = new EventReader(conn);
            event = eventReader.getEventBasicInfo(eventId);

            returnJSON = Response.JSONMessage(true, "New event successfully saved", gson.toJson(event));

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            returnJSON = Response.JSONMessage(false, "Error saving new event, " + e.getMessage(), null);

        } finally {
            try {
                // close connection
                conn.close();
            } catch (SQLException exp) {
                returnJSON = Response.JSONMessage(false, "Error closing db connection, " + exp.getMessage(), null);
            }
        }

        return returnJSON;
    }

}