package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserEventIO;
import com.gasstove.gs.models.AbstractObject;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.models.User;
import com.gasstove.gs.models.UserEvent;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Response;
import com.gasstove.gs.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.lang.reflect.Type;
import java.util.ArrayList;

@Path("/userevents")
public class UserEventResource extends AbstractResource  {

    @SuppressWarnings("unused")
    public UserEventResource(){
        this(Configuration.getDB());
    };

    public UserEventResource(String db){
        super(db);
        this.ioclass = UserEventIO.class;
        this.objclass = UserEvent.class;
    }

    ////////////////////////////////////////////////////////////
    // '/user/#'
    ////////////////////////////////////////////////////////////

    @Path("/user/{userId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getEventsForUser(@PathParam("userId") String userId) {
        String returnJSON = "";
        UserEventIO io = null;
        try {
            io = (UserEventIO) get_connection();
            ArrayList<Event> events = io.getEventsForUser(Integer.parseInt(userId));
            returnJSON = Util.getGson().toJson(events);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            if(io!=null)
                io.close();
        }
        return returnJSON;
    }

    ////////////////////////////////////////////////////////////
    // '/event/#'
    ////////////////////////////////////////////////////////////

    @Path("/event/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUsersForEvent(@PathParam("eventId") String eventId) {
        String returnJSON = "";
        UserEventIO io = null;
        try {
            io = (UserEventIO) get_connection();
            ArrayList<User> users = io.getUsersForEvent(Integer.parseInt(eventId));
            returnJSON = Util.getGson().toJson(users);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            if(io!=null)
                io.close();
        }
        return returnJSON;
    }

    @Path("/event/{eventId: [0-9]+}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String cloberGuestsInEvent(@PathParam("eventId") String eventId, String users_json){

        Response response;
        int eid = Integer.parseInt(eventId);
        UserEventIO io = null;

        try {

            // construct an array from json string
            Type type = new TypeToken<ArrayList<User>>(){}.getType();
            ArrayList<AbstractObject> new_users = Util.getGson().fromJson(users_json,type);

            // connect
            io = (UserEventIO) get_connection();

            // get all users in this event
            ArrayList<AbstractObject> existing_users = new ArrayList<AbstractObject>();
            existing_users.addAll(io.getUsersForEvent(eid));

            // serparate added and deleted users
            ArrayList<AbstractObject> added_users = Util.diffById(new_users,existing_users);
            ArrayList<AbstractObject> deleted_users = Util.diffById(existing_users,new_users);

            // insert added_users
            for(AbstractObject user : added_users){
                UserEvent ue = new UserEvent();
                ue.setEventId(eid);
                ue.setUserId(user.getId());
                ue.setRole(Permissions.Role.GUEST);
                io.insert(ue);
            }

            // delete deleted_users
            // TODO: Should we also delete their media, or keep until event gets deleted?
            for(AbstractObject user : deleted_users)
                io.deleteForEventAndUserIds(eid, user.getId());

            // success response
            response = new Response(true, "New object success fully saved","");

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving guest list, " + e.getMessage(), null);

        } finally {
            io.close();
        }

        return response.toJSON();
    }

}
