package com.ict.tablayoutviewpager16.data.model;

import java.io.Serializable;

public class Training implements Serializable {

    private String id;
    private String Imgpath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgpath() {
        return Imgpath;
    }

    public void setImgpath(String imgpath) {
        Imgpath = imgpath;
    }

    public Training(String id, String imgpath) {
    }
}
