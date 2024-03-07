package com.ict.tablayoutviewpager16;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ict.tablayoutviewpager16.data.model.Diets;
import com.ict.tablayoutviewpager16.databinding.ActivityDetailfoodBinding;

public class Detailfood extends AppCompatActivity {
    ActivityDetailfoodBinding binding;
    private Diets object;
    private int num = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailfoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> {
            // 메인 액티비티를 시작하는 인텐트 생성
            Intent intent = new Intent(Detailfood.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 메인 액티비티 위에 있는 모든 액티비티를 제거합니다.
            startActivity(intent);
        });

//        binding.picDetailFood.setImageResource(R.drawable.veggieroll);
        Glide.with(Detailfood.this)
                .load(object.getRecipe_img())
                .into(binding.picDetailFood);

        binding.kcalDetailFood.setText(object.getCalory()+"Kcal");
        binding.subtitleDetailFood.setText(object.getRecipe_title());
        binding.titleDetailfood.setText(object.getEating_foodname());
        binding.contentRecipefood.setText(object.getRecipe_seq());
    }

    private void getIntentExtra() {
        object = (Diets) getIntent().getSerializableExtra("object");
    }
}