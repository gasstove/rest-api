package com.gasstove.gs.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by smorris on 3/24/15.
 */
public class UserEvent {

    // user_event table
    private User user;
    private Role role;
    private Event event;
    private Date joinDate;

    // refs
    private ArrayList<MediaEvent> mediaEvents = new ArrayList<MediaEvent>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ArrayList<MediaEvent> getMediaEvents() {
        return mediaEvents;
    }

    public void setMediaEvents(ArrayList<MediaEvent> mediaEvents) {
        this.mediaEvents = mediaEvents;
    }
}
