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
    private int actor_id;

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

    public int getActor_id() {
        return actor_id;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public void add_media_event(MediaEvent me){
        if(media_events==null)
            media_events = new ArrayList<MediaEvent>();
        media_events.add(me);
    }


}
