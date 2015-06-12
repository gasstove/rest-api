package com.gasstove.gs.models;

import com.gasstove.gs.util.Util;
import com.google.gson.Gson;

public class User extends DBObject  {

    // table fields
    private String first;
    private String last;

    // cross table fields

    // CONSTRUCTION .......................................................

    public User() { }
    public User(String json) { super(json); }


    // OVERRIDES .......................................................

    @Override
    public void populate_from_Json(String json){
        User x = Util.getGson().fromJson(json, User.class);
        this.setId(x.getId());
        this.setFirst(x.getFirst());
        this.setLast(x.getLast());
    }

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


    // DBObjectInterface ..................................................

    @Override
    public boolean shallowEquals(DBObject o) {
        User x = (User) o;
        return first.equals(x.first)
               && last.equals(x.last);
    }

    @Override
    public boolean deepEquals(DBObject o) {
        return this.shallowEquals(o);
    }

}
