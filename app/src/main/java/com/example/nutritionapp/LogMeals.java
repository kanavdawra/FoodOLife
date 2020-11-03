package com.example.nutritionapp;

public class LogMeals {
    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public LogMeals(int img, String header, String description) {
        this.img = img;
        this.header = header;
        this.description = description;
    }

    int img;
    String header;
    String description;

}
