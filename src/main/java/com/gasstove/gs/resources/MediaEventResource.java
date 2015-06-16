package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Response;
import com.gasstove.gs.util.Util;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/mediaevents")
public class MediaEventResource extends AbstractResource {

    @SuppressWarnings("unused")
    public MediaEventResource(){
        this(Configuration.dbConnect);
    };

    public MediaEventResource(String db) {
        super(db);
        this.ioclass = MediaEventIO.class;
        this.objclass = MediaEvent.class;
    }

    ////////////////////////////////////////////////////////////
    // '/event/#/user/#'
    ////////////////////////////////////////////////////////////

    @Path("/event/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getSharedMediaForEvent(@PathParam("eventId") String eventId) {
        String returnJSON;
        MediaEventIO io = null;
        try {
            io = (MediaEventIO) get_connection();
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = io.getSharedMediaForEvent(eId);
            returnJSON = Util.getGson().toJson(mediaevents);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            io.close();
        }
        return returnJSON;
    }

    ////////////////////////////////////////////////////////////
    // '/event/#/user/#'
    ////////////////////////////////////////////////////////////

    @Path("/event/{eventId: [0-9]+}/user/{userId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMediaForUserAndEvent(@PathParam("eventId") String eventId,@PathParam("userId") String userId) {
        String returnJSON;
        MediaEventIO io = null;
        try {
            io = (MediaEventIO) get_connection();
            int uId = Integer.parseInt(userId);
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = io.getMediaForUserAndEvent(uId,eId);
            returnJSON = Util.getGson().toJson(mediaevents);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            io.close();
        }
        return returnJSON;
    }

}
