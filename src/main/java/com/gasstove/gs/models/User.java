package com.gasstove.gs.models;

import com.google.gson.Gson;

public class User extends DBObject  {

    private String first;
    private String last;
    private Boolean isSubscriber;
    private String contactMethod;

    // CONSTRUCTION .......................................................

    public User(){};

    public User(String json){
        User x = (new Gson()).fromJson(json,User.class);
        this.setFirst(x.getFirst());
        this.setLast(x.getLast());
        this.setIsSubscriber(x.isSubscriber());
        this.setContactMethod(x.getContactMethod());
    }

    // GET/SET .......................................................

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

    public void setIsSubscriber(boolean isSubscriber) {
        this.isSubscriber = isSubscriber;
    }

    public boolean isSubscriber() {
        return isSubscriber;
    }

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }


}
