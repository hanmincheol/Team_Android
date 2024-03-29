package com.ict.tablayoutviewpager16;

import com.ict.tablayoutviewpager16.data.model.BBSDto;
import com.ict.tablayoutviewpager16.data.model.Diets;
import com.ict.tablayoutviewpager16.data.model.ExerciseRequest;
import com.ict.tablayoutviewpager16.data.model.FoodListDto;
import com.ict.tablayoutviewpager16.data.model.MemberDto;
import com.ict.tablayoutviewpager16.data.model.Post;
import com.ict.tablayoutviewpager16.data.model.ProfileUser;
import com.ict.tablayoutviewpager16.data.model.SCHDto;
import com.ict.tablayoutviewpager16.data.model.Training;
import com.ict.tablayoutviewpager16.data.model.UserProfileFriend;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @GET("/recipe/View.do")
    Call <List<FoodListDto>> findAllList(@Query("id") String id, @Query("category") String category);


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

    @POST("http://192.168.0.107:4000/Dietfood/SaveBulk.do")
    Call<Void> sendDataToServer(@Body List<Map<String, Object>> dataToSave);

    @POST("sch/seleteAnyAll.do")
    Call<List<SCHDto>> getEventsForDate(@Body Map<String, Object> requestBody);
    @Multipart
    @POST("/bbs/Write.do")
    Call<Integer> uploadImageAndText(
            @Part("id") RequestBody id,
            @Part MultipartBody.Part files,
            @Part("content") RequestBody content,
            @Part("hashTag") RequestBody hashTag,
            @Part("disclosureYN") RequestBody disclosureYN,
            @Part("type") RequestBody type
    );
}
