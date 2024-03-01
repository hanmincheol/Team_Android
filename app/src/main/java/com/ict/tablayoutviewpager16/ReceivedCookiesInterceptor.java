package com.ict.tablayoutviewpager16;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReceivedCookiesInterceptor implements Interceptor {

    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) {
        try {
            Request.Builder builder = chain.request().newBuilder();
            Response response = chain.proceed(builder.build());

            // 응답이 null인지 확인하고 처리
            if (response != null) {
                // 응답 헤더에서 쿠키 추출
                List<String> cookiesList = response.headers("Set-Cookie");

                // 쿠키를 SharedPreferences에 저장
                SharedPreferences sharedPreferences = context.getSharedPreferences("Cookies", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> cookiesSet = new HashSet<>(cookiesList);
                editor.putStringSet("Set-Cookie", cookiesSet);
                editor.apply();
            } else {
                Log.e("Interceptor", "Error: Response is null");
            }

            return response;
        } catch (Exception e) {
            Log.e("Interceptor", "Error: " + e.getMessage());
            return null;
        }
    }

}
