package com.gasstove.gs.models;

import com.gasstove.gs.util.Util;
import com.google.gson.Gson;

import java.sql.ResultSet;

/**
 * Base class for all models
 */
public class DBObject {

    protected int id = -1;

    // SERIALIZE .....................................................

    public String toJson(){
        return Util.getGson().toJson(this);
    }

    // GET/SET .......................................................

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
