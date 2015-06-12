package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.MediaIO;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.models.MediaEvent;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Util;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Restful Jersey based servlet
 * <p/>
 * Reference:
 * http://docs.oracle.com/cd/E19226-01/820-7627/giepu/index.html
 */

@Path("/medias")
public class MediaResource extends AbstractResource  {

    public MediaResource(String db){
        super(db);
        this.ioclass = MediaIO.class;
        this.objclass = Media.class;
    }

    ////////////////////////////////////////////////////////////
    // '/'
    ////////////////////////////////////////////////////////////

    /**
     *  This is the root resource for medias. It returns all medias
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMediasBasicInfo() {
        String returnJSON = "";
        MediaIO mediaIO = null;
        try {
            mediaIO = (MediaIO) get_connection();
            ArrayList<Media> medias = mediaIO.getAll();
            returnJSON = Util.getGson().toJson(medias);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            mediaIO.close();
        }
        return returnJSON;
    }

    ////////////////////////////////////////////////////////////
    // '/#'
    ////////////////////////////////////////////////////////////

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
        MediaIO mediaIO = null;
        try {
            mediaIO = (MediaIO) get_connection();
            Media media = mediaIO.getWithId(Integer.parseInt(mediaId));
            returnJSON = media.toJson();
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            mediaIO.close();
        }
        return returnJSON;
    }

    ////////////////////////////////////////////////////////////
    // '/event/#'
    ////////////////////////////////////////////////////////////

    @Path("/event/{eventId: [0-9]+}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getSharedMediaForEvent(@PathParam("eventId") String eventId) {
        String returnJSON;
        MediaIO mediaIO = null;
        try {
            mediaIO = (MediaIO) get_connection();
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = mediaIO.getSharedMediaForEvent(eId);
            returnJSON = Util.getGson().toJson(mediaevents);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            mediaIO.close();
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
        MediaIO mediaIO = null;
        try {
            mediaIO = (MediaIO) get_connection();
            int uId = Integer.parseInt(userId);
            int eId = Integer.parseInt(eventId);
            ArrayList<MediaEvent> mediaevents = mediaIO.getMediaForUserAndEvent(uId,eId);
            returnJSON = Util.getGson().toJson(mediaevents);
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = (new Response(false, exp.getMessage(), null)).toJSON();
        } finally {
            mediaIO.close();
        }
        return returnJSON;
    }

}