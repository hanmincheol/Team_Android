package com.ict.tablayoutviewpager16.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.OnDataActivityTransferListener;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Diets;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.data.model.Profile;
import com.ict.tablayoutviewpager16.databinding.Content5LayoutBinding;
import com.ict.tablayoutviewpager16.model.CommunityAdapter;
import com.ict.tablayoutviewpager16.model.CommunityProfileAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class Content5 extends Fragment implements Content2.OnDataTransferListener{

    private ImageView imageView;
    private TextView textView;
    private Content5LayoutBinding binding;

    private OnDataActivityTransferListener onDataActivityTransferListener;

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewprofile;
    private CommunityAdapter communityAdapter;
    private CommunityProfileAdapter communityProfileAdapter;
    private ArrayList<Post> postItems;
    private ArrayList<Profile> profileItems;

    private Context context;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Content5LayoutBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        context = getActivity();


        imageView = view.findViewById(R.id.mypage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginActivity로 이동
                Intent intent = new Intent(getActivity(), MyPage.class);
                startActivity(intent);
            }
        });

        // -----------------Category RecyclerView 설정-------------------
        recyclerView = view.findViewById(R.id.eventListView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        // 다이어트 아이템 생성 (임의의 데이터)
        postItems = new ArrayList<>();
        communityAdapter = new CommunityAdapter(postItems);
        recyclerView.setAdapter(communityAdapter);

        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("식단");
        selectedItems.add("운동");
        selectedItems.add("심리");

        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("selectedItems", selectedItems);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 사용
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<Post>> call = apiService.getCommPosts(requestBody);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful()) {
                    List<Post> postList = response.body();
                    // 서버에서 받은 데이터를 처리하고 postItems에 추가하는 작업 수행
                    for (Post post : postList) {
                        postItems.add(post);
                    }
                    // RecyclerView에 변경된 데이터를 반영하기 위해 어댑터에 알림
                    communityAdapter.notifyDataSetChanged();
                } else {
                    // 서버 응답이 실패한 경우에 대한 처리
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                // 서버 요청이 실패한 경우에 대한 처리
            }
        });


        // -----------------profile RecyclerView 설정-------------------
        recyclerViewprofile = view.findViewById(R.id.eventListProfileView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerViewprofile.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        // 다이어트 아이템 생성 (임의의 데이터)
        profileItems = new ArrayList<>();
        communityProfileAdapter = new CommunityProfileAdapter(profileItems);
        recyclerViewprofile.setAdapter(communityProfileAdapter);

        List<String> temp = new ArrayList<>();

        String username = LocalStorage.getUsername(context);
        temp.add(username);

        Map<String, Object> requestBodyPro = new HashMap<>();
        requestBodyPro.put("ids", temp);

        Call<List<Profile>> callPro = apiService.updateUserProfile(requestBodyPro);
        callPro.enqueue(new Callback<List<Profile>>() {

            @Override
            public void onResponse(Call<List<Profile>> call, Response<List<Profile>> response) {

            }

            @Override
            public void onFailure(Call<List<Profile>> callPro, Throwable t) {
                // 서버 요청 실패 처리
            }
        });



        //3]루트 뷰 반환
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;//인스턴스 참조를 null로 설정
    }
    //2번)프래그먼트->프래그먼트에서 전송한 데이타 받기
    @Override
    public void onDataTransfer(String data) {
        Log.i("com.ict.tablayoutviewpager16","Content3의 onDataTransfer:data:"+data);
        binding.textView.setText(data);
    }

    // 클래스명을 기반으로 프래그먼트를 찾는 메서드
    private Fragment findFragmentByClass(Class fragmentClass) {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        for (Fragment fragment : fragmentManager.getFragments()) {
            if (fragmentClass.isInstance(fragment)) {
                return fragment;
            }
        }
        return null;
    }
}
