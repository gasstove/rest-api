package com.gasstove.gs.models;

import java.util.ArrayList;

/**
 * Created by smorris on 3/24/15.
 */
public class Media {

    // medias table
    private int id;
    private String type;
    private String fileName;
    private int userId;

    // refs
    private ArrayList<MediaEvent> media_events;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void add_media_event(MediaEvent me){
        if(media_events==null)
            media_events = new ArrayList<MediaEvent>();
        media_events.add(me);
    }


}
