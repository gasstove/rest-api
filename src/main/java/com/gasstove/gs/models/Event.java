package com.gasstove.gs.models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by seanmorris on 3/22/15.
 */
public class Event {

    // events table
    private int id;
    private String name;
    private Date openDate;
    private Date closeDate;
    private boolean joinInvitation;
    private boolean joinAllowByAccept;
    private boolean joinAllowAuto;

    // refs
    private ArrayList<Actor> users = new ArrayList<Actor>();

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

    public ArrayList<Actor> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<Actor> users) {
        this.users = users;
    }

}
