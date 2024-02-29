package com.ict.tablayoutviewpager16.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ict.tablayoutviewpager16.MyPage;
import com.ict.tablayoutviewpager16.R;

//1]Fragement상속
//※androidx.fragment.app.Fragment 상속
public class Content3 extends Fragment {

    private ImageView imageView;
    //2]onCreateView()오버 라이딩
    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //3]layout inflate
        View view=inflater.inflate(R.layout.content3_layout,container,false);

        imageView = view.findViewById(R.id.mypage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // LoginActivity로 이동
                Intent intent = new Intent(getActivity(), MyPage.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
