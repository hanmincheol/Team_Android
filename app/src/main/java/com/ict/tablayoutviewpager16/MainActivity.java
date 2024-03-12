package com.ict.tablayoutviewpager16;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.ict.tablayoutviewpager16.databinding.ActivityMainBinding;
import com.ict.tablayoutviewpager16.model.MyPagerAdapter;
import com.ict.tablayoutviewpager16.view.Content2;
import com.ict.tablayoutviewpager16.view.Content1;
import com.ict.tablayoutviewpager16.view.Content3;
import com.ict.tablayoutviewpager16.view.Content4;
import com.ict.tablayoutviewpager16.view.Content5;
import com.ict.tablayoutviewpager16.view.CubeOutTransformer;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements Content2.OnDataTransferListener, OnDataActivityTransferListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 탭 메뉴 추가
        TabLayout tabLayout = binding.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.diet_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.baseline_add_box_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.fitness_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.community_icon));

        // 프래그먼트 클래스 리스트에 추가
        List<Fragment> fragmentClasses = new ArrayList<>();
        fragmentClasses.add(new Content1());
        fragmentClasses.add(new Content2());
        fragmentClasses.add(new Content3());
        fragmentClasses.add(new Content4());
        fragmentClasses.add(new Content5());

        // 어댑터에 프래그먼트 클래스 리스트 전달
        MyPagerAdapter adapter = new MyPagerAdapter(this, fragmentClasses);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(1); // 프래그먼트를 미리 로드하여 초기화

        // 화면 전환시 ViewPager의 PageTransformer를 이용한 변형 설정
        binding.viewPager.setPageTransformer(new CubeOutTransformer());

        // 탭 레이아웃(TabLayout) 이벤트 처리
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // ViewPager의 페이지 변경을 감지하여 탭을 선택하는 리스너 설정
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });
    }

    @Override
    public void onDataTransfer(String data) {
        Log.i("com.ict.tablayoutviewpager16", "MainActivity의 onDataTransfer:data:" + data);
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }
}
