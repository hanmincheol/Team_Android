package com.ict.tablayoutviewpager16;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
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

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ReceivedCookiesInterceptor(this))
                .build();

        // Retrofit 설정에 OkHttpClient 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/") // 백엔드 서버의 주소와 포트 번호
                .client(client)
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
                        int statusCode = response.code(); // HTTP 응답 코드 가져오기
                        Log.d("Response", "HTTP Status Code: " + statusCode);
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();

                            // 로그인 성공 시 처리
                            handleLoginSuccess(loginResponse);
                        } else {
                            // 로그인 실패 시 처리

                            handleLoginFailure();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("Error", "Login failed: " + t.getMessage());
                        Toast.makeText(Login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void handleLoginSuccess(LoginResponse loginResponse) {
        // 로그인 성공 시 처리
        Log.d("Login", "Login success. Token: ");
        Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();

        String username = loginResponse.getUsername();
        String token = loginResponse.getToken();
        // 다음 화면으로 이동하거나 추가적인 작업을 수행할 수 있습니다.
        Log.d("Login", "Login success. Username: " + username + ", Token: " + token);

        // 사용자 이름과 토큰을 로컬 저장소에 저장
        LocalStorage.setUsername(getApplicationContext(), username);
        LocalStorage.setToken(getApplicationContext(), token);

        // 다음 화면으로 이동
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    private void handleLoginFailure() {
        // 로그인 실패 시 처리
        Log.e("Login", "Login failed");
        Toast.makeText(Login.this, "Login failed", Toast.LENGTH_SHORT).show();
        // 추가적인 작업을 수행할 수 있습니다.
    }
}
