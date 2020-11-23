package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nutritionapp.Modals.Meal;
import com.example.nutritionapp.Modals.Quiz;
import com.example.nutritionapp.Modals.Recentmeals;

import java.util.ArrayList;

public class DatabaseUtility {
    Context context;

    public DatabaseUtility(Context context) {
        this.context=context;
    }

    public Database getDataBase(){
        return new Database(context,"FoodOLife",null,1);
    }

    public Quiz[] getQuiz(){
        SQLiteDatabase database=getDataBase().getReadableDatabase();
        Quiz[] quiz;
        Cursor cursor=database.rawQuery("select * from foodolife_quiz",null);

        quiz=GetQuizModal(cursor);
        cursor.close();
        return quiz;
    }

    public String getLastWeightUpdateDate(){
        SQLiteDatabase database=getDataBase().getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from user_weight order by date desc limit 1",null);
        cursor.moveToFirst();
        String date ="";
        if(cursor.getCount()>0){
            date =cursor.getString(1);
        }
        cursor.close();
        return date;
    }

    private Quiz[] GetQuizModal(Cursor cursor){
        Quiz[] quiz = new Quiz[11];
        Quiz quizObject;
        Log.e("Count",String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        do{
            quizObject=new Quiz();

            quizObject.setId(cursor.getInt(0));
            quizObject.setQuestion(cursor.getString(1));
            quizObject.setAnswer(cursor.getString(2));
            quizObject.setOption1(cursor.getString(3));
            quizObject.setOption2(cursor.getString(4));
            quizObject.setOption3(cursor.getString(5));
            quizObject.setOption4(cursor.getString(6));
            quizObject.setOptionSelected(cursor.getString(7));

            quiz[cursor.getInt(0)]=quizObject;


        }while (cursor.moveToNext());
       return quiz;
    }

    public ArrayList<Meal> getMeal(){
        SQLiteDatabase database=getDataBase().getReadableDatabase();
        ArrayList<Meal> listMeal = new ArrayList<Meal>();
        Cursor cursor=database.rawQuery("select * from food_nutrients", null);
        listMeal=getMeal(cursor);
        cursor.close();
        return listMeal;
    }

    private ArrayList<Meal> getMeal(Cursor cursor){
        ArrayList<Meal> listMeal = new ArrayList<Meal>();
        cursor.moveToFirst();
        do{
            Meal m =new Meal();
            m.setName(cursor.getString(1));
            m.setCarbohydrate(cursor.getDouble(2));
            m.setProtein(cursor.getDouble(3));
            m.setFat(cursor.getDouble(4));
            m.setVitamina(cursor.getDouble(5));
            m.setVitaminb(cursor.getDouble(6));
            m.setVitaminc(cursor.getDouble(7));
            m.setVitammine(cursor.getDouble(8));
            m.setSodium(cursor.getDouble(9));
            m.setIron(cursor.getDouble(10));
            m.setFibre(cursor.getDouble(11));
            m.setCalcium(cursor.getDouble(12));
            m.setCalorie(cursor.getDouble(13));
            m.setId(cursor.getInt(0));
            listMeal.add(m);
        }while (cursor.moveToNext());
        return listMeal;
    }

    public ArrayList<Recentmeals> getrecentsModal(Cursor cursor){

        ArrayList<Recentmeals> listofrecents=new ArrayList<>();
        cursor.moveToFirst();
        do{
            Recentmeals objectforrecentmeals=new Recentmeals();
            objectforrecentmeals.setFood_amount(cursor.getInt(1));
            objectforrecentmeals.setRecent_food_name(cursor.getString(2));

        }while (cursor.moveToNext());

        return listofrecents;
    }

    public ArrayList<Recentmeals> getRecentsfoodamount(Cursor cursor){
        SQLiteDatabase databaseone=getDataBase().getReadableDatabase();
        ArrayList<Recentmeals> listofrecents=new ArrayList<>();
        Cursor cursor1=databaseone.rawQuery("select amount from food_intake",null);
        listofrecents=getrecentsModal(cursor1);
        cursor1.close();
        return listofrecents;
    }


    public String getUserId(String email){
        StringBuilder userId= new StringBuilder();
        int temp=-2;
        for(int i=0;i<email.length();i++){
            if(email.charAt(i)=='@'){
                temp=-1;
            }
            if(temp==-1 && email.charAt(i)=='.'){
                break;
            }
            userId.append(email.charAt(i));
        }
        return userId.toString();
    }
}
