package com.ict.tablayoutviewpager16.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {
    private String id;
    private String pwd;
    private String name;
    private String gender;
    private String b_day;
    private String height;
    private String weight;
    private String tel;
    private String userAddress;
    private String goal_No;
    private String regidate;
    private String authority;
    private String point;

    //소셜로그인
    private String profileimage;
    private String provider;
    private String pro_filepath;
}
