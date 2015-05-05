package com.gasstove.gs.models;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

/**
 * Complete information for a single media item
 */
public class Media extends DBObject  {

    // medias table
    private String type;
    private String fileName;
    private int userId;
    private Date dateTaken;

    public Media(){};

    public Media(String json){
        Media x = (new Gson()).fromJson(json,Media.class);
        this.setType(x.getType());
        this.setUserId(x.getUserId());
        this.setFileName(x.getFileName());
        this.setDateTaken(x.getDateTaken());
    }

    public Date getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(Date dateTaken) {
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
