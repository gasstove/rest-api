package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;
import com.gasstove.gs.util.Configuration;

import javax.ws.rs.*;

@Path("/users")
public class UserResource extends AbstractResource  {

    @SuppressWarnings("unused")
    public UserResource(){
        this(Configuration.dbConnect);
    };

    public UserResource(String db){
        super(db);
        this.ioclass = UserIO.class;
        this.objclass = User.class;
    }

    @Override
    public String delete(@PathParam("id") String id) {

        String super_str = super.delete(id);

        // if not success, return

        // delete all events belonging to this user
        String event_str = (new EventResource(this.db)).delete_all_events_for_user(id);

        // delete all media belonging to this user
        String media_str = (new MediaResource(this.db)).delete_all_media_for_user(id);

        return "";

    }
}