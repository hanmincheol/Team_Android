package com.ict.tablayoutviewpager16;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignInNextPage extends AppCompatActivity {

    private Button btn_nextview;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_next_page);

        btn_nextview = findViewById(R.id.btn_nextview);
        btn_nextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInNextPage.this , Login.class); //main페이지로 이동시켜놓음
                startActivity(intent); //액티비티 이동
            }
        });

    }
}