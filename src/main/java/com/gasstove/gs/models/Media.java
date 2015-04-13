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
    private Actor owner;

    private ArrayList<MediaEvent> cards = new ArrayList<MediaEvent>();

    public void addCard(MediaEvent mc){
        this.cards.add(mc);
    }

    public ArrayList<MediaEvent> getCards(){
        return cards;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public void setOwnerName(String owner_name) {
        this.owner_name = owner_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(int owner_id) {
        this.owner_id = owner_id;
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
