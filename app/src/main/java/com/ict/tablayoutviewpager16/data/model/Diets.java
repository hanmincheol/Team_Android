package com.ict.tablayoutviewpager16.data.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

public class Diets implements Serializable {

    public String getEating_foodname() {
        return eating_foodname;
    }

    public void setEating_foodname(String eating_foodname) {
        this.eating_foodname = eating_foodname;
    }

    public String getRecipe_img() {
        return recipe_img;
    }

    public void setRecipe_img(String recipe_img) {
        this.recipe_img = recipe_img;
    }

    public String getRecipe_title() {
        return recipe_title;
    }

    public void setRecipe_title(String recipe_title) {
        this.recipe_title = recipe_title;
    }

    public Diets(String eating_foodname, String recipe_img, String recipe_title) {
    }

    private String eating_foodname;
    private String recipe_img;
    private String recipe_title;
}
