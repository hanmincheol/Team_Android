package com.ict.tablayoutviewpager16.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.Login;
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
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView loginIdTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Content2LayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

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
                String username = LocalStorage.getUsername(context);
                if (username != null) {
                    Intent intent = new Intent(getActivity(), MyPage.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "로그인 후 이용하세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Login.class);
                    startActivity(intent);
                }
            }
        });

        recyclerView = view.findViewById(R.id.bestFoodView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        dietItems = new ArrayList<>();
        dietAdapter = new DietAdapter(dietItems);
        recyclerView.setAdapter(dietAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);


        Call<List<Diets>> call = apiService.getDailyDiet(username);
        call.enqueue(new Callback<List<Diets>>() {
            @Override
            public void onResponse(Call<List<Diets>> call, Response<List<Diets>> response) {
                if (response.isSuccessful()) {
                    List<Diets> dietsList = response.body();
                    for (Diets diet : dietsList) {
                        dietItems.add(diet);
                    }
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


        recyclerViewCat = view.findViewById(R.id.categoryView);
        recyclerViewCat.setLayoutManager(new GridLayoutManager(getActivity(),4));

        categoryItems = new ArrayList<>();
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));
        categoryItems.add(new Category("밥","밥"));

        categoryAdapter = new CategoryAdapter(categoryItems);
        recyclerViewCat.setAdapter(categoryAdapter);

        swipeRefreshLayout = view.findViewById(R.id.swipecontent2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null)
            binding.textView5.setText(bundle.getString("activity_to_fragment"));

        return view;
    }

    private void refreshData() {
        // 여기에 데이터 새로고침 작업을 수행하는 코드를 추가합니다.
        // 예를 들어, 네트워크에서 데이터를 다시 가져오는 등의 작업을 수행합니다.
        // 데이터 새로고침이 완료되면 아래의 코드를 호출하여 새로고침을 완료합니다.
        swipeRefreshLayout.setRefreshing(false);
    }

    public interface OnDataTransferListener {
        void onDataTransfer(String data);
    }

    private OnDataTransferListener onDataTransferListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onDataTransferListener = (OnDataTransferListener) context;
        onDataActivityTransferListener = (OnDataActivityTransferListener) context;
        Log.i("com.ict.tablayoutviewpager16", "onAttach:" + onDataTransferListener);
        this.context = context;
        onDataTransferListener = (OnDataTransferListener) context;
        onDataActivityTransferListener = (OnDataActivityTransferListener) context;
        Log.i("com.ict.tablayoutviewpager16", "onAttach:" + onDataTransferListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
