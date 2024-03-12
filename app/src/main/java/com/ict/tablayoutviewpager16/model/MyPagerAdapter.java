package com.ict.tablayoutviewpager16.model;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ict.tablayoutviewpager16.MyPage;

import java.util.ArrayList;
import java.util.List;


//1]FragmentStateAdapter상속:ViewPager와 연결할 어댑터
public class MyPagerAdapter extends FragmentStateAdapter {


    private List<Fragment> fragments;
    //2]생성자에서 프래그먼트들 목록 초기화
    public MyPagerAdapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragments) {
        super(fragmentActivity);
        this.fragments=fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }
    //화면의 수를 반환. fragments 크기가 page의 개수
    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
