package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.EventIO;
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
        EventIO eventIo = null;
        try {
            eventIo = new EventIO(db);
            ArrayList<Event> events = eventIo.getAll();
            returnJSON = Util.getGson().toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            eventIo.close();
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
        Connection dbConn = null;

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
            dbConn = (new DBConnection()).getConnection(db);

            // create writer
            EventIO eventIo = new EventIO(dbConn);

            // insert or update
            int eventId = get_event.getId()<0  ? eventIo.insert(get_event) : eventIo.update(get_event);

            // check success
            if(eventId<0) throw new Exception("Insert|update failed");

            // query and send it back
            return_event = eventIo.getWithId(eventId);

            response = new Response(true, "New event successfully saved", return_event.toJson());

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new event, " + e.getMessage(), null);

        } finally {
            try {
                // close connection
                dbConn.close();
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
        EventIO eventIO = null;
        try {
            eventIO = new EventIO(db);
            Event event = eventIO.getWithId(Integer.parseInt(eventId));
            returnJSON = event.toJson();
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            eventIO.close();
        }
        return returnJSON;
    }

    @Path("/{eventId: [0-9]+}")
    @DELETE
    public String deleteEvent(@PathParam("eventId") String eventId)  {

        Response response;

        try {

            // write
            boolean success = (new EventIO(db)).delete(Integer.parseInt(eventId));

            // check success
            response = success ?
                    new Response(true, "Event successfully deleted",null) :
                    new Response(true, "Event deletion failed",null) ;

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new event, " + e.getMessage(), null);

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
        EventIO eventIO = null;
        try {
            eventIO = new EventIO(db);
            ArrayList<Event> events = eventIO.getEventsForUser(Integer.parseInt(userId));
            Gson gson = Util.getGson();
            returnJSON = gson.toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            eventIO.close();
        }
        return returnJSON;
    }

}