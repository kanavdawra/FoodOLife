package com.example.nutritionapp.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.nutritionapp.Interface.MainActivityInterface;

public class MainActivityReceiver extends BroadcastReceiver {

    MainActivityInterface mainActivityInterface;

    public MainActivityReceiver(MainActivityInterface mainActivityInterface) {
        this.mainActivityInterface=mainActivityInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String task = intent.getStringExtra("Task");
        assert task != null;
        if(task.equals("SignUp")){
            mainActivityInterface.SignUp();
        }
        if(task.equals("SignIn")){
            mainActivityInterface.SignIn();
        }
        if(task.equals("GetSex")){
            mainActivityInterface.GetSex();
        }
        if(task.equals("GetAge")){
            String sex=intent.getStringExtra("Sex");
            mainActivityInterface.GetAge(sex);
        }
        if(task.equals("GetWeight")){
            double age=intent.getDoubleExtra("Age",10.0);
            mainActivityInterface.GetWeight(age);
        }
        if(task.equals("GetHeight")){
            double weight=intent.getDoubleExtra("Weight",70.0);
            String weightUnit=intent.getStringExtra("WeightUnit");
            mainActivityInterface.GetHeight(weight,weightUnit);
        }
        if(task.equals("GetGoal")){
            double height=intent.getDoubleExtra("Height",175.0);
            String heightUnit=intent.getStringExtra("HeightUnit");
            mainActivityInterface.GetGoal(height,heightUnit);

        }
        if(task.equals("AllSet")){
            String goal=intent.getStringExtra("Goal");
            mainActivityInterface.AllSet(goal);
        }
        if(task.equals("NextActivity")){
            mainActivityInterface.NextActivity();
        }

    }
}
