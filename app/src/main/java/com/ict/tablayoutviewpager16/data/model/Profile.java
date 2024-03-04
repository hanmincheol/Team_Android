package com.ict.tablayoutviewpager16.data.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Profile {

    @SerializedName("subscribe_id")
    private String subscribeId;
    private String name;
    private String profilePath;
    private String fNum;
    private String mNum;
    private String sNum;

}