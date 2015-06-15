package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserEventIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.models.User;
import com.gasstove.gs.models.UserEvent;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Response;
import com.gasstove.gs.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/userevents")
public class UserEventResource extends AbstractResource  {

    @SuppressWarnings("unused")
    public UserEventResource(){
        this(Configuration.dbConnect);
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
            Gson gson = Util.getGson();
            returnJSON = gson.toJson(events);
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
            io.close();
        }
        return returnJSON;
    }

    @Path("/event/{eventId: [0-9]+}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String addUsersToEvent(@PathParam("eventId") String eventId, String users_json){

        int eid = Integer.parseInt(eventId);
        UserEventIO io = null;

        try {

            // construct an array from json string
            ArrayList<User> users = new ArrayList<User>();
            users = Util.getGson().fromJson(users_json,users.getClass());

            // connect
            io = (UserEventIO) get_connection();

            for(User user : users){
                UserEvent ue = new UserEvent();
                ue.setEventId(eid);
                ue.setUserId(user.getId());
                ue.setRole(Permissions.Role.GUEST);
                io.insert(ue);
            }

//            get_user = new User(userString);
//
//            // connect to db
//            conn = (new DBConnection()).getConnection();
//            UserWriter writer = new UserWriter(conn);
//
//            // insert or update
//            int userId = get_user.getId()<0  ? writer.insert(get_user) : writer.update(get_user);
//
//            // check success
//            if(userId<0)
//                throw new Exception("Insert|update failed");
//
//            // query and send it back
//            return_user = (new UserReader(conn)).getUserBasicInfo(userId);
//
//            response = new Response(true, "New user successfully saved", return_user.toJson());

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

//            response = new Response(false, "Error saving new user, " + e.getMessage(), null);

        } finally {
            io.close();
        }

//        return response.toJSON();
        return null;
    }

}
