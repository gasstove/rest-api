package com.gasstove.gs.models;

import com.google.gson.Gson;

import java.sql.Date;

/**
 * Complete information for a single event
 */
public class Event extends DBObject {

    // events table
    private String name;
    private Date openDate;
    private Date closeDate;
    private Boolean joinInvitation;
    private Boolean joinAllowByAccept;
    private Boolean joinAllowAuto;


    public Event(){};

    public Event(String json){
        Event x = (new Gson()).fromJson(json,Event.class);
        this.setName(x.getName());
        this.setOpenDate(x.getOpenDate() );
        this.setCloseDate(x.getCloseDate());
        this.setJoinAllowByAccept(x.isJoinAllowByAccept());
        this.setJoinInvitation(x.isJoinInvitation());
        this.setJoinAllowAuto(x.isJoinAllowAuto());
    }

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

    @Override
    public String toString() {
        String str = "";
        str += "event " + getId()+ "\n";
        str += "\tname: " + name+ "\n";
        str += "\topen date: " + openDate+ "\n";
        str += "\tclose date: " + closeDate+ "\n";
        str += "\tjoinInvitation: " + joinInvitation+ "\n";
        str += "\tjoinAllowbyAccept: " + joinAllowByAccept+ "\n";
        str += "\tjoinAllowAuto: " + joinAllowAuto;
        return str;
    }
}
