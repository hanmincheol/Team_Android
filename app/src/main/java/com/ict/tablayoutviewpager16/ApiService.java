package com.ict.tablayoutviewpager16;

import com.ict.tablayoutviewpager16.data.model.BBSDto;
import com.ict.tablayoutviewpager16.data.model.Diets;
import com.ict.tablayoutviewpager16.data.model.ExerciseRequest;
import com.ict.tablayoutviewpager16.data.model.MemberDto;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.data.model.ProfileUser;
import com.ict.tablayoutviewpager16.data.model.Training;
import com.ict.tablayoutviewpager16.data.model.UserProfileFriend;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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
    @Headers("Content-Type: application/json")
    @POST("http://192.168.0.107:4000/exer/getData.do")
    Call<List<Training>> getData(@Body Map<String, String> requestBody);

    @GET("http://192.168.0.107:4000/comm/profile")
    Call<ProfileUser> userProfile(@Query("id") String id);

    @GET("http://192.168.0.107:4000/user/relationship")
    Call<Map<String, Integer>> userProfileFriend(@Query("id") String id);

    @GET("/user/View")
    Call<MemberDto> getUserData(@Query("id") String id);

    @POST("bbs/List.do")
    Call<List<Post>> getCommPosts(@Body Map<String, List<String>> selectedItems);

    @Headers("Content-Type: application/json")
    @GET("bbs/ViewMy.do")
    Call<List<BBSDto>> getViewMy(@Query("id") String id);

    @GET("http://192.168.0.107:4000/comm/subscribe")
    Call<Map<String, Object>> updateUserProfile(@Query("id") String id);

    @POST("recommendExercise")
    Call<Void> recommendExercise(@Body ExerciseRequest request);
}
