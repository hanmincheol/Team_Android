package com.ict.tablayoutviewpager16.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SCHDto {
    private String sno; // 글번호
    private String id; // 아이디
    private String stitle; // 제목
    private String scontent; // 내용
    private String seat;  // 음식
    private String sexer; // 운동
    private String sdest; // 목적지
    private String cal; // 타입
    private String sarea; //시작 지역
    private String start; //시작
    private String end; //끝
    private String scom; //완료여부
    private String rpathno;  //경로
    private String smate; //메이트
    @Override
    public String toString() {
        return "SCHDto{" +
                "sNo=" + sno +
                ", id='" + id + '\'' +
                ", sTitle='" + stitle + '\'' +
                ", sContent='" + scontent + '\'' +
                ", sEat='" + seat + '\'' +
                ", sExer='" + sexer + '\'' +
                ", sDest='" + sdest + '\'' +
                ", cal=" + cal +
                ", sArea='" + sarea + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", sCom=" + scom +
                ", rPathNo=" + rpathno +
                ", sMate='" + smate + '\'' +
                '}';
    }
}
