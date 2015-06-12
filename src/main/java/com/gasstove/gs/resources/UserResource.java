package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.util.Util;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/users")
public class UserResource extends AbstractResource  {

    public UserResource(String db){
        super(db);
        this.ioclass = UserIO.class;
        this.objclass = User.class;
    }

    ////////////////////////////////////////////////////////////
    // '/'
    ////////////////////////////////////////////////////////////

//    /**
//     *  Returns ids and names of all users.
//     */
//    @Path("/")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getUsersBasicInfo() {
//        String returnJSON = "";
//        UserIO userIO = null;
//        try {
//            userIO = (UserIO) get_connection();
//            ArrayList<User> users = userIO.getAll();
//            returnJSON = Util.getGson().toJson(users);
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
//        } finally {
//            userIO.close();
//        }
//        return returnJSON;
//    }
//
//    ////////////////////////////////////////////////////////////
//    // '/#'
//    ////////////////////////////////////////////////////////////
//
//    /**
//     * Restful method to return user object by id
//     *
//     * @param userId   The id of the user to be loaded
//     * @return JSON representation of User object
//     */
//    @Path("/{userId: [0-9]+}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getUserBasicInfo(@PathParam("userId") String userId) {
//        String returnJSON = "";
//        UserIO userIO = null;
//        try {
//            userIO = (UserIO) get_connection();
//            User user = userIO.getWithId(Integer.parseInt(userId));
//            returnJSON = user.toJson();
//        } catch (Exception exp) {
//            exp.printStackTrace();
//            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
//        } finally {
//            userIO.close();
//        }
//        return returnJSON;
//    }

    ////////////////////////////////////////////////////////////
    // '/event/#'
    ////////////////////////////////////////////////////////////

    @Path("/event/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUsersForEvent(@PathParam("eventId") String eventId) {
        String returnJSON = "";
        UserIO userIO = null;
        try {
            userIO = (UserIO) get_connection();
            ArrayList<User> users = userIO.getUsersForEvent(Integer.parseInt(eventId));
            returnJSON = Util.getGson().toJson(users);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            userIO.close();
        }
        return returnJSON;
    }

    @Path("/event/{eventId: [0-9]+}")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String addUsersToEvent(@PathParam("eventId") String eventId, String userarray_json){

        System.out.println(eventId);
        System.out.println(userarray_json);

//        try {
//
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
//
//        } catch (Exception e) {
//            e.printStackTrace();
//
//            // rollback transaction
//            //oraDatabase.rollbackTransaction(conn);
//
//            response = new Response(false, "Error saving new user, " + e.getMessage(), null);
//
//        } finally {
//            try {
//                // close connection
//                conn.close();
//            } catch (SQLException exp) {
//                response = new Response(false, "Error closing db connection, " + exp.getMessage(), null);
//            }
//        }
//
//        return response.toJSON();
        return null;
    }

}