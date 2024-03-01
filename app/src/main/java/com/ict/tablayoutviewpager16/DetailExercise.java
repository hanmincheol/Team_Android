package com.ict.tablayoutviewpager16;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
        binding.picDetailExercise.setImageResource(R.drawable.muscular2);
//        Glide.with(Detailfood.this)
//                .load(object.getImagePath()+R.drawable.go)
//                .into(binding.picDetailFood);

    }

    private void getIntentExtra() { object = (Training) getIntent().getSerializableExtra("object"); }
}