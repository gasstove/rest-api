package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.models.Event;

import javax.ws.rs.*;

@Path("/events")
public class EventResource extends AbstractResource {

    public EventResource(String db){
        super(db);
        this.ioclass = EventIO.class;
        this.objclass = Event.class;
    }

}