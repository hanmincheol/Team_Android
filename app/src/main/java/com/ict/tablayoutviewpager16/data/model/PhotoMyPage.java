package com.ict.tablayoutviewpager16.data.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhotoMyPage {
    private String imgpath;

    public PhotoMyPage(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgpath() {
        return imgpath;
    }
}
