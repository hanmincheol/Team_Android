package com.ict.tablayoutviewpager16.data.model;

public class Post {

    private String content;
    private String title;
    private String id;
    private String Imgpath;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public Post(String id, String content, String title, String imgpath) {
    }
}
