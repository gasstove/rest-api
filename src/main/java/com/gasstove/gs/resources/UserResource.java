package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserReader;
import com.gasstove.gs.dbaccess.UserWriter;
import com.gasstove.gs.models.User;
import com.gasstove.gs.util.DBConnection;
import com.gasstove.gs.util.Util;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.SQLException;
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
            returnJSON = Util.getGson().toJson(users);
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
            ArrayList<User> users = ur.getUsersForEvent(Integer.parseInt(eventId));
            returnJSON = Util.getGson().toJson(users);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            ur.close();
        }
        return returnJSON;
    }

    @Path("/")
    @POST
    @Produces({ MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_JSON })
    public String insertUser(String userString)  {

        User get_user, return_user;
        Response response;
        Connection conn = null;

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

            get_user = new User(userString);

            // connect to db
            conn = (new DBConnection()).getConnection();
            UserWriter writer = new UserWriter(conn);

            // insert or update
            int userId = get_user.getId()<0  ? writer.insert(get_user) : writer.update(get_user);

            // check success
            if(userId<0)
                throw new Exception("Insert|update failed");

            // query and send it back
            return_user = (new UserReader(conn)).getUserBasicInfo(userId);

            response = new Response(true, "New user successfully saved", return_user.toJson());

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new user, " + e.getMessage(), null);

        } finally {
            try {
                // close connection
                conn.close();
            } catch (SQLException exp) {
                response = new Response(false, "Error closing db connection, " + exp.getMessage(), null);
            }
        }

        return response.toJSON();
    }

    @Path("/{userId: [0-9]+}")
    @DELETE
    public String deleteUser(@PathParam("eventId") String userId)  {

        Connection conn = null;
        Response response;

        try {

            // connect to db
            conn = (new DBConnection()).getConnection();

            UserWriter userWriter = new UserWriter(conn);
            boolean success = userWriter.delete(Integer.parseInt(userId));

            response = success ?
                    new Response(true, "User successfully deteled",null) :
                    new Response(true, "User deletion failed",null) ;

        } catch (Exception e) {
            e.printStackTrace();

            // rollback transaction
            //oraDatabase.rollbackTransaction(conn);

            response = new Response(false, "Error saving new user, " + e.getMessage(), null);

        } finally {
            try {
                // close connection
                conn.close();
            } catch (SQLException exp) {
                response = new Response(false, "Error closing db connection, " + exp.getMessage(), null);
            }
        }

        return response.toJSON();
    }

}