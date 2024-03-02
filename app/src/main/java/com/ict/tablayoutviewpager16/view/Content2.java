package com.ict.tablayoutviewpager16.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.OnDataActivityTransferListener;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.Category;
import com.ict.tablayoutviewpager16.databinding.Content2LayoutBinding;
import com.ict.tablayoutviewpager16.model.CategoryAdapter;
import com.ict.tablayoutviewpager16.model.DietAdapter;
import com.ict.tablayoutviewpager16.data.model.Diets;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class Content2 extends Fragment {

    private ImageView imageView;
    private OnDataActivityTransferListener onDataActivityTransferListener;

    private Content2LayoutBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView recyclerViewCat;
    private DietAdapter dietAdapter;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Diets> dietItems;
    private ArrayList<Category> categoryItems;
    private Context context;

    //2]onCreateView()오버 라이딩
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Content2LayoutBinding.inflate(inflater, container, false);
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

        // RecyclerView 설정
        recyclerView = view.findViewById(R.id.bestFoodView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));

        // 다이어트 아이템 생성 (임의의 데이터)
        dietItems = new ArrayList<>();
        dietAdapter = new DietAdapter(dietItems);
        recyclerView.setAdapter(dietAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 사용
                .build();

        // ApiService 인터페이스의 인스턴스 생성
        ApiService apiService = retrofit.create(ApiService.class);

        String username = LocalStorage.getUsername(context);
        // 서버에 요청 보내기
        Call<List<Diets>> call = apiService.getDailyDiet(username);
        call.enqueue(new Callback<List<Diets>>() {
            @Override
            public void onResponse(Call<List<Diets>> call, Response<List<Diets>> response) {
                if (response.isSuccessful()) {
                    List<Diets> dietsList = response.body();
                    // 서버에서 받은 데이터를 처리하고 Diets 리스트에 추가하는 작업 수행
                    for (Diets diet : dietsList) {
                        dietItems.add(diet);
                    }
                    // RecyclerView에 변경된 데이터를 반영하기 위해 어댑터에 알림
                    dietAdapter.notifyDataSetChanged();
                } else {
                    // 서버 응답이 실패한 경우에 대한 처리
                }
            }

            @Override
            public void onFailure(Call<List<Diets>> call, Throwable t) {
                // 서버 요청이 실패한 경우에 대한 처리
            }
        });

        // -----------------Category RecyclerView 설정-------------------
        recyclerViewCat = view.findViewById(R.id.categoryView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerViewCat.setLayoutManager(new GridLayoutManager(getActivity(),4));

        // 다이어트 아이템 생성 (임의의 데이터)
        categoryItems = new ArrayList<>();
        // 필요에 따라 다른 항목 추가
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));


        // 어댑터 설정
        categoryAdapter = new CategoryAdapter(categoryItems);
        recyclerViewCat.setAdapter(categoryAdapter);

        //3번)액티비티->프래그먼트(로 전송한 데이타 받기):getArgments()
        Bundle bundle = getArguments();
        if (bundle != null)
            binding.textView5.setText(bundle.getString("activity_to_fragment"));
        // 각 버튼의 이벤트 처리

        //3]루트 뷰 반환
        return view;
    }

    //STEP1. 데이타 전송 이벤트 용 인터페이스 정의
    public interface OnDataTransferListener {
        void onDataTransfer(String data);
    }

    //2번)용 인터페이스
    private OnDataTransferListener onDataTransferListener;

    //STEP3. 다른 프래그먼트로 전송을 위한  세터 정의 및
    //       액티비티로 전송을 위한 onAttach 오버라이딩
    //STEP3. 다른 프래그먼트로 전송을 위한  세터 정의
    //2번)용 인터페이스 타입의 세터
    public void setOnDataTransferListener(OnDataTransferListener onDataTransferListener) {
        this.onDataTransferListener = onDataTransferListener;
    }

    //1번 및 2번용) onAttach 오버라이딩.
    //      ※단,현재 프래그먼트(Content1)가 부착된 액티비티(MainActivity)는 OnDataTransferListener를 구현한다
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // MainActivity가 OnDataTransferListener 및 OnDataActivityTransferListener를 구현했음으로
        // MainActivity는 OnDataTransferListener 및 OnDataActivityTransferListener타입이다
        onDataTransferListener = (OnDataTransferListener) context;
        onDataActivityTransferListener = (OnDataActivityTransferListener) context;
        Log.i("com.ict.tablayoutviewpager16", "onAttach:" + onDataTransferListener);

        this.context = context; // context 초기화
        onDataTransferListener = (OnDataTransferListener) context;
        onDataActivityTransferListener = (OnDataActivityTransferListener) context;
        Log.i("com.ict.tablayoutviewpager16", "onAttach:" + onDataTransferListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 프래그먼트는 뷰보다 오래 지속됩니다.
        // 프래그먼트의 onDestroyView() 메서드에서 결합 클래스 인스턴스 참조를 정리해야 합니다.
        binding = null; // 인스턴스 참조를 null로 설정
    }
}
