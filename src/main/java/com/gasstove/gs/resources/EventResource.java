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
public class EventResource extends AbstractResource {

    public EventResource(String db){
        super(db);
        this.ioclass = EventIO.class;
        this.objclass = Event.class;
    }

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
            eventIo = (EventIO) get_connection();
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
            eventIO = (EventIO) get_connection();
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
            eventIO = (EventIO) get_connection();
            ArrayList<Event> events = eventIO.getEventsForUser(Integer.parseInt(userId));
            Gson gson = Util.getGson();
            returnJSON = gson.toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            if(eventIO!=null)
                eventIO.close();
        }
        return returnJSON;
    }

}