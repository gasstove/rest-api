package com.gasstove.gs;

import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.DBConnection;
import com.google.gson.Gson;

import javax.ws.rs.*;
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
}