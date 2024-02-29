package com.ict.tablayoutviewpager16;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("http://192.168.0.107:4000/login")
    Call<LoginResponse> login(
            @Field("id") String id,
            @Field("pwd") String pwd
    );

//    @GET("/dietfood/search.do/{id}")
//    Call<Diets> post(@Path("id") int id);
}