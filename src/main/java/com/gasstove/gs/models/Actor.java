package com.gasstove.gs.models;

import java.util.ArrayList;

/**
 * Created by smorris on 3/24/15.
 */
public class Actor {
    private int id;
    private String first;
    private String last;
    private boolean is_subscriber;
    private String contact_method;
    private ArrayList<Event> events = new ArrayList<Event>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public boolean isIs_subscriber() {
        return is_subscriber;
    }

    public void setIs_subscriber(boolean is_subscriber) {
        this.is_subscriber = is_subscriber;
    }

    public String getContact_method() {
        return contact_method;
    }

    public void setContact_method(String contact_method) {
        this.contact_method = contact_method;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
