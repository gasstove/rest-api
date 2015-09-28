package com.gasstove.gs.models;

import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Time;

/**
 * Created by gomes on 6/9/15.
 */
public class Factory {

    /** genretate an instance of a subclass (clath) of DBObject.
     * Populate with random data
     */
    public static AbstractObject generate_random(Class clath){

        AbstractObject obj = null;
        String classname = clath.getSimpleName();
        User user;
        Event event;
        MediaEvent mediaevent;

        if( classname.equals("Event") ) {
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

        if( classname.equals("Media") ){
            Media media = new Media();
            media.setFileName("random file name");
            media.setUserId(12345);
            media.setUrl("aaa");
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
            userevent.setUserId(12345);
            userevent.setEventId(12345);
            userevent.setRole(Permissions.Role.GUEST);
            obj = userevent;
        }

        return obj;
    }

    /** modify a DBObject in some arbitrary way
     */
    public static void modify(AbstractObject x){
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
