package com.example.nutritionapp.Tools;


import android.content.Context;
import android.content.SharedPreferences;

public class Utility {
    public void setSharedPreferences(Context context,String fileName,String key,String value){
        SharedPreferences sharedpreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public void setSharedPreferences(Context context,String fileName,String key,float value){
        SharedPreferences sharedpreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }
    public void setSharedPreferences(Context context,String fileName,String key,int value){
        SharedPreferences sharedpreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public String getSharedPreferences(Context context,String fileName,String key,String defValue){
        SharedPreferences sharedpreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String data="";
        data=sharedpreferences.getString(key,defValue);
        return data;
    }
    public float getSharedPreferences(Context context,String fileName,String key,float defValue){
        SharedPreferences sharedpreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        float data=0;
        data=sharedpreferences.getFloat(key,defValue);
        return data;
    }
    public double getSharedPreferences(Context context,String fileName,String key,int defValue){
        SharedPreferences sharedpreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        int data=0;
        data=sharedpreferences.getInt(key,defValue);
        return data;
    }
}
