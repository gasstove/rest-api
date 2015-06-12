package com.gasstove.gs.models;

import com.gasstove.gs.util.Util;
import com.google.gson.Gson;

import java.sql.ResultSet;

/**
 * Base class for all models
 */
public abstract class DBObject {

    protected int id = -1;

    public DBObject(){};

    public DBObject(String json){
        this.populate_from_Json(json);
    }

    // SERIALIZE / DESERIALIZE .......................................

    public String toJson(){
        return Util.getGson().toJson(this);
    }

    public void populate_from_Json(String json){ };

    // GET/SET .......................................................

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // COMPARE .......................................................

    /** compare with another DBObject in terms of table fields
     * @param o
     * @return
     */
    public boolean shallowEquals(DBObject o){ return false; };

    /** Compare with another DBObject in terms of table and cross-table fields
     *
     * @param o
     * @return
     */
    public boolean deepEquals(DBObject o){ return false; };

}
