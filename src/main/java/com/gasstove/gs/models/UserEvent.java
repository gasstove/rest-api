package com.gasstove.gs.models;

import com.gasstove.gs.util.Permissions;
import com.gasstove.gs.util.Util;

public class UserEvent extends AbstractObject {

    // table fields
    private int userId;
    private int eventId;
    private Permissions.Role role;

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

    @Override
    public boolean shallowEquals(AbstractObject o) {
        UserEvent x = (UserEvent) o;
        return  this.userId==x.userId
                && this.eventId==x.eventId
                && this.role.compareTo(x.role)==0;
    }

    @Override
    public boolean deepEquals(AbstractObject o) {
        UserEvent x = (UserEvent) o;
        boolean result = this.shallowEquals(x);
        return result;
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

}
