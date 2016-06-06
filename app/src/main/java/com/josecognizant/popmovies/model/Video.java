package com.josecognizant.popmovies.model;

/**
 * Class representing a video of a movie
 * Created by Jose on 06/06/2016.
 */
public class Video {
    private String urlKey;
    private String name;
    private String site;
    private int size;
    private String type;

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String key) {
        this.urlKey = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
