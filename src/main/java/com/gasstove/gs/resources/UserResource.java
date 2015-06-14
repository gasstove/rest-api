package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.UserIO;
import com.gasstove.gs.models.User;

import javax.ws.rs.*;

@Path("/users")
public class UserResource extends AbstractResource  {

    public UserResource(String db){
        super(db);
        this.ioclass = UserIO.class;
        this.objclass = User.class;
    }

}