package com.ict.tablayoutviewpager16;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.ict.tablayoutviewpager16.databinding.ActivityIntrolayoutBinding;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Introlayout extends AppCompatActivity {
    ActivityIntrolayoutBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntrolayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 액션바 가져오기
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 액션바 숨기기
            actionBar.hide();
            // 액션바 색상 변경
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#253697")));
        }

        // 일정 시간 후 MainActivity로 이동하는 스레드 실행
        ScheduledExecutorService worker = Executors.newSingleThreadScheduledExecutor();
        Runnable runnable = () ->{
            Intent intent = new Intent(Introlayout.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        };
        worker.schedule(runnable, 1, TimeUnit.SECONDS);
    }
}
