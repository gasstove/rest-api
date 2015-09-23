package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.dbaccess.MediaIO;
import com.gasstove.gs.dbaccess.UserEventIO;
import com.gasstove.gs.models.Media;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Response;

import javax.ws.rs.*;

/**
 * Restful Jersey based servlet
 * <p/>
 * Reference:
 * http://docs.oracle.com/cd/E19226-01/820-7627/giepu/index.html
 */

@Path("/medias")
public class MediaResource extends AbstractResource  {

    @SuppressWarnings("unused")
    public MediaResource(){
        this(Configuration.getDB());
    };

    public MediaResource(String db){
        super(db);
        this.ioclass = MediaIO.class;
        this.objclass = Media.class;
    }

    /**
     * Delete media item,
     *  1) delete a row from the media table
     *  2) MediaEventIO.deleteForMediaId
     * @param id
     * @return
     */
    @Override
    public String delete(@PathParam("id") String id,@QueryParam("gaswrapper") String callback) {
        return this.delete_media(id,Configuration.FORMAT.jsonp,callback);
    }

    public String deleteMediaOwnedBy(String user_id,@QueryParam("gaswrapper") String callback){
        return this.deleteMediaOwnedBy(user_id,Configuration.FORMAT.jsonp,callback);
    }

    ////////////////////////////////////////////////////////////
    // overloads
    ////////////////////////////////////////////////////////////

    public String delete_media(String id,Configuration.FORMAT response_format,String callback) {

        boolean success = true;
        MediaEventIO mediaEventIO = null;

        try {
            int media_id = Integer.parseInt(id);

            // delete row in events table, get a response
            String resp_json = super.deleteX(id);
            success &= (new Response(resp_json)).success;

            // delete from media_events
            if(success) {
                mediaEventIO = new MediaEventIO(db);
                mediaEventIO.deleteForMediaId(media_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return (new Response(false, "Media deletion failed",null)).format(response_format,callback);
        } finally {
            if(mediaEventIO!=null)
                mediaEventIO.close();
            Response response = success ?
                    new Response(true, "Media successfully deleted",null) :
                    new Response(false, "Media deletion failed",null) ;
            return response.format(response_format,callback);
        }
    }

    public String delete_media(String id){
        return this.delete_media(id,Configuration.FORMAT.json,"");
    }

    public String deleteMediaOwnedBy(String user_id,Configuration.FORMAT response_format,String callback){

        MediaIO io = null;
        Response response;
        try {
            io = (MediaIO) get_connection();
            io.delete_all_media_for_user(Integer.parseInt(user_id));
        } catch (Exception exp) {
            exp.printStackTrace();
            response = new Response(false, exp.getMessage(), null);
            return response.format(response_format,callback);
        } finally {
            if(io!=null)
                io.close();
            response = new Response(true, "Media successfully deleted",null);
            return response.format(response_format,callback);
        }

    }

    public String deleteMediaOwnedBy(String user_id) {
        return this.deleteMediaOwnedBy(user_id,Configuration.FORMAT.json,"");
    }

}