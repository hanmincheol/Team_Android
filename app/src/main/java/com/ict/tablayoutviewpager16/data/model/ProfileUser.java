package com.ict.tablayoutviewpager16.data.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileUser {

    private String id;
    private String name;
    private String date;
    private String profilePath;
    private String proIntroduction;
    private String backfiletype;
    private List<UserProfileFriend> friendsList;

}