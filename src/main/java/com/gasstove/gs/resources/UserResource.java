package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserReader;
import com.gasstove.gs.models.User;
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

@Path("/users")
public class UserResource {

    /**
     *  Returns ids and names of all users.
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUsersBasicInfo() {
        String returnJSON = "";
        UserReader ur = null;
        try {
            ur = new UserReader();
            ArrayList<User> users = ur.getUsersBasicInfo();
            Gson gson = new Gson();
            returnJSON = gson.toJson(users);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            ur.close();
        }
        return returnJSON;
    }

    /**
     * Restful method to return user object by id
     *
     * @param userId   The id of the user to be loaded
     * @return JSON representation of User object
     */
    @Path("/{userId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUserBasicInfo(@PathParam("userId") String userId) {
        String returnJSON = "";
        UserReader ur = null;
        try {
            ur = new UserReader();
            User user = ur.getUserBasicInfo(Integer.parseInt(userId));
            returnJSON = user.toJson();
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            ur.close();
        }
        return returnJSON;
    }

    @Path("/event/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUsersForEvent(@PathParam("eventId") String eventId) {
        String returnJSON = "";
        UserReader ur = null;
        try {
            ur = new UserReader();
            ArrayList<User> users = ur.getUsersForEvent (Integer.parseInt(eventId));
            Gson gson = new Gson();
            returnJSON = gson.toJson(users);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            ur.close();
        }
        return returnJSON;
    }

}