package com.ict.tablayoutviewpager16;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.ict.tablayoutviewpager16.data.model.Diets;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.databinding.ActivityDetailCommunityBinding;
import com.ict.tablayoutviewpager16.databinding.ActivityDetailfoodBinding;
import com.ict.tablayoutviewpager16.model.InnerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailCommunity extends AppCompatActivity {

    ActivityDetailCommunityBinding binding;
    private Post object;
    private int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailCommunityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra(); // intent로 전달된 객체를 가져옴

        if (object != null) {
            // Post 객체의 파일 목록을 가져옴
            List<String> files = object.getFiles();

            if (files != null && !files.isEmpty()) {
                // 파일 목록이 있으면 RecyclerView에 표시
                InnerAdapter innerAdapter = new InnerAdapter(files);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                binding.detcommimage.setLayoutManager(layoutManager);
                binding.detcommimage.setAdapter(innerAdapter);
            } else {
                // 파일 목록이 없으면 RecyclerView를 숨김
                binding.detcommimage.setVisibility(View.GONE);
            }

            setVariable(); // 나머지 UI 요소 초기화
        } else {
            // 오류 처리 코드
        }
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
        binding.detcommid.setText(object.getId());
        binding.detcommlike.setText("좋아요 "+object.getLikes().size());
        Glide.with(DetailCommunity.this)
                .load(object.getProfilepath())
                .into(binding.detcommidprofile);
        binding.detcontent.setText(object.getContent());

        // Post 객체의 파일 목록을 String 형식으로 변환하여 InnerAdapter에 전달
    }

    private void getIntentExtra() {object = (Post) getIntent().getSerializableExtra("object");}
}