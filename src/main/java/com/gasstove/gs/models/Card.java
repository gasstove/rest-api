package com.gasstove.gs.models;

import java.util.ArrayList;

/**
 * Created by smorris on 3/24/15.
 */
public class Card {
    private Actor actor;
    private Role role;
    private Event event;
    private ArrayList<MediaCard> mediaCards = new ArrayList<MediaCard>();

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

    public ArrayList<MediaCard> getMediaCards() {
        return mediaCards;
    }

    public void setMediaCards(ArrayList<MediaCard> mediaCards) {
        this.mediaCards = mediaCards;
    }
}
