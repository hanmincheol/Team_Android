package com.ict.tablayoutviewpager16;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.ict.tablayoutviewpager16.data.model.Training;
import com.ict.tablayoutviewpager16.databinding.ActivityDetailexerciseBinding;


public class DetailExercise extends AppCompatActivity {
    ActivityDetailexerciseBinding binding;
    private Training object;
    private int num = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailexerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
//        binding.picDetailExercise.setImageResource(R.drawable.muscular2);
        String id = object.getEVideoPath().substring(object.getEVideoPath().lastIndexOf("/")+1);  //맨마지막 '/'뒤에 id가있으므로 그것만 파싱해줌
        Log.d("파싱한 아이디id 값", id);
        String url ="https://img.youtube.com/vi/"+ id+ "/" + "default.jpg";

        Glide.with(DetailExercise.this)
                .load(url)//
                .transform(new CenterCrop(),new RoundedCorners(1))
                .into(binding.picDetailExercise);
        binding.titleDetailExercise.setText(object.getEName());
        binding.contentTitleExercise.setText(object.getEType());
        binding.contentExercise.setText(object.getEContent());

    }

    private void getIntentExtra() { object = (Training) getIntent().getSerializableExtra("object"); }
}