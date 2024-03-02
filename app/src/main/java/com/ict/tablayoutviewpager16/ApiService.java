package com.ict.tablayoutviewpager16;

import com.ict.tablayoutviewpager16.data.model.Diets;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("http://192.168.0.107:4000/login")
    Call<LoginResponse> login(
            @Field("id") String id,
            @Field("pwd") String pwd
    );

        @GET("http://192.168.0.107:4000/Dietfood/DailyView.do")
        Call<List<Diets>> getDailyDiet(@Query("id") String id);
    }