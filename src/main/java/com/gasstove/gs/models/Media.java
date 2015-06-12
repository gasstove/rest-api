package com.gasstove.gs.models;

import com.gasstove.gs.util.Time;
import com.gasstove.gs.util.Util;

public class Media extends AbstractObject {

    // table fields
    private String type;
    private String fileName;
    private int userId;
    private Time dateTaken;

    // cross table fields

    // CONSTRUCTION .......................................................

    public Media() { }
    public Media(String json) { super(json); }

    // OVERRIDES .......................................................

    @Override
    public void populate_from_Json(String json){
        Media x = Util.getGson().fromJson(json,Media.class);
        this.setId(x.getId());
        this.setType(x.getType());
        this.setUserId(x.getUserId());
        this.setFileName(x.getFileName());
        this.setDateTaken(x.getDateTaken());
    }

    @Override
    public boolean shallowEquals(AbstractObject o) {
        Media x = (Media) o;
        return type.equals(x.type)
                && fileName.equals(x.fileName)
                && userId==x.userId
                && dateTaken.equals(x.dateTaken);
    }

    @Override
    public boolean deepEquals(AbstractObject o) {
        return this.shallowEquals(o);
    }

    // GET/SET .......................................................

    public Time getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Time dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


}
