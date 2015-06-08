package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.dbaccess.EventWriter;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.DBConnection;
import com.gasstove.gs.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Restful Jersey based servlet for Image Resource
 * <p/>
 * Reference:
 * http://docs.oracle.com/cd/E19226-01/820-7627/giepu/index.html
 */

@Path("/events")
public class EventResource {

    private String db = Configuration.dbConnect;     // used to distinguish test and dev

    public EventResource(String db){
        this.db = db;
    }

//    @Context
//    Request req;

    @Context
    HttpHeaders headers;

//    @Context
//    private ServletContext context;

    ////////////////////////////////////////////////////////////
    // '/'
    ////////////////////////////////////////////////////////////

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
            er = new EventReader(db);
            ArrayList<Event> events = er.getEventsBasicInfo();
            returnJSON = Util.getGson().toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            er.close();
        }
        return returnJSON;
    }

    @Path("/")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String insertEvent(String eventString)  {

        Event get_event, return_event;
        Response response;
        Connection conn = null;

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

            // generate an Event
            get_event = new Event(eventString);

            // connect to db
            conn = (new DBConnection()).getConnection();
            EventWriter writer = new EventWriter(conn);

            // insert or update
            int eventId = get_event.getId()<0  ? writer.insert(get_event) : writer.update(get_event);

            // check success
            if(eventId<0) throw new Exception("Insert|update failed");

            // query and send it back
            return_event = (new EventReader(conn)).getEventBasicInfo(eventId);

            response = new Response(true, "New event successfully saved", return_event.toJson());

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new event, " + e.getMessage(), null);

        } finally {
            try {
                // close connection
                conn.close();
            } catch (SQLException exp) {
                response = new Response(false, "Error closing db connection, " + exp.getMessage(), null);
            }
        }

        return response.toJSON();
    }

    ////////////////////////////////////////////////////////////
    // '/#'
    ////////////////////////////////////////////////////////////

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
            er = new EventReader(db);
            Event event = er.getEventBasicInfo(Integer.parseInt(eventId));
            returnJSON = event.toJson();
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            er.close();
        }
        return returnJSON;
    }

    @Path("/{eventId: [0-9]+}")
    @DELETE
    public String deleteEvent(@PathParam("eventId") String eventId)  {

        Connection conn = null;
        Response response;

        try {

            // connect to db
            conn = (new DBConnection()).getConnection();

            // write
            boolean success = (new EventWriter(conn)).delete(Integer.parseInt(eventId));

            // check success
            response = success ?
                    new Response(true, "Event successfully deleted",null) :
                    new Response(true, "Event deletion failed",null) ;

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new event, " + e.getMessage(), null);

        } finally {
            try {
                // close connection
                conn.close();
            } catch (SQLException exp) {
                response = new Response(false, "Error closing db connection, " + exp.getMessage(), null);
            }
        }

        return response.toJSON();
    }

    ////////////////////////////////////////////////////////////
    // '/user/#'
    ////////////////////////////////////////////////////////////

    @Path("/user/{userId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEventsForUser(@PathParam("userId") String userId) {
        String returnJSON = "";
        EventReader er = null;
        try {
            er = new EventReader(db);
            ArrayList<Event> events = er.getEventsForUser(Integer.parseInt(userId));
            Gson gson = Util.getGson();
            returnJSON = gson.toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            er.close();
        }
        return returnJSON;
    }

}