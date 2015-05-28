package com.gasstove.gs.models;

import com.gasstove.gs.util.Util;
import com.google.gson.Gson;

public class User extends DBObject  {

    private String first;
    private String last;

    // CONSTRUCTION .......................................................

    public User(){};

    public User(String json){
        User x = Util.getGson().fromJson(json, User.class);

        this.setId(x.getId());
        this.setFirst(x.getFirst());
        this.setLast(x.getLast());
    }

    // OVERRIDES .......................................................

    @Override
    public String toString() {
        String str = "";
        str += "\tid: " + id+ "\n";
        str += "\tfirst: " + first+ "\n";
        str += "\tlast: " + last;
        return str;
    }

    // GET/SET ............................................................

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

}
