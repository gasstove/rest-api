package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.MediaReader;
import com.gasstove.gs.models.Media;
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
    public String getMedias() {
        String returnJSON = "";
        try {
            ArrayList<Media> medias = MediaReader.getMedias();
            Gson gson = new Gson();
            returnJSON = gson.toJson(medias);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
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
    public String getMedia(@PathParam("mediaId") String mediaId) {

        String returnJSON = "";

        try {
            Media media = MediaReader.getMedia(Integer.parseInt(mediaId));
            Gson gson = new Gson();
            returnJSON = gson.toJson(media);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
        }
        return returnJSON;
    }


    /**
     * Restful method to return list of media for a given event id
     *
     * @param eventId The id of the media to be loaded
     * @return JSON representation of Media object
     */
    @Path("/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMediaForEvent(@PathParam("eventId") String eventId) {
        String returnJSON = "";
        try {
            ArrayList<Media> medias = MediaReader.getMediaForEvent(Integer.parseInt(eventId));
            Gson gson = new Gson();
            returnJSON = gson.toJson(medias);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
        }
        return returnJSON;
    }


}