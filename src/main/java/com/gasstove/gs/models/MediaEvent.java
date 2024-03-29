package com.gasstove.gs.models;

import com.gasstove.gs.util.Time;
import com.gasstove.gs.util.Util;

public class MediaEvent extends AbstractObject {

    // table fields
    private int mediaId;
    private int eventId;
    private int numDownloads;
    private Boolean shared;
    private String comment;
    private int numLikes;
    private int numDislikes;

    // cross-table fields
    private String mediaType;
    private String mediaFileName;
    private Integer userId;
    private Time mediaDateTaken;
    private String url = "resources//images//1.jpg";

    // CONSTRUCTION .......................................................

    public MediaEvent() { }
    public MediaEvent(String json) { super(json); }

    // OVERRIDES .......................................................

    @Override
    public void populate_from_Json(String json){
        MediaEvent x = Util.getGson().fromJson(json, MediaEvent.class);
        this.setId(x.getId());
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
        this.setUrl(x.getUrl());
    }

    @Override
    public boolean shallowEquals(AbstractObject o) {
        MediaEvent x = (MediaEvent) o;
        return  mediaId==x.mediaId
                && eventId==x.eventId
                && numDownloads==x.numDownloads
                && shared.equals(x.shared)
                && comment.equals(x.comment)
                && numLikes==x.numLikes
                && numDislikes==x.numDislikes;
    }

    @Override
    public boolean deepEquals(AbstractObject o) {
        MediaEvent x = (MediaEvent) o;
        return this.shallowEquals(o)
                && mediaType.equals(x.mediaType)
                && mediaFileName.equals(x.mediaFileName)
                && userId.equals(x.userId)
                && mediaDateTaken.equals(x.mediaDateTaken)
                && url.equals(x.url);

    }

    // GET/SET .......................................................

    public Time getMediaDateTaken() {
        return mediaDateTaken;
    }

    public void setMediaDateTaken(Time mediaDateTaken) {
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

    public String getUrl() { return url; }

    public void setUrl(String url){
        this.url = url;
    }

}
