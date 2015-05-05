package com.gasstove.gs.models;

import com.google.gson.Gson;

public class DBObject {

    private int id = -1;

    public String toJson(){
        return (new Gson()).toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
