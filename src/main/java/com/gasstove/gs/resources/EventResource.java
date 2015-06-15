package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.Configuration;

import javax.ws.rs.*;

@Path("/events")
public class EventResource extends AbstractResource {

    @SuppressWarnings("unused")
    public EventResource(){
        this(Configuration.dbConnect);
    };

    public EventResource(String db){
        super(db);
        this.ioclass = EventIO.class;
        this.objclass = Event.class;
    }

}