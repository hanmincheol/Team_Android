package com.ict.tablayoutviewpager16.view;


import android.annotation.SuppressLint;
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

import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.OnDataActivityTransferListener;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.data.model.Profile;
import com.ict.tablayoutviewpager16.databinding.Content5LayoutBinding;
import com.ict.tablayoutviewpager16.model.CommunityAdapter;
import com.ict.tablayoutviewpager16.model.CommunityProfileAdapter;

import java.util.ArrayList;

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

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Content5LayoutBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

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
        // 필요에 따라 다른 항목 추가
        postItems.add(new Post("밥","밥","밥","밥")); // 나중에 이런 거 다 수정해야함
        postItems.add(new Post("밥","밥","밥","밥"));
        postItems.add(new Post("밥","밥","밥","밥"));
        postItems.add(new Post("밥","밥","밥","밥"));
        postItems.add(new Post("밥","밥","밥","밥"));
        postItems.add(new Post("밥","밥","밥","밥"));
        postItems.add(new Post("밥","밥","밥","밥"));
        postItems.add(new Post("밥","밥","밥","밥"));


        // 어댑터 설정
        communityAdapter = new CommunityAdapter(postItems);
        recyclerView.setAdapter(communityAdapter);



        // -----------------profile RecyclerView 설정-------------------
        recyclerViewprofile = view.findViewById(R.id.eventListProfileView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerViewprofile.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        // 다이어트 아이템 생성 (임의의 데이터)
        profileItems = new ArrayList<>();
        // 필요에 따라 다른 항목 추가
        profileItems.add(new Profile("밥","밥")); // 나중에 이런 거 다 수정해야함
        profileItems.add(new Profile("밥","밥"));
        profileItems.add(new Profile("밥","밥"));
        profileItems.add(new Profile("밥","밥"));
        profileItems.add(new Profile("밥","밥"));
        profileItems.add(new Profile("밥","밥"));
        profileItems.add(new Profile("밥","밥"));
        profileItems.add(new Profile("밥","밥"));
        profileItems.add(new Profile("밥","밥"));


        // 어댑터 설정
        communityProfileAdapter = new CommunityProfileAdapter(profileItems);
        recyclerViewprofile.setAdapter(communityProfileAdapter);



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
