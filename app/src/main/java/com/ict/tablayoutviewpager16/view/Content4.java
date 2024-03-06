package com.ict.tablayoutviewpager16.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.Challenge;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.Login;
import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.data.model.ExerciseRequest;
import com.ict.tablayoutviewpager16.data.model.Training;
import com.ict.tablayoutviewpager16.data.model.YoutubeVideo;
import com.ict.tablayoutviewpager16.databinding.Content4LayoutBinding;
import com.ict.tablayoutviewpager16.model.TrainAdapter;
import com.ict.tablayoutviewpager16.model.YouTubeVideoAdapter;

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
    private Context context;

    public Content4() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Content4LayoutBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        String username = LocalStorage.getUsername(context);

        textView = view.findViewById(R.id.challengeMore);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }

        });


        imageView = view.findViewById(R.id.mypage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SharedPreferences를 사용하여 로컬 스토리지에 저장된 아이디를 가져옴
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
        recyclerView = view.findViewById(R.id.exciseCategoryView); // RecyclerView의 ID를 BestFoodView로 변경
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));

        // 다이어트 아이템 생성 (임의의 데이터)
        trainingItems = new ArrayList<>();
        trainAdapter = new TrainAdapter(trainingItems);
        recyclerView.setAdapter(trainAdapter);

        Context context = getContext(); // Fragment의 Context 가져오기

        // LocalStorage에서 username 가져오기
        if (username != null) {
            Log.d("Username", username);
        } else {
            Log.d("Username", "Username is null");
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:4000/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        // ApiService 인터페이스의 인스턴스 생성
        ApiService apiService = retrofit.create(ApiService.class);

        // 요청 본문 데이터 생성
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("id", username);

        // 로그로 요청 본문 데이터 확인
        Log.d("RequestBody", requestBody.toString());

        Call<List<Training>> call = apiService.getData(requestBody);
        call.enqueue(new Callback<List<Training>>() {
             @Override
             public void onResponse(Call<List<Training>> call, Response<List<Training>> response) {
                 if (response.isSuccessful()) {
                     // 요청이 성공적으로 처리됨
                     List<Training> trainingList = response.body();
                     Log.d("ResponseData", "Training List: " + trainingList);
                     for(Training training : trainingList){
                         trainingItems.add(training);
                         Log.d("ResponseData", "Training List: " + training.getEVideoPath()+"  Etype"+training.getEType()+"  EName"+training.getEName());
                     }
                     trainAdapter.notifyDataSetChanged();
                 } else {
                     // 요청이 실패한 경우
                     Log.d("ResponseData:",response.errorBody()+"");
                 }
             }

             @Override
             public void onFailure(Call<List<Training>> call, Throwable t) {
                 Log.d("ResponseData:",t.getMessage());
             }
        });


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
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void openDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.exerciserecomm);

        String username = LocalStorage.getUsername(context);

        LinearLayout exersho = bottomSheetDialog.findViewById(R.id.exersho);
        LinearLayout exerchest = bottomSheetDialog.findViewById(R.id.exerchest);
        LinearLayout exersto = bottomSheetDialog.findViewById(R.id.exersto);
        LinearLayout exerweist = bottomSheetDialog.findViewById(R.id.exerweist);
        LinearLayout exerarm = bottomSheetDialog.findViewById(R.id.exerarm);
        LinearLayout exerleg = bottomSheetDialog.findViewById(R.id.exerleg);
        LinearLayout exerall = bottomSheetDialog.findViewById(R.id.exerall);

        exersho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "어깨 운동 클릭", Toast.LENGTH_SHORT).show();
                sendDataToServer("Shoulders",username);
                bottomSheetDialog.dismiss();
            }
        });

        exerchest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "가슴 운동 클릭", Toast.LENGTH_SHORT).show();
                sendDataToServer("Chest",username);
                bottomSheetDialog.dismiss();
            }
        });

        exersto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "복부 운동 클릭", Toast.LENGTH_SHORT).show();
                sendDataToServer("Abdominals",username);
                bottomSheetDialog.dismiss();
            }
        });

        exerweist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "허리 운동 클릭", Toast.LENGTH_SHORT).show();
                sendDataToServer( "Back",username);
                bottomSheetDialog.dismiss();
            }
        });

        exerarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "팔 운동 클릭", Toast.LENGTH_SHORT).show();
                sendDataToServer("arms",username);
                bottomSheetDialog.dismiss();
            }
        });

        exerleg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "다리 운동 클릭", Toast.LENGTH_SHORT).show();
                sendDataToServer( "legs",username);
                bottomSheetDialog.dismiss();
            }
        });



        exerall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "무작위 운동 클릭", Toast.LENGTH_SHORT).show();
                sendDataToServer("randam",username);
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.show();
    }
    private void sendDataToServer(String username, String exerciseType) {
        ExerciseRequest request = new ExerciseRequest(username, exerciseType);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.107:5000/") // 서버 주소
                .addConverterFactory(GsonConverterFactory.create()) // Gson 변환기 사용
                .build();


        ApiService apiService = retrofit.create(ApiService.class);
        Call<Void> call = apiService.recommendExercise(request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 서버 응답 성공 처리
                    Log.d("Recommendation", "Exercise recommendation sent successfully");
                } else {
                    // 서버 응답 실패 처리
                    Log.d("Recommendation", "Failed to send exercise recommendation");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // 네트워크 오류 처리
                Log.e("Recommendation", "Error sending exercise recommendation: " + t.getMessage());
            }
        });
    }
}

