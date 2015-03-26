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
     *  This is the root resource for actors. It returns all actors
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getActors() {

        String returnJSON = "";

        try {
            ActorReader er = new ActorReader();
            ArrayList<Actor> actors = er.getActors();

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
     * @return JSON representation of Event object
     */
    @Path("/{actorId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEvent(@PathParam("actorId") String actorId) {

        String returnJSON = "";

        try {
            ActorReader ar = new ActorReader();
            Actor actor = ar.getActor(Integer.parseInt(actorId));

            Gson gson = new Gson();
            returnJSON = gson.toJson(actor);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
        }
        return returnJSON;
    }
}