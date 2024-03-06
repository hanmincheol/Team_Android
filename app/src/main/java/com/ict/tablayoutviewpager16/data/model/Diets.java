package com.ict.tablayoutviewpager16.data.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Diets implements Serializable {
    private String id;
    private String mealType;
    private String eating_foodname;
    private String eating_recipeCode;
    private String eating_date;
    private String calory;
    private String recipe_title;
    private String recipe_url;
    private String recipe_seq;
    private String recipe_img;
}
