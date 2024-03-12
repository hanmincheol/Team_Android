package com.ict.tablayoutviewpager16.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;


public class CubeOutTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(@NonNull View page, float position) {
        float deltaY=0.5F;
        page.setPivotX(position < 0F ? page.getWidth() : 0F);
        page.setPivotY(page.getHeight() * deltaY);
        page.setRotationY(45F * position);
    }
}
