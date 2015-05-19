package com.gasstove.gs.models;

import com.gasstove.gs.util.Time;
import com.gasstove.gs.util.Util;

public class Event extends DBObject {

    private String name;
    private Time openDate;
    private Time closeDate;
    private Boolean joinInvitation;
    private Boolean joinAllowByAccept;
    private Boolean joinAllowAuto;

    // CONSTRUCTION .......................................................

    public Event(){};

    public Event(String json){

        Event x = Util.getGson().fromJson(json, Event.class);

        this.setId(x.getId());
        this.setName(x.getName());
        this.setOpenDate(x.getOpenDate() );
        this.setCloseDate(x.getCloseDate());
        this.setJoinAllowByAccept(x.isJoinAllowByAccept());
        this.setJoinInvitation(x.isJoinInvitation());
        this.setJoinAllowAuto(x.isJoinAllowAuto());
    }

    // OVERRIDES .......................................................

    @Override
    public String toString() {
        String str = "";
        str += "event\n";
        str += "\tid: " + id+ "\n";
        str += "\tname: " + name+ "\n";
        str += "\topen date: " + openDate+ "\n";
        str += "\tclose date: " + closeDate+ "\n";
        str += "\tjoinInvitation: " + joinInvitation+ "\n";
        str += "\tjoinAllowbyAccept: " + joinAllowByAccept+ "\n";
        str += "\tjoinAllowAuto: " + joinAllowAuto;
        return str;
    }

    // GET/SET .......................................................

    public Time getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Time closeDate) {
        this.closeDate = closeDate;
    }

    public Time getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Time date) {
        this.openDate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isJoinInvitation() {
        return joinInvitation==null ? false : joinInvitation;
    }

    public void setJoinInvitation(boolean joinInvitation) {
        this.joinInvitation = joinInvitation;
    }

    public Boolean isJoinAllowByAccept() {
        return joinAllowByAccept==null ? false : joinAllowByAccept;
    }

    public void setJoinAllowByAccept(boolean joinAllowByAccept) {
        this.joinAllowByAccept = joinAllowByAccept;
    }

    public Boolean isJoinAllowAuto() {
        return joinAllowAuto==null ? false : joinAllowAuto;
    }

    public void setJoinAllowAuto(boolean joinAllowAuto) {
        this.joinAllowAuto = joinAllowAuto;
    }

}
