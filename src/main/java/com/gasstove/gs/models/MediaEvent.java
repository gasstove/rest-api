package com.gasstove.gs.models;

import com.google.gson.Gson;

import java.util.Date;

/**
 * Media information in an event context
 */
public class MediaEvent extends DBObject {

    // media_mapping table
    private int mediaId;
    private int eventId;
    private int numDownloads;
    private Boolean shared;
    private String comment;
    private int numLikes;
    private int numDislikes;

    // media information
    private String mediaType;
    private String mediaFileName;
    private Integer userId;
    private Date mediaDateTaken;

    public MediaEvent(){};

    public MediaEvent(String json){
        MediaEvent x = (new Gson()).fromJson(json,MediaEvent.class);
        this.setMediaId(x.getMediaId());
        this.setEventId(x.getEventId());
        this.setNumDownloads(x.getNumDownloads());
        this.setShared(x.isShared());
        this.setComment(x.getComment());
        this.setNumLikes(x.getNumLikes());
        this.setNumDislikes(x.getNumDislikes());
        this.setMediaType(x.getMediaType());
        this.setMediaFileName(x.getMediaFileName());
        this.setUserId(x.getUserId());
        this.setMediaDateTaken(x.getMediaDateTaken());
    }

    public Date getMediaDateTaken() {
        return mediaDateTaken;
    }

    public void setMediaDateTaken(Date mediaDateTaken) {
        this.mediaDateTaken = mediaDateTaken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaFileName() {
        return mediaFileName;
    }

    public void setMediaFileName(String mediaFileName) {
        this.mediaFileName = mediaFileName;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getNumDownloads() {
        return numDownloads;
    }

    public void setNumDownloads(int numDownloads) {
        this.numDownloads = numDownloads;
    }

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumDislikes() {
        return numDislikes;
    }

    public void setNumDislikes(int numDislikes) {
        this.numDislikes = numDislikes;
    }
}
