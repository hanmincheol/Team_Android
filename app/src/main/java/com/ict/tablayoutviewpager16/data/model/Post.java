package com.ict.tablayoutviewpager16.data.model;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
public class Post implements Serializable {

    private String id;
    private int type;
    private String content;
    private char disclosureYN;
    private String hashTag;
    private String postDate;
    private List<String> files;
    private List<String> likes;
    private List<Integer> likesnum;
    private String profilepath;
}