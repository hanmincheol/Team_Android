package com.ict.tablayoutviewpager16.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
public class Post {
    private int bno;

    public int getBno() {
        return bno;
    }

    public void setBno(int bno) {
        this.bno = bno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public char getDisclosureYN() {
        return disclosureYN;
    }

    public void setDisclosureYN(char disclosureYN) {
        this.disclosureYN = disclosureYN;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public int getLikesnum() {
        return likesnum;
    }

    public void setLikesnum(int likesnum) {
        this.likesnum = likesnum;
    }

    public String getProfilepath() {
        return profilepath;
    }

    public void setProfilepath(String profilepath) {
        this.profilepath = profilepath;
    }

    private String id;
    private int type;
    private String content;
    private char disclosureYN;
    private String hashTag;
    private String postDate;
    private List<String> files;
    private String likes;
    private int likesnum;
    private String profilepath;
}