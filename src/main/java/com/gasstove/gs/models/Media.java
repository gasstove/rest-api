package com.gasstove.gs.models;

import com.gasstove.gs.util.Time;
import com.google.gson.Gson;

public class Media extends DBObject  {

    // medias table
    private String type;
    private String fileName;
    private int userId;
    private Time dateTaken;

    // CONSTRUCTION .......................................................

    public Media(){};

    public Media(String json){
        Media x = (new Gson()).fromJson(json,Media.class);
        this.setType(x.getType());
        this.setUserId(x.getUserId());
        this.setFileName(x.getFileName());
        this.setDateTaken(x.getDateTaken());
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
