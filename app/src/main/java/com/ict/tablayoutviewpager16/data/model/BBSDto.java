package com.ict.tablayoutviewpager16.data.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BBSDto {
    private int bno;
    private String id;
    private int type;
    private String content;
    private String disclosureYN;
    private String hashTag;
    private String postDate;
    private List<String> files;  // 파일들을 저장할 필드
    private List<String> likes;
    private List<Integer> likesnum;
    private String profilepath;
    private int isSubto; //구독된 상태인지 체크
}
