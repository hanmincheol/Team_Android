package com.ict.tablayoutviewpager16.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.RecommFood;
import com.ict.tablayoutviewpager16.data.model.Category;
import com.ict.tablayoutviewpager16.data.model.Diets;
import com.ict.tablayoutviewpager16.data.model.FoodListDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewholder> {
    ArrayList<Category> items;
    Context context;
    private ApiService apiService;
    RecyclerView recyclerView;

    public CategoryAdapter(ArrayList<Category> items){

        this.items = items;
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY) // 필드 이름 변환 비활성화
                .create();
        // Retrofit 초기화
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create(gson)) // Gson 변환기 사용
                .build();
        // ApiService 인터페이스의 인스턴스 생성
        apiService = retrofit.create(ApiService.class);
    }

    @NonNull
    @Override
    public CategoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
    View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new viewholder(inflate);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.viewholder holder, int position) {

        holder.titleTxt.setText(items.get(position).getName());


        switch (position){
            case 0: {
                holder.titleTxt.setText("다이어트");
                holder.pic.setImageResource(R.drawable.veggieroll);
                break;
            }
            case 1: {
                holder.titleTxt.setText("영양식");
                holder.pic.setImageResource(R.drawable.cat2);
                break;
            }
            case 2: {
                holder.titleTxt.setText("양식");
                holder.pic.setImageResource(R.drawable.cat3);
                break;
            }
            case 3: {
                holder.titleTxt.setText("일상");
                holder.pic.setImageResource(R.drawable.cat4);
                break;
            }
            case 4: {
                holder.titleTxt.setText("찌개");
                holder.pic.setImageResource(R.drawable.cat5);
                break;
            }
            case 5: {
                holder.titleTxt.setText("육류");
                holder.pic.setImageResource(R.drawable.cat6);
                break;
            }
            case 6: {
                holder.titleTxt.setText("셀러드");
                holder.pic.setImageResource(R.drawable.cat7);
                break;
            }
            case 7: {
                holder.titleTxt.setText("면류");
                holder.pic.setImageResource(R.drawable.cat8);
                break;
            }



        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getBindingAdapterPosition();
                String categoryName = (String) holder.titleTxt.getText();
                sendRequest(categoryName);
                Log.d("Category","categoryname : "+categoryName);
            }
        });

    }
    private void sendRequest(String category) {
        // 서버에 요청 보내기
        String username = LocalStorage.getUsername(context);
        Call<List<FoodListDto>> call = apiService.findAllList(username, category);
        call.enqueue(new Callback<List<FoodListDto>>() {
            @Override
            public void onResponse(Call<List<FoodListDto>> call, Response<List<FoodListDto>> response) {
                if (response.isSuccessful()) {
                    List<FoodListDto> foodList = response.body();

                    // 중복된 FOODNAME 제거
                    Set<String> foodNameSet = new HashSet<>();
                    List<FoodListDto> distinctFoodList = new ArrayList<>();
                    for (FoodListDto food : foodList) {
                        if (!foodNameSet.contains(food.getFOODNAME())) {
                            foodNameSet.add(food.getFOODNAME());
                            distinctFoodList.add(food);
                        }
                    }

                    // 최대 8개의 항목 가져오기
                    int dataSize = Math.min(distinctFoodList.size(), 8);
                    List<FoodListDto> firstEightFoods = distinctFoodList.subList(0, dataSize);

                    goToRecommFoodScreen(firstEightFoods);

                    for (FoodListDto food : firstEightFoods) {
                        Log.d("Category", "response Category : " + food);
                    }
                } else {
                    Log.e("Category", "response Category : " + response);
                }
            }

            @Override
            public void onFailure(Call<List<FoodListDto>> call, Throwable t) {
                Log.e("Category", "response Category : " + t.getMessage());
            }
        });
    }
    private void goToRecommFoodScreen(List<FoodListDto> firstFiveFoods) {
        // 새로운 ArrayList 인스턴스를 생성하여 데이터를 복사합니다.
        ArrayList<FoodListDto> foodListCopy = new ArrayList<>(firstFiveFoods);

        // RecommFood 화면으로 이동하고, 데이터를 넘겨줍니다.
        Intent intent = new Intent(context, RecommFood.class);
        intent.putExtra("foodList", foodListCopy);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder{
        TextView titleTxt;
        ImageView pic;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.catName);

            pic = itemView.findViewById(R.id.imgCat);
        }
    }
}
