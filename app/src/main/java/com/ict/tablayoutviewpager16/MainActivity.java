package com.ict.tablayoutviewpager16;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.ict.tablayoutviewpager16.databinding.ActivityMainBinding;
import com.ict.tablayoutviewpager16.model.MyPagerAdapter;
import com.ict.tablayoutviewpager16.view.Content2;
import com.ict.tablayoutviewpager16.view.Content3;
import com.ict.tablayoutviewpager16.view.Content4;
import com.ict.tablayoutviewpager16.view.Content1;
import com.ict.tablayoutviewpager16.view.Content5;
import com.ict.tablayoutviewpager16.view.CubeOutTransformer;

import java.util.Arrays;
import java.util.List;

/*
1번)프레그먼트 ->액티비티
    1)인터페이스 정의
    2)보내는 프래그먼트
       2-1)인터페이스타입 필드선언
       2-2)onAttach오버라이딩해서 2-1)필드 초기화
       2-3)클릭 이벤트등에서 인터페이스타입필드.추상메소드()호출해서 이벤트발생시키기

     3)받는 액티비티에서는 인터페이스 구현하여
        추상메소드 오버라이딩해서 데이타 받기

2번)프래그먼트 ->  프래그먼트
    ※탭 메뉴를 눌러야 해당 프래그먼트가 액티비티에 부착한다
    즉 Content3 프래그먼트의 전송 데이타 확인시 먼저 3번째 탭 메뉴를 클릭한후 전송버튼을 눌러야한다
    1)인터베이스 정의
    2)보내는 프래그먼트
       2-1)인터페이스타입 필드선언
       2-2)필드의 세터 정의
       2-3)onAttach오버라이딩해서 2-1)필드 초기화
       2-4)클릭 이벤트등에서 인터페이스타입필드.추상메소드()호출해서 이벤트발생시키기
     3)받는 프래그먼트에서는 getSupportFragmentManager()로 프래그먼트얻어서 세터호출하여
     추상메소드 오버라이딩해서 데이타 받기

3번)액티비티 ->  프래그먼트
    액티비티에서 프래그먼트객체.setArgments()호출
    프래그먼트에서는 getArgments()호출로 데이타 받기
 */

public class MainActivity extends AppCompatActivity implements Content2.OnDataTransferListener, OnDataActivityTransferListener{

    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //1.탭 메뉴 추가후 실행 앱에서 확인
        TabLayout tabLayout=binding.tabLayout;
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.diet_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.baseline_add_box_24));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.fitness_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.community_icon));
        //2.Fragment 생성후 컬렉션에 저장
        Content1 content1 = new Content1();
        //3번)액티비티->프래그먼트(로 데이타 전송하기):프래그먼트 객체.setArgments(Bundle)
        Bundle bundle = new Bundle();
        bundle.putString("activity_to_fragment","액티비티에서 전송한 데이타(번들)");
        content1.setArguments(bundle);
        Content2 content2 = new Content2();
        Content3 content3 = new Content3();
        Content4 content4 = new Content4();
        Content5 content5 = new Content5();

        List<Fragment> fragments= Arrays.asList(content1,content2,content3,content4,content5);
        //3.내가 만든 PageAdapter객체 생성
        MyPagerAdapter adapter = new MyPagerAdapter(this,fragments);
        //4.ViewPager에 내가 만든 PageAdapter를 연결
        binding.viewPager.setAdapter(adapter);

        //binding.viewPager.setCurrentItem(1);//앱 실행시 두번째 화면 보여주기(기본값 0)
        //Objects.requireNonNull(binding.tabLayout.getTabAt(1)).select();//두번째 탭메뉴로 인디케이터 이동

        //옵션:화면전환시 ViewPager의 PageTransformer를 이용한 변형 설정하기
        binding.viewPager.setPageTransformer(new CubeOutTransformer());
        //5.탭 레이아웃(TabLayout) 이벤트 처리-즉 탭 메뉴 클릭시 비로소 화면 전환이 된다
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            //탭이 선택되었을때
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(탭메뉴 인덱스) 호출시 어댑터의 createFragment(int position)가 호출된다
                binding.viewPager.setCurrentItem(tab.getPosition());
            }
            //탭이 선택되지 않았을때
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            //탭이 다시 선택되었을때
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });




    }////onCreate
    //1번)프래그먼트에서 전송한 데이타를 받기 위한 오버라이딩(내가 만든 이벤트 리스너의 추상 메소드)
    //dataTransfer이벤트 발생시 자동호출

    @Override
    public void onDataTransfer(String data) {
        Log.i("com.ict.tablayoutviewpager16","MainActivity의 onDataTransfer:data:"+data);
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }


}/////class