package com.gasstove.gs.models;

import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Time;

import java.util.ArrayList;

/**
 * Created by gomes on 6/9/15.
 */
public class Factory {

    /** genretate an instance of a subclass (clath) of DBObject.
     * Populate with random data
     */
    public static DBObject generate_random(Class clath){

        DBObject obj = null;
        String classname = clath.getSimpleName();
        User user = null;
        Event event = null;
        MediaEvent mediaevent = null;

        if( classname.equals("Event") || classname.equals("UserEvent")) {
            event = new Event();
            event.setName("random event");
            event.setOpenDate(new Time(0));
            event.setCloseDate(new Time(1));
            event.setJoinAllowAuto(false);
            event.setJoinAllowByAccept(false);
            event.setJoinInvitation(false);
            event.setOwnerId(1);
            obj = event;
        }

        if( classname.equals("Media") || classname.equals("UserEvent")){
            Media media = new Media();
            media.setFileName("random file name");
            media.setUserId(12345);
            media.setType("audio");
            media.setDateTaken(new Time(0));
            obj = media;
        }

        if( classname.equals("MediaEvent")){
            mediaevent = new MediaEvent();
            mediaevent.setMediaId(12345);
            mediaevent.setEventId(12345);
            mediaevent.setNumDownloads(12345);
            mediaevent.setShared(false);
            mediaevent.setComment("random comment");
            mediaevent.setNumLikes(12345);
            mediaevent.setNumDislikes(12345);
            obj = mediaevent;
        }

        if( classname.equals("User") || classname.equals("UserEvent")){
            user = new User();
            user.setFirst("Random");
            user.setLast("Name");
            obj = user;
        }

        if( classname.equals("UserEvent")){
            UserEvent userevent = new UserEvent();
            userevent.setUserId(user.getId());
            userevent.setEventId(event.getId());
            ArrayList<MediaEvent> medialist = new ArrayList<MediaEvent>();
            medialist.add(mediaevent);
            userevent.setMyMedia(medialist);
            userevent.setRole(Permissions.Role.GUEST);
            obj = userevent;
        }

        return obj;
    }

    /** modify a DBObject in some arbitrary way
     */
    public static void modify(DBObject x){
        String classname = x.getClass().getSimpleName();

        if( classname.equals("Event"))
            ((Event) x).setName("modified name");

        if( classname.equals("Media"))
            ((Media) x).setFileName("modified file name");

        if( classname.equals("MediaEvent"))
            ((MediaEvent) x).setComment("modified comment");

        if( classname.equals("User"))
            ((User) x).setFirst("modified first");

        if( classname.equals("UserEvent")){
            // no strings to modify here
        }

    }


}
