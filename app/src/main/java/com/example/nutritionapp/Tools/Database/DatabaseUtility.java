package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nutritionapp.Interface.QuizInterface;
import com.example.nutritionapp.Interface.RankListInterface;
import com.example.nutritionapp.Modals.Meal;
import com.example.nutritionapp.Modals.Quiz;
import com.example.nutritionapp.Modals.QuizRankList;
import com.example.nutritionapp.Modals.Recentmeals;
import com.example.nutritionapp.Modals.TodayMacros;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

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

    public ArrayList<Meal> getLog(String type){
        SQLiteDatabase database=getDataBase().getReadableDatabase();
        ArrayList<Meal> listMeal = new ArrayList<Meal>();
        Cursor cursor=database.rawQuery("select n.*, i.* from food_intake i left join food_nutrients n on n.id =i.food_id where type =?", new String[]{type});
        if (cursor.getCount() ==0){
            Meal noMeal = new Meal();
            noMeal.setName("There is nothing. Please Add Something");
            listMeal.add(noMeal);
        }else{
            listMeal=getMeal(cursor);
        }
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
        cursor.moveToPosition(0);



        while (!(cursor.getPosition()==cursor.getCount())){
            Recentmeals objectforrecentmeals=new Recentmeals();
            objectforrecentmeals.setFood_amount(cursor.getInt(0));
            objectforrecentmeals.setRecent_food_name(cursor.getString(1));
            objectforrecentmeals.setfood_calorie(cursor.getInt(2) * cursor.getInt(0) );

            listofrecents.add(objectforrecentmeals);
            cursor.moveToNext();
        }

        return listofrecents;
    }

    public ArrayList<Recentmeals> getRecentsfoodamount(String type){
        SQLiteDatabase databaseone=getDataBase().getReadableDatabase();
        ArrayList<Recentmeals> listofrecents=new ArrayList<>();

        Cursor cursor1=databaseone.rawQuery("Select " +
                "b.amount,a.food_name,a.calorie from" +
                " food_nutrients a  inner join food_intake b on " +
                "a.id=b.food_id  where b.type='"+type+"' order by b.date",null);

        Log.e("query",String.valueOf(cursor1.getCount()));

        listofrecents=getrecentsModal(cursor1);
        cursor1.close();
        return listofrecents;
    }

    public void getQuizRankList(final RankListInterface rankListInterface){

        final ArrayList<QuizRankList> quizRankList=new ArrayList<>();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference("RankList").child("Quiz");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildrenCount();

                for(DataSnapshot rankSnapshot: snapshot.getChildren()){
                    String name=String.valueOf(rankSnapshot.child("Name").getValue());
                    String email=String.valueOf(rankSnapshot.child("Email").getValue());
                    int index= Integer.parseInt(Objects.requireNonNull(rankSnapshot.child("QuizIndex").getValue()).toString());
                    int marks= Integer.parseInt(Objects.requireNonNull(rankSnapshot.child("QuizMarks").getValue()).toString());

                    QuizRankList rankListObject= new QuizRankList();
                    rankListObject.setEmail(email);
                    rankListObject.setName(name);
                    rankListObject.setQuizIndex(index);
                    rankListObject.setQuizScore(marks);
                    double score=(double) marks/index;
                    rankListObject.setTotalscore(score);
                    quizRankList.add(rankListObject);
                }
                rankListInterface.QuizRankList(quizRankList);
                Log.e("RankListCount",String.valueOf(snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void getGoalRankList(final RankListInterface rankListInterface){


    }

    public ArrayList<TodayMacros> getTodayMacros(String date){
        ArrayList<TodayMacros> todayMacros= new ArrayList<>();

        SQLiteDatabase database=getDataBase().getReadableDatabase();
        Cursor cursor=database.rawQuery("select a.calorie,a.carbohydrates,a.protein,a.fat,b.amount " +
                "from food_nutrients a inner join food_intake b on a.id=b.food_id " +
                "where date='"+date+"'",null);
cursor.moveToFirst();

        if(cursor.getCount()!=0){
            while (!(cursor.getPosition()==cursor.getCount())){
                TodayMacros todayMacrosObject=new TodayMacros();
                todayMacrosObject.setCalories(cursor.getDouble(0));
                todayMacrosObject.setCarbohydrates(cursor.getDouble(1));
                todayMacrosObject.setProteins(cursor.getDouble(2));
                todayMacrosObject.setFats(cursor.getDouble(3));
                todayMacrosObject.setAmount(cursor.getDouble(4));

                todayMacros.add(todayMacrosObject);
                cursor.moveToNext();
            }
        }
        return todayMacros;

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
