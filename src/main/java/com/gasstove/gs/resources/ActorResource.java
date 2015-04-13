package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.ActorReader;
import com.gasstove.gs.dbaccess.EventReader;
import com.gasstove.gs.models.Actor;
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

@Path("/actors")
public class ActorResource {

    /**
     *  Returns ids and names of all actors.
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getActors() {
        String returnJSON = "";
        try {
            ArrayList<Actor> actors = ActorReader.getActorsIdAndName();
            Gson gson = new Gson();
            returnJSON = gson.toJson(actors);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
        }
        return returnJSON;
    }

    /**
     * Restful method to return actor object by id
     *
     * @param actorId   The id of the actor to be loaded
     * @return JSON representation of Actor object
     */
    @Path("/{actorId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getActor(@PathParam("actorId") String actorId) {
        String returnJSON = "";
        try {
            Actor actor = ActorReader.getActorFull(Integer.parseInt(actorId));
            Gson gson = new Gson();
            returnJSON = gson.toJson(actor);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
        }
        return returnJSON;
    }

//    /**
//     * Restful method to return actor object an event and media
//     * in the event
//     *
//     * @param actorId   The id of the actor to be loaded
//       @param eventId   The id of the event to be loaded
//     * @return JSON representation of Actor object
//     */
//    @Path("/actor/{Id: [0-9]+}/event/{eventId: [0-9]+}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getEvent(@PathParam("actorId") String actorId,
//                           @PathParam("eventId") String eventId) {
//
//        String returnJSON = "";
//
//        try {
//            Actor actor = ActorReader.getActorEventMedia(Integer.parseInt(actorId),
//                                                Integer.parseInt(eventId));
//
//            Gson gson = new Gson();
//            returnJSON = gson.toJson(actor);
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
//        } finally {
//        }
//        return returnJSON;
//    }
}