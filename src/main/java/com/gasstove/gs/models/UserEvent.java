package com.gasstove.gs.models;

import com.gasstove.gs.util.Time;

import java.util.ArrayList;

public class UserEvent extends DBObject {

    private User user;
    private Role role;
    private Event event;
    private Time joinDate;

    // refs
    private ArrayList<MediaEvent> myMedia = new ArrayList<MediaEvent>();
    private ArrayList<MediaEvent> sharedMedia = new ArrayList<MediaEvent>();

    // CONSTRUCTION .......................................................


    // GET/SET .......................................................

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

    public ArrayList<MediaEvent> getMyMedia() {
        return myMedia;
    }

    public void setMyMedia(ArrayList<MediaEvent> myMedia) {
        this.myMedia = myMedia;
    }
}
