package com.example.nutritionapp.Modals;

public class Recentmeals {

    String recent_food_name;
    int food_amount;

    public int getFood_amount(){
        return food_amount;
    }

    public String getRecent_food_name(){
        return recent_food_name;
    }


    public void setRecent_food_name(String recent_food_name){
        this.recent_food_name=recent_food_name;
    }

    public void setFood_amount(int food_amount) {
        this.food_amount = food_amount;
    }

}
