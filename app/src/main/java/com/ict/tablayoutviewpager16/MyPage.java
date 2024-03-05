package com.ict.tablayoutviewpager16;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ict.tablayoutviewpager16.data.model.BBSDto;
import com.ict.tablayoutviewpager16.data.model.MemberDto;
import com.ict.tablayoutviewpager16.data.model.PhotoMyPage;
import com.ict.tablayoutviewpager16.data.model.ProfileUser;
import com.ict.tablayoutviewpager16.databinding.ActivityMainBinding;
import com.ict.tablayoutviewpager16.model.PhotoMyPageAdapter;
import com.ict.tablayoutviewpager16.view.Content1;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MyPage extends AppCompatActivity {

    private TextView myPageIdTextView;
    private TextView myPageAdrTextView;
    private TextView myPageIntro;
    private TextView myPageS;
    private TextView myPageM;
    private TextView myPageF;
    private ImageView imageView;
    private ImageView myPageProfile;
    private RecyclerView recyclerView;
    private PhotoMyPageAdapter photoMyPageAdapter;
    private ArrayList<PhotoMyPage> mypageItems;
    private ActivityMainBinding binding;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        myPageIdTextView = findViewById(R.id.myPageId);
        myPageAdrTextView = findViewById(R.id.address);
        myPageProfile = findViewById(R.id.myPageProfile);
        myPageIntro = findViewById(R.id.mypagePhone);
        myPageF = findViewById(R.id.mypageF);
        myPageS = findViewById(R.id.mypageS);
        myPageM = findViewById(R.id.mypageM);


        // 로컬 스토리지에서 아이디를 가져옵니다.
        String username = LocalStorage.getUsername(this);

        // 가져온 아이디를 TextView에 설정합니다.
        myPageIdTextView.setText(username);


        imageView = findViewById(R.id.backContent);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPage.this, Content1.class);
                startActivity(intent);
            }
        });

        imageView = findViewById(R.id.logout);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SharedPreferences를 사용하여 로컬 스토리지에 저장된 값을 초기화
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // 모든 데이터를 제거합니다. 필요에 따라 원하는 데이터만 제거할 수도 있습니다.
                editor.apply();

                // LoginActivity로 이동
                Intent intent = new Intent(MyPage.this, Login.class);
                startActivity(intent);
                finish(); // 현재 화면을 종료합니다.
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 사용
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

// 데이터를 요청하고 응답을 받습니다.
        Call<MemberDto> call = apiService.getUserData(username);
        call.enqueue(new Callback<MemberDto>() {
            @Override
            public void onResponse(Call<MemberDto> call, Response<MemberDto> response) {
                if (response.isSuccessful()) {
                    MemberDto memberData = response.body();
                    Log.d("MyPage","response.body"+response.body());
                    Log.d("MyPage","memberData : "+memberData.getUserAddress());
                    myPageAdrTextView.setText(memberData.getUserAddress());


                } else {
                    // 서버 응답이 실패한 경우에 대한 처리
                }
            }

            @Override
            public void onFailure(Call<MemberDto> call, Throwable t) {
                // 서버 요청이 실패한 경우에 대한 처리
            }
        });

        Retrofit retrofitPro = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        ApiService apiServicePro = retrofit.create(ApiService.class);
        Call<ProfileUser> callPro = apiServicePro.userProfile(username);
        callPro.enqueue(new Callback<ProfileUser>() {
            @Override
            public void onResponse(Call<ProfileUser> call, Response<ProfileUser> response) {
                if (response.isSuccessful()) {
                    Log.d("MyPage","response.body: "+response.body().getProfilePath());
                    ProfileUser userProfile = response.body();
                    // 여기에서 userProfile 객체를 사용하여 필요한 작업을 수행합니다.
                    Picasso.get().load(userProfile.getProfilePath()).into(myPageProfile);
                    myPageIntro.setText(userProfile.getProIntroduction());
                } else {
                    // 서버 응답이 실패한 경우에 대한 처리
                    Log.d("MyPage","response.body"+response.body());
                }
            }

            @Override
            public void onFailure(Call<ProfileUser> call, Throwable t) {
                Log.d("MyPage","응답에러"+t.getMessage());
            }
        });

        //-------------------------------------------------------------
        Retrofit retrofitFri = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiServiceFri = retrofitFri.create(ApiService.class);

        Call<Map<String, Integer>> callFri = apiServiceFri.userProfileFriend(username);

        callFri.enqueue(new Callback<Map<String, Integer>>() {
            @Override
            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                if (response.isSuccessful()) {
                    Map<String, Integer> relationship = response.body();
                    Log.d("MyPage", "relationship: " + relationship.toString());
                    String fValue = String.valueOf(relationship.get("f"));
                    String mValue = String.valueOf(relationship.get("m"));
                    String sValue = String.valueOf(relationship.get("s"));

                    myPageF.setText("친구\n"+fValue);
                    myPageS.setText("구독자\n"+sValue);
                    myPageM.setText("메이트\n"+mValue);
                } else {
                    // 서버 응답이 실패한 경우에 대한 처리
                    Log.d("MyPage", "응답 실패: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {
                // 서버 요청 실패 처리
                Log.d("MyPage", "응답 실패: " + t.getMessage());
            }
        });

        // -----------------mypage RecyclerView 설정-------------------
        recyclerView = findViewById(R.id.mypagePhotoView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerView.setLayoutManager(new GridLayoutManager(MyPage.this,3));

        // 다이어트 아이템 생성 (임의의 데이터)
        mypageItems = new ArrayList<>();
        // 필요에 따라 다른 항목 추가
        Call<List<BBSDto>> callView = apiService.getViewMy(username);  // id는 필요에 따라 적절한 값으로 설정해야 합니다.

        callView.enqueue(new Callback<List<BBSDto>>() {
            @Override
            public void onResponse(Call<List<BBSDto>> call, Response<List<BBSDto>> response) {
                if (response.isSuccessful()) {
                    List<BBSDto> mypageItems = response.body();
                    Log.d("MyPage", "response 마이페이지 파일 데이터 : " + response.body());
                    ArrayList<PhotoMyPage> photoMyPages = new ArrayList<>();
                    for (BBSDto bbsDto : mypageItems) {
                        List<String> files = bbsDto.getFiles();
                        if (files != null && !files.isEmpty()) {
                            String firstFile = files.get(0);
                            photoMyPages.add(new PhotoMyPage(firstFile));
                            Log.d("MyPage", "response 마이페이지 파일 데이터 : " + firstFile);
                        } else {
                            photoMyPages.add(new PhotoMyPage(null));
                        }
                    }
                    // RecyclerView 어댑터 설정
                    photoMyPageAdapter = new PhotoMyPageAdapter(photoMyPages);
                    recyclerView.setAdapter(photoMyPageAdapter);
                } else {
                    Log.d("MyPage", "response 에러 : " + response.body());
                }
            }

            @Override
            public void onFailure(Call<List<BBSDto>> call, Throwable t) {
                Log.e("MyPage","서버요청 에러 내 게시글: "+t.getMessage());
            }
        });


        // 어댑터 설정
        photoMyPageAdapter = new PhotoMyPageAdapter(mypageItems);
        recyclerView.setAdapter(photoMyPageAdapter);
    }
}