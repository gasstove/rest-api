package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.MediaReader;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.MediaEvent;
import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Restful Jersey based servlet for Image Resource
 * <p/>
 * Reference:
 * http://docs.oracle.com/cd/E19226-01/820-7627/giepu/index.html
 */

@Path("/medias")
public class MediaResource {

    /**
     *  This is the root resource for medias. It returns all medias
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMediasBasicInfo() {
        String returnJSON = "";
        MediaReader mr = null;
        try {
            mr = new MediaReader();
            ArrayList<Media> medias = mr.getMediasBasicInfo();
            Gson gson = new Gson();
            returnJSON = gson.toJson(medias);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
            mr.close();
        }
        return returnJSON;
    }

    /**
     * Restful method to return media object by id
     *
     * @param mediaId The id of the media to be loaded
     * @return JSON representation of Media object
     */
    @Path("/{mediaId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMediaBasicInfo(@PathParam("mediaId") String mediaId) {
        String returnJSON;
        MediaReader mr = null;
        try {
            mr = new MediaReader();
            Media media = mr.getMediaBasicInfo(Integer.parseInt(mediaId));
            Gson gson = new Gson();
            returnJSON = gson.toJson(media);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
            mr.close();
        }
        return returnJSON;
    }

    @Path("/event/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getSharedMediaForEvent(@PathParam("eventId") String eventId) {
        String returnJSON;
        MediaReader mr = null;
        try {
            mr = new MediaReader();
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = mr.getSharedMediaForEvent(eId);
            Gson gson = new Gson();
            returnJSON = gson.toJson(mediaevents);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
            mr.close();
        }
        return returnJSON;
    }

    @Path("/event/{eventId: [0-9]+}/user/{userId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMediaForUserAndEvent(@PathParam("eventId") String eventId,@PathParam("userId") String userId) {
        String returnJSON;
        MediaReader mr = null;
        try {
            mr = new MediaReader();
            int uId = Integer.parseInt(userId);
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = mr.getMediaForUserAndEvent(uId,eId);
            Gson gson = new Gson();
            returnJSON = gson.toJson(mediaevents);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
            mr.close();
        }
        return returnJSON;
    }

}