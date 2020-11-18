package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nutritionapp.Modals.Meal;
import com.example.nutritionapp.Modals.Quiz;

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

    public ArrayList<Meal> getMeal(String name){
        SQLiteDatabase database=getDataBase().getReadableDatabase();
        ArrayList<Meal> listMeal = new ArrayList<Meal>();
        Cursor cursor=database.rawQuery("select * from food_nutrients where name like ?", new String [] {String.valueOf(name)});
        listMeal=getMeal(cursor);
        cursor.close();
        return listMeal;
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

    private ArrayList<Meal> getMeal(Cursor cursor){
        ArrayList<Meal> listMeal = new ArrayList<Meal>();
        Log.e("Meal",String.valueOf(cursor.getCount()));
        cursor.moveToFirst();
        do{
            Meal m =new Meal();
            m.setName(cursor.getString(0));
            m.setCarbo(cursor.getInt(1));
            m.setProt(cursor.getInt(2));
            m.setVitamina(cursor.getInt(3));
            m.setVitaminb(cursor.getInt(4));
            m.setVitaminc(cursor.getInt(5));
            m.setVitammine(cursor.getInt(6));
            m.setSodium(cursor.getInt(7));
            m.setIron(cursor.getInt(8));
            m.setFibre(cursor.getInt(9));
            m.setCalcium(cursor.getInt(10));
            m.setCalorie(cursor.getInt(11));
            m.setDescription(cursor.getString(12));
            listMeal.add(m);
        }while (cursor.moveToNext());
        return listMeal;
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
