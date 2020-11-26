package com.example.nutritionapp.Modals;

public class calorieintake {
    String date;
    int amount;
    double calorie;
    double totalcalorie;

    public double getTotalcalorie() {
        return totalcalorie;
    }

    public void setTotalcalorie(double totalcalorie) {
        this.totalcalorie = totalcalorie;
    }





    public double getcalorie(){
        return calorie;
    }

    public void setcalorie(double calorie){

        this.calorie=calorie;

    }

    public int getamount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount=amount;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date=date;
    }


}
