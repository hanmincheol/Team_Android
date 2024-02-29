package com.ict.tablayoutviewpager16.view;


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

import com.ict.tablayoutviewpager16.Challenge;
import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Training;
import com.ict.tablayoutviewpager16.data.model.YoutubeVideo;
import com.ict.tablayoutviewpager16.databinding.Content4LayoutBinding;
import com.ict.tablayoutviewpager16.model.TrainAdapter;
import com.ict.tablayoutviewpager16.model.YouTubeVideoAdapter;

import java.util.ArrayList;

//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class Content4 extends Fragment implements Content2.OnDataTransferListener{


    private RecyclerView recyclerView;
    private RecyclerView youtuberecyclerView;

    private YouTubeVideoAdapter youTubeVideoAdapter;
    private ArrayList<YoutubeVideo> youtubeItems;

    private TrainAdapter trainAdapter;
    private ArrayList<Training> trainingItems;
    private ImageView imageView;
    private TextView textView;
    private Content4LayoutBinding binding;

    public Content4() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Content4LayoutBinding.inflate(inflater,container,false);
        View view = binding.getRoot();


        textView = view.findViewById(R.id.challengeMore);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginActivity로 이동
                Intent intent = new Intent(getActivity(), Challenge.class);
                startActivity(intent);
            }
        });

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
        recyclerView = view.findViewById(R.id.exciseCategoryView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));

        // 다이어트 아이템 생성 (임의의 데이터)
        trainingItems = new ArrayList<>();
        // 필요에 따라 다른 항목 추가
        trainingItems.add(new Training("밥","밥")); // 나중에 이런 거 다 수정해야함
        trainingItems.add(new Training("밥","밥"));
        trainingItems.add(new Training("밥","밥"));
        trainingItems.add(new Training("밥","밥"));
        trainingItems.add(new Training("밥","밥"));
        trainingItems.add(new Training("밥","밥"));
        trainingItems.add(new Training("밥","밥"));
        trainingItems.add(new Training("밥","밥"));
        trainingItems.add(new Training("밥","밥"));


        // 어댑터 설정
        trainAdapter = new TrainAdapter(trainingItems);
        recyclerView.setAdapter(trainAdapter);

//       ----------- Youtube Video-------------------
        youtuberecyclerView = view.findViewById(R.id.exYoutube); // RecyclerView의 ID를 BestFoodView로 변경
        youtuberecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));

        // 다이어트 아이템 생성 (임의의 데이터)
        youtubeItems = new ArrayList<>();
        // 필요에 따라 다른 항목 추가
        youtubeItems.add(new YoutubeVideo("밥")); // 나중에 이런 거 다 수정해야함
        youtubeItems.add(new YoutubeVideo("밥"));
        youtubeItems.add(new YoutubeVideo("밥"));
        youtubeItems.add(new YoutubeVideo("밥"));
        youtubeItems.add(new YoutubeVideo("밥"));
        youtubeItems.add(new YoutubeVideo("밥"));
        youtubeItems.add(new YoutubeVideo("밥"));


        // 어댑터 설정
        youTubeVideoAdapter = new YouTubeVideoAdapter(youtubeItems);
        youtuberecyclerView.setAdapter(youTubeVideoAdapter);


        //2번용(프래그먼트->프래그먼트) 데이타 받기
        if(((Content2)findFragmentByClass(Content2.class)) !=null){
            //dataTransfer이벤트(내가 만든 이벤트)가 발생할때마다(TabContent1에서 버튼 클릭시:onDataTransfer()메소드를 호출할 때마다)
            //data->binding.textView.setText(data)이 실행된다
            //이벤트(인터페이스)가 등록된 프래그먼트의 세터 호출
            //((MainActivity)getActivity()).content1.setOnDataTransferListener(data->binding.textView.setText(data));
            ((Content2)findFragmentByClass(Content2.class)).setOnDataTransferListener(data->binding.textView.setText(data));
            Log.i("com.ict.tablayoutviewpager16","클래스:"+findFragmentByClass(Content2.class));
        }


        //3]루트 뷰 반환
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //프래그먼트는 뷰보다 오래 지속됩니다.
        //프래그먼트의 onDestroyView() 메서드에서 결합 클래스 인스턴스 참조를 정리해야 합니다.
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
