package com.gasstove.gs.models;

/**
 * Created by smorris on 3/24/15.
 */
public class Media {
    private int id;
    private String type;
    private String fileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
