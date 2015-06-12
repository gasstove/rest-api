package com.gasstove.gs.models;

import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Time;
import com.gasstove.gs.util.Util;

import java.util.ArrayList;

public class UserEvent extends DBObject {

    // table fields
    private int userId;
    private int eventId;
    private Permissions.Role role;

    // cross table fields
    private ArrayList<MediaEvent> myMedia = new ArrayList<MediaEvent>();
    private ArrayList<MediaEvent> sharedMedia = new ArrayList<MediaEvent>();

    // CONSTRUCTION .......................................................

    public UserEvent() { }
    public UserEvent(String json) { super(json); }

    // OVERRIDES .......................................................

    @Override
    public void populate_from_Json(String json){
        UserEvent x = Util.getGson().fromJson(json, UserEvent.class);
        this.setId(x.getId());
        this.setUserId(x.getUserId());
        this.setEventId(x.getEventId());
        this.setRole(x.getRole());
    }

    // GET/SET .......................................................

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Permissions.Role getRole() {
        return role;
    }

    public void setRole(Permissions.Role role) {
        this.role = role;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public ArrayList<MediaEvent> getMyMedia() {
        return myMedia;
    }

    public void setMyMedia(ArrayList<MediaEvent> myMedia) {
        this.myMedia = myMedia;
    }

    // DBObjectInterface ..................................................

    @Override
    public boolean shallowEquals(DBObject o) {
        UserEvent x = (UserEvent) o;
        return  this.userId==x.userId
             && this.eventId==x.eventId
             && this.role.compareTo(x.role)==0;
    }

    @Override
    public boolean deepEquals(DBObject o) {
        UserEvent x = (UserEvent) o;
        boolean result = this.shallowEquals(x);

        // assumes they are in the same order - not good.
        if(x.myMedia!=null && myMedia!=null){
            if(x.myMedia.size()!=myMedia.size())
                return false;
            for(int i=0;i<myMedia.size();i++)
                result &= myMedia.get(i).deepEquals(x.myMedia.get(i));
        }

        if(x.sharedMedia!=null && sharedMedia!=null){
            if(x.sharedMedia.size()!=sharedMedia.size())
                return false;
            for(int i=0;i<sharedMedia.size();i++)
                result &= sharedMedia.get(i).deepEquals(x.sharedMedia.get(i));
        }

        return result;

    }

}
