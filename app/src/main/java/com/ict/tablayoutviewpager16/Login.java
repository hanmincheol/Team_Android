package com.ict.tablayoutviewpager16;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    private Button btn_move;
    private Button btn_signup;
    private ApiService apiService;
    private EditText edit_id, edit_pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:4000/") // 백엔드 서버의 주소와 포트 번호
                .addConverterFactory(GsonConverterFactory.create())// json
                .build();

        apiService = retrofit.create(ApiService.class);

        edit_id = findViewById(R.id.login_id);
        edit_pw = findViewById(R.id.login_pw);

        btn_move = findViewById(R.id.btn_move);
        btn_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 사용자가 입력한 아이디와 비밀번호 가져오기
                String id = edit_id.getText().toString();
                String pw = edit_pw.getText().toString();

                // Retrofit을 사용하여 로그인 요청 보내기
                Call<LoginResponse> call = apiService.login(id, pw);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            String token = loginResponse.getToken();
                            // 로그인 성공 시 처리
                            handleLoginSuccess(token);
                        } else {
                            // 로그인 실패 시 처리
                            handleLoginFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        handleLoginFailure();
                    }
                });
            }
        });

        btn_signup = findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignInFirstPage.class); // 회원가입 페이지로
                startActivity(intent); // 액티비티 이동
            }
        });
    }

    private void handleLoginSuccess(String token) {
        // 로그인 성공 시 처리
        Log.d("Login", "Login success. Token: " + token);
        Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();
        // 다음 화면으로 이동하거나 추가적인 작업을 수행할 수 있습니다.
    }

    private void handleLoginFailure() {
        // 로그인 실패 시 처리
        Log.e("Login", "Login failed");
        Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
        // 추가적인 작업을 수행할 수 있습니다.
    }
}
