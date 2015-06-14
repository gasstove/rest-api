package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.MediaIO;
import com.gasstove.gs.models.Media;

import javax.ws.rs.*;

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

}