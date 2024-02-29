package com.ict.tablayoutviewpager16.data.model;

public class Category {

    private String ImagePath;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    private String Name;

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public Category(String Name, String imagePath) {
    }
}
