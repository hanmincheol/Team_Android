package com.ict.tablayoutviewpager16;

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
        binding.backBtn.setOnClickListener(v -> finish());

        binding.picDetailFood.setImageResource(R.drawable.veggieroll);
//        Glide.with(Detailfood.this)
//                .load(object.getImagePath()+R.drawable.go)
//                .into(binding.picDetailFood);

        binding.kcalDetailFood.setText(object.getPrice()+"3억");
        binding.titleDetailfood.setText(object.getTitle()+"veggieroll");
        binding.ratingBar2.setRating((float) (object.getStar()+2.5));
    }

    private void getIntentExtra() {
        object = (Diets) getIntent().getSerializableExtra("object");
    }
}