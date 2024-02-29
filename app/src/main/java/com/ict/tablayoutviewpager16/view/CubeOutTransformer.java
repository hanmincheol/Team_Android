package com.ict.tablayoutviewpager16.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

//https://medium.com/codex/33-viewpager2-transformers-for-your-android-uis-bbdab801eb2b 나중에 해보삼
//https://stackoverflow.com/questions/68142916/how-to-achieve-cube-effect-in-view-pager-in-rtl-mode
public class CubeOutTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        float deltaY=0.5F;
        page.setPivotX(position < 0F ? page.getWidth() : 0F);
        page.setPivotY(page.getHeight() * deltaY);
        page.setRotationY(45F * position);
    }
}
