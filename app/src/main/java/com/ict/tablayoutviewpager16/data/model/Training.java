package com.ict.tablayoutviewpager16.data.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Training implements Serializable{


    private String id;
    private String eName;
    private String eVideoPath;
    private String eContent;
    private String eType;
    private String calories_per_hour;
    private String postDate;

}