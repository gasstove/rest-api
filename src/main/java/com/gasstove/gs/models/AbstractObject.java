package com.gasstove.gs.models;

import com.gasstove.gs.util.Util;

/**
 * Base class for all models
 */
public abstract class AbstractObject {

    protected int id = -1;

    public AbstractObject(){};

    public AbstractObject(String json){
        this.populate_from_Json(json);
    }

    // SERIALIZE / DESERIALIZE .......................................

    public String toJson(){
        return Util.getGson().toJson(this);
    }

    public void populate_from_Json(String json){}

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
    public boolean shallowEquals(AbstractObject o){ return false; };

    /** Compare with another DBObject in terms of table and cross-table fields
     *
     * @param o
     * @return
     */
    public boolean deepEquals(AbstractObject o){ return false; };

}
