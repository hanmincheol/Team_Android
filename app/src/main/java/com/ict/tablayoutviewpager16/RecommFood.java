package com.ict.tablayoutviewpager16;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.data.model.FoodListDto;
import com.ict.tablayoutviewpager16.model.RecommFoodAdapter;
import com.ict.tablayoutviewpager16.view.Content2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecommFood extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<FoodListDto> foodList;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomm_food);
        context = this;

        // 전달된 인텐트 받기
        Intent intent = getIntent();

        // 인텐트로부터 Serializable 객체 추출
        foodList = (List<FoodListDto>) intent.getSerializableExtra("foodList");

        // 데이터가 유효한지 확인하고 로그에 출력
        if (foodList != null) {
            for (FoodListDto food : foodList) {
                Log.d("RecommFood", "Food: " + food.toString());
            }
        } else {
            Log.d("RecommFood", "No data received");
        }

        recyclerView = findViewById(R.id.recommFood); // Activity에서는 findViewById를 사용
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        RecommFoodAdapter adapter = new RecommFoodAdapter((ArrayList<FoodListDto>) foodList, new RecommFoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 해당 아이템을 클릭했을 때의 동작 구현
                showMealSelectionDialog(position);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void showMealSelectionDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("수정할 식단을 골라주세요")
                .setItems(new CharSequence[]{"아침", "점심", "저녁"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 사용자가 선택한 아이템에 따라 적절한 동작 수행
                        String mealType = "";
                        switch (which) {
                            case 0:
                                mealType = "아침";
                                break;
                            case 1:
                                mealType = "점심";
                                break;
                            case 2:
                                mealType = "저녁";
                                break;
                        }
                        String username = LocalStorage.getUsername(context);
                        Log.d("RecommFood", "Username: " + username);
                        if (username == null) {
                            // 사용자 이름이 없는 경우 처리
                            Log.e("RecommFood", "Username is null");
                            return;
                        }

                        if (position < 0 || position >= foodList.size()) {
                            // foodList에서 position 인덱스에 해당하는 항목이 없는 경우 처리
                            Log.e("RecommFood", "Invalid position: " + position);
                            return;
                        }

                        FoodListDto selectedFood = foodList.get(position);
                        if (selectedFood == null) {
                            // 선택된 음식이 없는 경우 처리
                            Log.e("RecommFood", "Selected food is null");
                            return;
                        }

                        String foodName = selectedFood.getFOODNAME();
                        int recipeCode = selectedFood.getRECIPECODE();

                        if (mealType.isEmpty() || foodName == null ) {
                            // 필수 정보가 없는 경우 처리
                            Log.e("RecommFood", "Required information is missing");
                            return;
                        }

                        // 데이터 조합
                        Map<String, Object> dataToSave = new HashMap<>();
                        dataToSave.put("id", username);
                        dataToSave.put("mealtype", mealType);
                        dataToSave.put("eating_foodname", foodName);
                        dataToSave.put("eating_recipeCode", recipeCode);
                        dataToSave.put("action", "update");

                        // 서버에 데이터 전송
                        sendDataToServer(dataToSave);

                        Intent intent = new Intent(RecommFood.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void sendDataToServer(Map<String, Object> dataToSave) {
        // Retrofit을 사용하여 서버에 데이터 전송
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 사용
                .build();
        Log.d("Response", "onClick: "+dataToSave);
        ApiService apiService = retrofit.create(ApiService.class);

        // List<Map<String, Object>> 형식으로 변환
        List<Map<String, Object>> requestDataList = new ArrayList<>();
        requestDataList.add(dataToSave);

        // POST 요청 생성
        Call<Void> call = apiService.sendDataToServer(requestDataList);

        // 요청을 비동기식으로 실행하고 응답을 처리
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    // 요청이 성공적으로 처리됨
                    Log.d("Response", "Request successful");
                } else {
                    // 요청이 실패함
                    Log.d("Response", "Request failed" + response.errorBody());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
