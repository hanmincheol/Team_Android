package com.ict.tablayoutviewpager16;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SignInFirstPage extends AppCompatActivity {

    private Button btn_signin_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_first_page);

        btn_signin_next = findViewById(R.id.btn_signin_next);
        btn_signin_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInFirstPage.this , SignInNextPage.class); // 회원가입 페이지로
                startActivity(intent); //액티비티 이동
            }
        });
    }
}