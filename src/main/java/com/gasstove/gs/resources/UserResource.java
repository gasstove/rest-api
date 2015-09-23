package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Response;
import com.sun.jersey.api.core.InjectParam;

import javax.ws.rs.*;

import static org.junit.Assert.fail;

@Path("/users")
public class UserResource extends AbstractResource  {

    @SuppressWarnings("unused")
    public UserResource(){
        this(Configuration.getDB());
    };

    public UserResource(String db){
        super(db);
        this.ioclass = UserIO.class;
        this.objclass = User.class;
    }

    /**
     * Delete a user.
     *  1) delete a row from the users table
     *  2) delete_event( event.owner.id = delete_id )
     *  3) delete_media( media.owner.id = delete_id )
     * @param id
     * @return
     */
    @Override
    public String delete(@PathParam("id") String id,@QueryParam("gaswrapper") String callback) {
        return this.delete_user(id, Configuration.FORMAT.jsonp, callback);
    }

    ////////////////////////////////////////////////////////////
    // overloads
    ////////////////////////////////////////////////////////////

    public String delete_user(String id,Configuration.FORMAT response_format,String callback) {

        boolean success = true;

        // TODO : THE main table delete has to go last!!!
        // TODO: TRANSACTIONS!
        try {

            // delete row in users table, get a response
            String resp_json = super.deleteX(id);
            success &= (new Response(resp_json)).success;

            // delete all events belonging to this user
            if(success) {
                EventResource er = new EventResource(db);
                Response event_resp = new Response( er.deleteEventsOwnedBy(id) );
                success &= event_resp.success;
            }

            // delete media owned by this user
            if(success) {
                MediaResource mr = new MediaResource(db);
                Response media_resp = new Response( mr.deleteMediaOwnedBy(id) );
                success &= media_resp.success;
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            Response response = success ?
                    new Response(true, "User successfully deleted",null) :
                    new Response(false, "User deletion failed",null) ;
            return response.format(response_format,callback);
        }

    }

    public String delete_user(String id){
        return this.delete_user(id, Configuration.FORMAT.json, "");
    }

}