package com.ict.tablayoutviewpager16.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import retrofit2.http.GET;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodListDto {
    private String FOODNAME;
    private String DATATYPE;
    private String category;
    private String RECIPE_TITLE;
    private String recipe_url;
    private String RECIPE_IMG;
    private String RECIPECODE;
    private String recipe_seq;
    private String INGREDIENT;
    private String RI_AMOUNT;
    private String calory;
    private String carbohydrate;
    private String protein;
    private String fat;
    private String sodium;
    private String cholesterol;

    @Override
    public String toString() {
        return "FoodListDto{" +
                "foodname='" + FOODNAME + '\'' +
                ", dataType='" + DATATYPE + '\'' +
                ", category='" + category + '\'' +
                ", recipe_title='" + RECIPE_TITLE + '\'' +
                ", recipe_url='" + recipe_url + '\'' +
                ", recipe_img='" + RECIPE_IMG + '\'' +
                ", RECIPECODE='" + RECIPECODE + '\'' +
                ", recipe_seq='" + recipe_seq + '\'' +
                ", INGREDIENT='" + INGREDIENT + '\'' +
                ", RI_AMOUNT='" + RI_AMOUNT + '\'' +
                ", calory='" + calory + '\'' +
                ", carbohydrate='" + carbohydrate + '\'' +
                ", protein='" + protein + '\'' +
                ", fat='" + fat + '\'' +
                ", sodium='" + sodium + '\'' +
                ", cholesterol='" + cholesterol + '\'' +
                '}';
    }
}
