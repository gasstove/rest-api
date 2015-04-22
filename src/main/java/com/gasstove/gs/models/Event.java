package com.gasstove.gs.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Complete information for a single event
 */
public class Event {

    // events table
    private int id;
    private String name;
    private Date openDate;
    private Date closeDate;
    private Boolean joinInvitation;
    private Boolean joinAllowByAccept;
    private Boolean joinAllowAuto;

    // refs
//    private ArrayList<User> users;
//    private ArrayList<MediaEvent> medias;

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date date) {
        this.openDate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isJoinInvitation() {
        return joinInvitation;
    }

    public void setJoinInvitation(boolean joinInvitation) {
        this.joinInvitation = joinInvitation;
    }

    public boolean isJoinAllowByAccept() {
        return joinAllowByAccept;
    }

    public void setJoinAllowByAccept(boolean joinAllowByAccept) {
        this.joinAllowByAccept = joinAllowByAccept;
    }

    public boolean isJoinAllowAuto() {
        return joinAllowAuto;
    }

    public void setJoinAllowAuto(boolean joinAllowAuto) {
        this.joinAllowAuto = joinAllowAuto;
    }

//    public ArrayList<User> getUsersBasicInfo() {
//        return users;
//    }
//
//    public void setUsers(ArrayList<User> users) {
//        this.users = users;
//    }
//
//    public ArrayList<MediaEvent> getMediasBasicInfo() {
//        return medias;
//    }
//
//    public void setMedias(ArrayList<MediaEvent> medias) {
//        this.medias = medias;
//    }
}
