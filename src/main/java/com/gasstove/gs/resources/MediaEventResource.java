package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Response;
import com.gasstove.gs.util.Util;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("/mediaevents")
public class MediaEventResource extends AbstractResource {

//    @SuppressWarnings("unused")
//    public MediaEventResource(){
//        this(Configuration.getDB());
//    };

    public MediaEventResource(String db,Configuration.FORMAT response_format) {
        super(db,response_format);
        this.ioclass = MediaEventIO.class;
        this.objclass = MediaEvent.class;
    }

    ////////////////////////////////////////////////////////////
    // '/event/#/user/#'
    ////////////////////////////////////////////////////////////

    @Path("/event/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getSharedMediaForEvent(@PathParam("eventId") String eventId,@QueryParam("gaswrapper") String callback) {

        String returnString;
        MediaEventIO io = null;
        try {
            io = (MediaEventIO) get_connection();
            ArrayList<MediaEvent> mediaevents = io.getSharedMediaForEvent(Integer.parseInt(eventId));
            returnString = Util.formatArray(mediaevents,response_format,callback);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnString = (new Response(false, exp.getMessage(), null)).format(response_format,callback);
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
    public String getMediaForUserAndEvent(@PathParam("eventId") String eventId,@PathParam("userId") String userId,@QueryParam("gaswrapper") String callback) {

        String returnString;
        MediaEventIO io = null;
        try {
            io = (MediaEventIO) get_connection();
            int uId = Integer.parseInt(userId);
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = io.getMediaForUserAndEvent(uId,eId);
            returnString = Util.formatArray(mediaevents,response_format,callback);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnString = (new Response(false, exp.getMessage(), null)).format(response_format,callback);
        } finally {
            if(io!=null)
                io.close();
        }
        return returnString;
    }

}
