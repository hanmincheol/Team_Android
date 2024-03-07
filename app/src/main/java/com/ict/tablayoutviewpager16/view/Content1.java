package com.ict.tablayoutviewpager16.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.Login;
import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.OnDataActivityTransferListener;
import com.ict.tablayoutviewpager16.R;

import java.util.ArrayList;
import java.util.List;

//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class Content1 extends Fragment {

    private ImageView imageView;
    private Context context;

    CalendarView calendarView;
    ListView eventListView;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content1_layout, container, false);


        calendarView = view.findViewById(R.id.calendarView);
        eventListView = view.findViewById(R.id.eventListView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 여기에서 선택한 날짜에 해당하는 이벤트를 가져오고, 리스트뷰에 표시하는 작업을 수행합니다.
                // 이벤트를 가져오는 부분은 여러 방법으로 구현할 수 있습니다.
                // 예를 들어, SQLite 데이터베이스를 사용하여 해당 날짜의 이벤트를 가져오거나,
                // 사전에 정의된 이벤트 리스트를 사용할 수도 있습니다.

                // 이 예시에서는 간단하게 선택한 날짜를 문자열로 만들어서 이벤트 리스트뷰에 표시합니다.
                String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                List<String> events = new ArrayList<>();
                events.add("Event 1 for " + selectedDate);
                events.add("Event 2 for " + selectedDate);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, events);
                eventListView.setAdapter(adapter);
            }
        });

        imageView = view.findViewById(R.id.mypage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SharedPreferences를 사용하여 로컬 스토리지에 저장된 아이디를 가져옴
                String username = LocalStorage.getUsername(context);

                if (username != null) {
                    // 로컬 스토리지에 아이디가 있을 경우 MyPage로 이동
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
