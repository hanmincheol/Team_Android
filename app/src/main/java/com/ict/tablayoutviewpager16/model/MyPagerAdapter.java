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



    //탭 메뉴의 position에 해당하는 프래그먼트를 반환.
    //앱 최초 실행후 탭 메뉴 클릭시 viewPager2.setCurrentItem(탭메뉴 인덱스); 호출하면 아래 createFragment가 호출된다
    //단, 모든 Fragment가 ViewPager에 전달된 후에는 더 이상 호출되지 않는다.
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
