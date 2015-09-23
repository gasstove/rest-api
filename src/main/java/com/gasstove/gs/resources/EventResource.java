package com.gasstove.gs.resources;

import com.gasstove.gs.dbaccess.EventIO;
import com.gasstove.gs.dbaccess.MediaEventIO;
import com.gasstove.gs.dbaccess.UserEventIO;
import com.gasstove.gs.models.Event;
import com.gasstove.gs.util.Configuration;
import com.gasstove.gs.util.Response;

import javax.ws.rs.*;
import java.util.ArrayList;

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
    public String delete(@PathParam("id") String id,@QueryParam("gaswrapper") String callback) {
        return this.delete_event(id,Configuration.FORMAT.jsonp,callback);
    }

    public String deleteEventsOwnedBy(String user_id_str,@QueryParam("gaswrapper") String callback){
        return this.deleteEventsOwnedBy(user_id_str,Configuration.FORMAT.jsonp,callback);
    }

    ////////////////////////////////////////////////////////////
    // overloads
    ////////////////////////////////////////////////////////////

    public String delete_event(String id,Configuration.FORMAT response_format,String callback) {

        UserEventIO userEventIO = null;
        MediaEventIO mediaEventIO = null;
        boolean success = false;

        try {
            int event_id = Integer.parseInt(id);

            // delete from user_events
            userEventIO = new UserEventIO(db);
            userEventIO.deleteForEventId(event_id);

            // delete from media_events
            mediaEventIO = new MediaEventIO(db);
            mediaEventIO.deleteForEventId(event_id);

            // delete row in events table, get a response
            String resp_json = super.deleteX(id);
            success = (new Response(resp_json)).success;

        } catch (Exception e) {
            e.printStackTrace();
            return (new Response(false, "Event deletion failed",null)).format(response_format,callback);
        } finally {
            if(userEventIO!=null)
                userEventIO.close();
            if(mediaEventIO!=null)
                mediaEventIO.close();
            Response response = success ?
                    new Response(true, "Event successfully deleted",null) :
                    new Response(false, "Event deletion failed",null) ;
            return response.format(response_format,callback);
        }

    }

    public String delete_event(String id){
        return this.delete_event(id,Configuration.FORMAT.json,"");
    }

    public String deleteEventsOwnedBy(String user_id_str,Configuration.FORMAT response_format,String callback){

        int user_id = Integer.parseInt(user_id_str);

        // use UserEventIO to get all events for this user
        UserEventIO usereventIO = new UserEventIO(db);
        ArrayList<Event> events = usereventIO.getEventsForUser(user_id);
        usereventIO.close();

        // use Event resource to delete all those events
        boolean success = true;
        for(Event event : events){
            Response response = new Response(deleteX(String.format("%d",event.getId())));
            success &= response.success;
        }

        Response return_resp = success ?
                new Response(true, "Events successfully deleted",null) :
                new Response(false, "Events deletion failed",null);

        return return_resp.format(response_format,"");
    }

    public String deleteEventsOwnedBy(String user_id_str){
        return this.deleteEventsOwnedBy(user_id_str,Configuration.FORMAT.json,"");
    }

}