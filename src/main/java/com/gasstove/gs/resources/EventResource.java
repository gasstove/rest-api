package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.models.Event;
import com.google.gson.Gson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Restful Jersey based servlet for Image Resource
 * <p/>
 * Reference:
 * http://docs.oracle.com/cd/E19226-01/820-7627/giepu/index.html
 */

@Path("/events")
public class EventResource {

    /**media_mapping
     *
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEvents() {

        String returnJSON = "";

        try {
            EventReader er = new EventReader();
            ArrayList<Event> events = er.getEvents();

            Gson gson = new Gson();
            returnJSON = gson.toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
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
    public String getEvent(@PathParam("eventId") String eventId) {

        String returnJSON = "";

        try {
            EventReader er = new EventReader();
            Event event = er.getEvent(Integer.parseInt(eventId));

            Gson gson = new Gson();
            returnJSON = gson.toJson(event);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
        }
        return returnJSON;
    }
}