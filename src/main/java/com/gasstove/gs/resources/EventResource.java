package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.AbstractIO;
import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.dbaccess.UserEventIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Response;

import javax.ws.rs.*;

@Path("/events")
public class EventResource extends AbstractResource {

    @SuppressWarnings("unused")
    public EventResource(){
        this(Configuration.getDB());
    }

    public EventResource(String db){
        super(db);
        this.ioclass = EventIO.class;
        this.objclass = Event.class;
    }

    /**
     * Delete an event.
     *  1) delete a row from the events table
     *  2) UserEventIO.deleteForEventId
     *  3) MediaEventIO.deleteForMediaId
     * @param id
     * @return
     */
    @Override
    public String delete(@PathParam("id") String id) {

        boolean success = true;
        UserEventIO userEventIO = null;
        MediaEventIO mediaEventIO = null;

        // TODO : THE main table delete has to go last!!!
        // TODO: TRANSACTIONS!
        try {
            int event_id = Integer.parseInt(id);

            // delete row in events table, get a response
            String resp_json = super.delete(id);
            success &= (new Response(resp_json)).success;

            // delete from user_events
            if(success) {
                userEventIO = new UserEventIO(db);
                success &= userEventIO.deleteForEventId(event_id);
            }

            // delete from media_events
            if(success) {
                mediaEventIO = new MediaEventIO(db);
                success &= mediaEventIO.deleteForEventId(event_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(userEventIO!=null)
                userEventIO.close();
            if(mediaEventIO!=null)
                mediaEventIO.close();
            Response response = success ?
                    new Response(true, "Event successfully deleted",null) :
                    new Response(false, "Event deletion failed",null) ;
            return response.toJSON();
        }

    }

    // TODO
    public String deleteEventsOwnedBy(String user_id){

        // user UserEventResource to get all events for this user

        // use Event resource to delete all those events

        return "";
    }

}