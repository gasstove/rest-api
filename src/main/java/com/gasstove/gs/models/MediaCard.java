package com.gasstove.gs.models;

/**
 * Created by smorris on 3/24/15.
 */
public class MediaCard {
    private Media media;
    private int numDownloads;
    private boolean shared;
    private String comment;
    private int numLikes;
    private int numDislikes;


    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
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
