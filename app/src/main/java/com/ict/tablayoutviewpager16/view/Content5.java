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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.Login;
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
import retrofit2.converter.jackson.JacksonConverterFactory;

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
    private TextView loginIdTextView;

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Content5LayoutBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        context = getActivity();

        loginIdTextView = view.findViewById(R.id.loginId);

        String username = LocalStorage.getUsername(context);
        if (username != null && !username.isEmpty()) {
            loginIdTextView.setText(username);
        } else {
            loginIdTextView.setText("UserID");
        }




        imageView = view.findViewById(R.id.mypage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fragment가 연결된 Context를 사용하여 SharedPreferences에서 아이디를 가져옴
                String username = LocalStorage.getUsername(context);

                if (username != null) {
                    // 로컬 스토리지에 아이디가 있을 경우 MyPage로 이동
                    Intent intent = new Intent(getActivity(), MyPage.class);
                    startActivity(intent);
                } else {
                    // 로컬 스토리지에 아이디가 없을 경우 LoginActivity로 이동
                    Toast.makeText(getContext(), "로그인 후 이용하세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
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

        Retrofit retrofitPro = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        ApiService apiServicePro = retrofitPro.create(ApiService.class);

        Call<Map<String, Object>> callPro = apiServicePro.updateUserProfile(username);
        callPro.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.isSuccessful()) {
                    Log.d("Profile","response.body가 이건가"+response.body().toString());
                    Map<String, Object> responseData = response.body();
                    Log.d("Profile","responseData가 이건가"+responseData);

                    List<Map<String, Object>> subTo = (List<Map<String, Object>>) responseData.get("subTo");
                    for (Map<String, Object> map : subTo) {
                        Profile profile = new Profile();
                        profile.setSubscribeId((String) map.get("subscribe_id"));
                        profile.setName((String) map.get("name"));
                        profile.setProfilePath((String) map.get("profilePath"));
                        // 맵으로부터 받은 데이터를 적절한 타입으로 변환하여 설정해야 합니다.
                        profile.setFNum(String.valueOf(map.get("fnum")));
                        profile.setMNum(String.valueOf(map.get("mnum")));
                        profile.setSNum(String.valueOf(map.get("snum")));
                        profileItems.add(profile);
                        Log.e("Profile","ProfileItems"+profile);
                    }

                    // RecyclerView에 변경된 데이터를 반영하기 위해 어댑터에 알림
                    communityProfileAdapter.notifyDataSetChanged();
                } else {
                    // 서버 응답이 실패한 경우에 대한 처리
                    Log.e("Profile", response.message());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
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
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
