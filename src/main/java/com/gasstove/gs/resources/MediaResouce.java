package com.gasstove.gs.resources;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Restful Jersey based servlet for Image Resource
 * <p/>
 * Reference:
 * http://docs.oracle.com/cd/E19226-01/820-7627/giepu/index.html
 */

@Path("/images")
public class MediaResouce {

    /**media_mapping
     *
     */
    @Path("/")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getMedias() {

        String returnJSON = "";

        try {
            Gson gson = new Gson();
            returnJSON = gson.toJson("{images:[{name:\"image1\", src:\"asdf\"}]}");
        } catch (Exception exp) {
            exp.printStackTrace();
            returnJSON = Response.JSONMessage(false, exp.getMessage(), null);
        } finally {
        }
        return returnJSON;
    }
}