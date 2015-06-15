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

}