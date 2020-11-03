package com.example.nutritionapp.Interface;

public interface MainActivityInterface {
    public void SignUp();

    public void SignIn();

    public void GetSex();
    public void GetAge(String sex);
    public void GetWeight(double age);
    public void GetHeight(double weight,String weightUnit);
    public void GetGoal(double height,String heightUnit);
    public void AllSet(String goal);
    public void NextActivity();
}
