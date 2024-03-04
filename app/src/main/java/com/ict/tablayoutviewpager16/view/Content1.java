package com.ict.tablayoutviewpager16.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ict.tablayoutviewpager16.LocalStorage;
import com.ict.tablayoutviewpager16.Login;
import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.OnDataActivityTransferListener;
import com.ict.tablayoutviewpager16.R;

//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class Content1 extends Fragment {

    private ImageView imageView;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content1_layout, container, false);

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
