package com.ict.tablayoutviewpager16;

import com.ict.tablayoutviewpager16.data.model.Diets;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.data.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    @FormUrlEncoded
    @POST("http://192.168.0.107:4000/login")
    Call<LoginResponse> login(
            @Field("id") String id,
            @Field("pwd") String pwd
    );

    @GET("http://192.168.0.107:4000/Dietfood/DailyView.do")
    Call<List<Diets>> getDailyDiet(@Query("id") String id);



    @POST("bbs/List.do")
    Call<List<Post>> getCommPosts(@Body Map<String, List<String>> selectedItems);
    @Headers("Content-Type: application/json")
    @POST("bbs/userProfile")
    Call<List<Profile>> updateUserProfile(@Body Map<String, Object> requestBody);
}
