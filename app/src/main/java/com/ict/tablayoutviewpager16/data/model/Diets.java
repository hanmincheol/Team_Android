package com.ict.tablayoutviewpager16.data.model;

public class Diets {

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public double getStar() {
        return Star;
    }

    public void setStar(double star) {
        Star = star;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    @Override
    public String toString() {
        return Title;
    }

    public Diets(String Title, String ImagePath, double Star, String Price) {
    }

    private String Price;
    private String ImagePath;
    private double Star;
    private String Title;
}
