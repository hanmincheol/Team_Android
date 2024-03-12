package com.ict.tablayoutviewpager16;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.ict.tablayoutviewpager16.databinding.ActivityWalkingBinding;
import com.ict.tablayoutviewpager16.databinding.InfoLayoutBinding;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
    내가 이동하면 마커와 이동 경로로 라인을 그리는 앱
    구글맵(내 위치 활성화) + 위치 서비스 로 아래 동시에 구현하기
    1)나의 현재 위치(이동)에 따른 마커 표시하기(마커 하나 사용)
    2)나의 현재 위치(이동)에 따른 Polyline그리기 즉 마커 하나도 이동하면서 선도 그린다
    3)클릭한 지점에 마커 표시하고 마커 사이에 Polyline그리기

    코딩순서:지도 표시->권한체크(위치 서비스)->위치 수신(onResume-리스너 시작/onPause-리스너 스탑/onMapReady-내 위치로 카메라 이동)->구글맵 내 위치 활성화(onResume/onPause-선택사항)->지도 클릭/롱클릭 이벤트(선택사항)
 */


//onResume에서는 위치 서비스와 GooleMap의 내 위치 서비스 활성
//onPause에서는 위치 서비스와 GooleMap의 내 위치 서비스 비활성
//최초 앱 실행시 onCreate - onStart-onResume(여기서 SupportMapFragment의 getMapAsync()호출-onMapReady 자동호출(GooleMap초기화))-지도가 보인다
//백 버튼 혹은 홈 버튼 혹은 다른 화면 전환시 onPause-onStop
public class Walking extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener,LocationListener {
    private ActivityWalkingBinding binding;
    private InfoLayoutBinding infoLayoutBinding;
    //현재 앱에서 필요한 권한들
    private String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    //권한 요청시 각 권한을 구분하기 위한 요청코드값(식별자)
    public static final int MY_LOCATION_SERVICE_PERMISSION=1;
    //사용자 위치 정보 관련 API들
    private LocationManager locationManager;
    private LocationListener locationListener;
    //거부된 권한들을 저장할 컬렉션
    private List<String> deniedPermissions = new Vector<>();
    private ImageView btn_back;


    //권한 거부시 저장용
    private SharedPreferences preferences;
    //하나의 마커 사용:GoogleMap의 clear() 미사용
    private Marker mMarker;
    //이동한 경로에 따라 Polyline을 긋기 위한 좌표 저장용
    private List<LatLng> movePoints=new Vector<>();
    //클릭한 경로에 따라 Polyline을 긋 기위한 좌표 저장용
    private List<LatLng> clickPoints=new Vector<>();

    private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityWalkingBinding.inflate(getLayoutInflater());
        infoLayoutBinding = InfoLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //위치 관리자
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //쉐어드 프레퍼런스
        preferences = getSharedPreferences("deny",MODE_PRIVATE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //쉐어드에서 권한 허용/거부 여부 읽어 오기
            boolean isDeny=preferences.getBoolean("DENY",false);
            if(isDeny){//사용자가 거부를 누른 경우
                new AlertDialog.Builder(this)
                        .setTitle("앱 권한 설정")
                        .setMessage("권한을 허용해야만 앱을 사용하실수 있습니다\r\n설정 하시겠습니까?")
                        .setPositiveButton("예",(dialog,which)->{
                            //안드로이드 Settings앱의 권한 설정화면으로 이동시키기(화면 전환)
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package",getPackageName(),null));
                            startActivity(intent);
                            //다시 false로 초기화(다시 앱 실행시 설정화면으로 들어가지 않기 위함)
                            preferences.edit().putBoolean("DENY",false).commit();
                        })
                        .setNegativeButton("아니오",(dialog,which)->finish()).show();
            }
            else{//앱 최초 실행시( 이후 다시 앱 실행시에는 requestUserPermissions()안에서 권한 체크해서 허용됬으면 묻는 권한창이 뜨지 않는다)
                if(requestUserPermissions()){//사용자가 위치 서비스 권한을 허용시
                    //현재 앱에서는 권한 허용시 할 일이 없다(예:비활성화 시킨 위젯 활성화등)
                }
            }
        }////if

        btn_back = findViewById(R.id.backBtn);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 메인 액티비티를 시작하는 인텐트 생성
                Intent intent = new Intent(Walking.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 메인 액티비티 위에 있는 모든 액티비티를 제거합니다.
                startActivity(intent);
            }
        });
    }////onCreate
    //사용자에게 권한을 요청하는 메소드(안드로이드 6.0(API LEVEL 23)이상부터 추가됨)
    private boolean requestUserPermissions(){
        //앱 관련 권한의 유무여부 파악
        IntStream.range(0,permissions.length-1).forEach(index->{
            int checkPermission= ActivityCompat.checkSelfPermission(this,permissions[index]);
            //0:권한 있다,-1:권한 없다
            //권한이 없는 경우 deniedPermissions컬렉션에 저장
            if(checkPermission == PackageManager.PERMISSION_DENIED) deniedPermissions.add(permissions[index]);
        });
        //권한이 없다면 유저한테 요청 보내기
        if(!deniedPermissions.isEmpty()){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .setTitle("권한 요청")
                    .setMessage("권한을 허용해야 앱을 정상적으로 사용할 수 있습니다")
                    .setPositiveButton("확인",(dialog,which)->{

                        Log.i("location","허용안된 권한들:"+deniedPermissions.stream().collect(Collectors.joining(",")));
                        //여기서 사용자에게 권한을 요청하는  코드 작성
                        //두번째 인자:요청할 권한들의 String[크기]
                        ActivityCompat.requestPermissions(Walking.this,deniedPermissions.toArray(new String[deniedPermissions.size()]),MY_LOCATION_SERVICE_PERMISSION);
                        //※onRequestPermissionsResult오버라이딩 하자(사용자가 권한 허용했는지 거부했는지 결과를 받기 위함)
                    })
                    .setNegativeButton("앱 종료",(dialog,which)->{finish();}).show();

            return false;
        }

        return true;

    }//////

    //사용자의 선택(앱 사용중에만 허용/이번만 허용/허용 안함)이 있을때마다 호출되는 콜백 메소드
    @Override
    public void onRequestPermissionsResult(
            int requestCode,//내가 보낸 요청 코드
            @NonNull String[] permissions,//사용자에게 보낸 요청 권한들
            @NonNull int[] grantResults//사용자의 선택 결과(허용(0) or 거부(-1))
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_LOCATION_SERVICE_PERMISSION://위치 서비스 권한 요청이면
                if(grantResults.length > 0){//거부(-1) 혹은 허용(0) 한 경우
                    boolean isAllowed=false;
                    for(int i=0;i < grantResults.length;i++){
                        //i번째 권한을 허용한 경우
                        if(grantResults[i]==PackageManager.PERMISSION_GRANTED){
                            isAllowed=true;
                            break;
                        }
                    }
                    if(isAllowed) {
                        //요청 권한과 관련된 기능 및 서비스 활성화
                    }
                    else {//거부한 경우
                        preferences.edit().putBoolean("DENY",true).commit();
                        finish();
                    }
                }
        }
    }//////////////////////


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap=googleMap;
        //지도에 이벤트 리스너 부착(클릭/롱클릭 이벤트)
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        try {
            //최초 앱 실행시 내 현재 위치(위도/경도)로 구글맵의 카메라를 이동시키자
            Location location=locationManager.getLastKnownLocation(LocationManager.FUSED_PROVIDER);
            if(location !=null){
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                //지도 유형
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                //카메라 이동
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),16));
                addMarker(location);
                clickPoints.add(new LatLng(lat,lng));
            }
        }
        catch(SecurityException e){e.printStackTrace();}
    }

    //마커 표시용 메소드
    private void addMarker(Location location){

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        String position = String.format("위도:%.3s,경도:%.3s",lat,lng);
        if(mMarker ==null) {
            MarkerOptions options = new MarkerOptions();
            //마커를 표시할 위치 설정
            options.position(new LatLng(lat, lng));
            //마커 아이콘 설정
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.locallocation));
            //마커 클릭 이벤트
            options.snippet(position);
            //구글 맵에 마커 추가(add라 검색시 계속 마커가 추가된다)
            googleMap.addMarker(options);
        }
        else{
            //위치정보 변경시 위치와 스니펫만 수정
            mMarker.setPosition(new LatLng(lat, lng));
            mMarker.setSnippet(position);
        }
        //커스텀 인포 원도우 설정
        MyInfoWindowAdapter myInfoWindowAdapter=new MyInfoWindowAdapter(infoLayoutBinding.getRoot(),position);
        googleMap.setInfoWindowAdapter(myInfoWindowAdapter);

    }/////////////addMarker

    //지도 클릭시 이벤트-구글 지도의 어느 한 지점 클릭시
    //인자 latLng:클릭한 지점의 위도/경도
    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        //클릭 지점에 마커 추가
        MarkerOptions options = new MarkerOptions();
        //마커를 표시할 위치 설정
        options.position(latLng);
        //구글맵에 마커 추가
        googleMap.addMarker(options);
        //마커와 마커와 사이 Polyline 그리기
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        //라인을 그릴 위치 저장
        clickPoints.add(latLng);
        //라인을 그릴 위치를 갖고 있는 컬렉션 설정
        polylineOptions.addAll(clickPoints);
        //인자로 받은 위치까지 라인 그리기
        googleMap.addPolyline(polylineOptions);
        //인자로 받은 위치로 카메라 이동하기
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
    }
    //지도 롱 클릭시 이벤트-지도위의 마커와 곡선 지우기
    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {
        //지도위에 표시된 마커 지우기
        googleMap.clear();
        //Polyline과 관련된 데이타 지우기
        clickPoints.clear();
        movePoints.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            //위치 서비스 시작-onPause에서 중지
            locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER, 3000, 3, this);
            if(googleMap !=null)//백 버튼 혹은 홈 버튼 클릭시 앱은 onPause-onStop->onRestart->onStart->onResume
                //구글맵의 내 위치 서비스 활성화
                googleMap.setMyLocationEnabled(true);//선택사항.추가시 내위치에 dot모양 점이 생성된다
            else//아래 메소드 호출시 onMapReady()가 실행된다
                ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMapAsync(this);
        }
        catch(SecurityException e){e.printStackTrace();}
    }

    @Override
    protected void onPause() {
        super.onPause();
        //위치 서비스 중지와
        locationManager.removeUpdates(this);
        try {
            //구글맵의 내 위치 서비스 활성화 중지
            googleMap.setMyLocationEnabled(false);
        }
        catch(SecurityException e){e.printStackTrace();}
    }/////////////
    //위치 변경시마다  아래 메소드 호출된다 즉 이때 그 위치로 카메라 이동하기
    //lcation:변화된 위도/경도 정보 포함
    @Override
    public void onLocationChanged(@NonNull Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        //수신한 위치로 카메라 이동하면서 이동 경로에 곡선 그리기
        drawPolyline(new LatLng(lat,lng));
        //이동한 현재 위치에 마커 표시하기
        addMarker(location);
    }
    //이동에 따른 Polyline 그리기용 메소드
    private void drawPolyline(LatLng latLng){
        //라인을 그리기 위한 옵션 설정(색 및 두께)
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);
        //라인을 그릴 위치 저장
        movePoints.add(latLng);
        //라인을 그릴 위치를 갖고 있는 컬렉션 설정
        polylineOptions.addAll(movePoints);
        //인자로 받은 위치까지 라인 그리기
        googleMap.addPolyline(polylineOptions);
        //인자로 받은 위치로 카메라 이동하기
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
    }////////////////
}////class