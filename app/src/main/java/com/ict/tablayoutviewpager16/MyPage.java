package com.ict.tablayoutviewpager16;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.ict.tablayoutviewpager16.data.model.PhotoMyPage;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.databinding.ActivityMainBinding;
import com.ict.tablayoutviewpager16.model.CommunityAdapter;
import com.ict.tablayoutviewpager16.model.MyPagerAdapter;
import com.ict.tablayoutviewpager16.model.PhotoMyPageAdapter;
import com.ict.tablayoutviewpager16.view.Content1;
import com.ict.tablayoutviewpager16.view.Content2;
import com.ict.tablayoutviewpager16.view.Content3;
import com.ict.tablayoutviewpager16.view.Content4;
import com.ict.tablayoutviewpager16.view.CubeOutTransformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyPage extends AppCompatActivity {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private PhotoMyPageAdapter photoMyPageAdapter;
    private ArrayList<PhotoMyPage> mypageItems;
    private ActivityMainBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);


        imageView = findViewById(R.id.backContent);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginActivity로 이동
                Intent intent = new Intent(MyPage.this, Content1.class);
                startActivity(intent);
            }
        });

        imageView = findViewById(R.id.logout);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginActivity로 이동
                Intent intent = new Intent(MyPage.this, Login.class);
                startActivity(intent);
            }
        });

        // -----------------mypage RecyclerView 설정-------------------
        recyclerView = findViewById(R.id.mypagePhotoView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerView.setLayoutManager(new GridLayoutManager(MyPage.this,3));

        // 다이어트 아이템 생성 (임의의 데이터)
        mypageItems = new ArrayList<>();
        // 필요에 따라 다른 항목 추가
        mypageItems.add(new PhotoMyPage("밥")); // 나중에 이런 거 다 수정해야함
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));
        mypageItems.add(new PhotoMyPage("밥"));



        // 어댑터 설정
        photoMyPageAdapter = new PhotoMyPageAdapter(mypageItems);
        recyclerView.setAdapter(photoMyPageAdapter);
    }
}