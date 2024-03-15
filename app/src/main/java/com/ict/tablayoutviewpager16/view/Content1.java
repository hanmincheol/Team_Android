package com.ict.tablayoutviewpager16.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ict.tablayoutviewpager16.ApiService;
import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.Login;
import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.R;
import com.ict.tablayoutviewpager16.RetrofitClient;
import com.ict.tablayoutviewpager16.data.model.SCHDto;
import com.ict.tablayoutviewpager16.model.EventAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Content1 extends Fragment {

    private ImageView imageView;
    private Context context;

    private CalendarView calendarView;
    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private TextView loginIdTextView;
    private ArrayList<SCHDto> eventList = new ArrayList<>();

    // 서버에서 가져온 이벤트를 저장할 리스트
    private List<SCHDto> cachedEvents = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content1_layout, container, false);

        calendarView = view.findViewById(R.id.calendarView);
        eventRecyclerView = view.findViewById(R.id.eventListView);
        eventAdapter = new EventAdapter(eventList);
        eventRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        eventRecyclerView.setAdapter(eventAdapter);

        loginIdTextView = view.findViewById(R.id.loginId);

        String username = LocalStorage.getUsername(context);
        if (username != null && !username.isEmpty()) {
            loginIdTextView.setText(username);
        } else {
            loginIdTextView.setText("UserID");
        }


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                Log.d("Content1", "Selected Date: " + selectedDate);
                getEvents(selectedDate);
            }
        });

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

        return view;
    }

    // 날짜에 해당하는 이벤트를 가져오는 메서드
    private void getEvents(String selectedDate) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Map<String, Object> requestBody = new HashMap<>();
        String username = LocalStorage.getUsername(context);
        requestBody.put("id", username);
        requestBody.put("start", selectedDate);

        Call<List<SCHDto>> call = apiService.getEventsForDate(requestBody);
        call.enqueue(new Callback<List<SCHDto>>() {
            @Override
            public void onResponse(Call<List<SCHDto>> call, Response<List<SCHDto>> response) {
                if (response.isSuccessful()) {
                    List<SCHDto> events = response.body();
                    if (events != null) {
                        updateEventList(events);
                        Log.d("Content1", "Successful response received from server: " + events.toString());
                    }
                } else {
                    Log.d("Content1", "Unsuccessful response received from server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<SCHDto>> call, Throwable t) {
                Log.e("Content1", "Failed to get events from server: " + t.getMessage());
            }
        });
    }

    // 이벤트 리스트를 업데이트하는 함수
    private void updateEventList(List<SCHDto> events) {
        // 이벤트를 종료 시간을 기준으로 정렬
        Collections.sort(events, new Comparator<SCHDto>() {
            @Override
            public int compare(SCHDto event1, SCHDto event2) {
                // 종료 시간을 비교하여 정렬
                return event1.getEnd().compareTo(event2.getEnd());
            }
        });

        eventList.clear();
        eventList.addAll(events);
        eventAdapter.setItems(eventList); // 업데이트된 이벤트 리스트를 어댑터에 설정
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
