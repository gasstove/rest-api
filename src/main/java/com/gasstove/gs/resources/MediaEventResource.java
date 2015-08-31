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
        this(Configuration.getDB());
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
        String returnString;
        MediaEventIO io = null;
        try {
            io = (MediaEventIO) get_connection();
            ArrayList<MediaEvent> mediaevents = io.getSharedMediaForEvent(Integer.parseInt(eventId));
            returnString = Util.formatArray(mediaevents,response_format);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnString = (new Response(false, exp.getMessage(), null)).format(response_format);
        } finally {
            if(io!=null)
                io.close();
        }
        return returnString;
    }

    ////////////////////////////////////////////////////////////
    // '/event/#/user/#'
    ////////////////////////////////////////////////////////////

    @Path("/event/{eventId: [0-9]+}/user/{userId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMediaForUserAndEvent(@PathParam("eventId") String eventId,@PathParam("userId") String userId) {
        String returnString;
        MediaEventIO io = null;
        try {
            io = (MediaEventIO) get_connection();
            int uId = Integer.parseInt(userId);
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = io.getMediaForUserAndEvent(uId,eId);
            returnString = Util.formatArray(mediaevents,response_format);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnString = (new Response(false, exp.getMessage(), null)).format(response_format);
        } finally {
            if(io!=null)
                io.close();
        }
        return returnString;
    }

}
