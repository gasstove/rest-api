package com.gasstove.gs.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by smorris on 3/24/15.
 */
public class ActorEvent {

    // actor_event table
    private Actor actor;
    private Role role;
    private Event event;
    private Date joinDate;

    // refs
    private ArrayList<MediaEvent> mediaEvents = new ArrayList<MediaEvent>();

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
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
