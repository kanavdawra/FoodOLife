package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nutritionapp.Interface.QuizInterface;
import com.example.nutritionapp.Interface.RankListInterface;
import com.example.nutritionapp.Modals.Meal;
import com.example.nutritionapp.Modals.Profileactivitygraph;
import com.example.nutritionapp.Modals.Quiz;
import com.example.nutritionapp.Modals.QuizRankList;
import com.example.nutritionapp.Modals.Recentmeals;
import com.example.nutritionapp.Modals.SodiumData;
import com.example.nutritionapp.Modals.TodayMacros;
import com.example.nutritionapp.Modals.calorieintake;
import com.example.nutritionapp.Modals.piechartgraph;
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


    public ArrayList<calorieintake> getcalorie(){
        SQLiteDatabase database=getDataBase().getReadableDatabase();
        ArrayList<calorieintake> listofcalorie=new ArrayList<>();
        Cursor cursor=database.rawQuery("Select sum(a.amount * b.calorie),a.date from food_intake a inner join food_nutrients b on a.food_id=b.id group by a.date",null);
        listofcalorie=getcalorie(cursor);
        cursor.close();
        return listofcalorie;
    }

    public ArrayList<calorieintake>getcalorie(Cursor cursor){
        ArrayList<calorieintake> listofcalorie=new ArrayList<>();
        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            do{

                calorieintake intake=new calorieintake();
                intake.setDate(cursor.getString(1));
                intake.setcalorie(cursor.getDouble(0));
                listofcalorie.add(intake);
                Log.e("Amount",cursor.getString(1)+" "+cursor.getDouble(0));


            }while (cursor.moveToNext());
        }


        return listofcalorie;
    }

    public ArrayList<piechartgraph> getpie(){
        SQLiteDatabase database=getDataBase().getReadableDatabase();
        ArrayList<piechartgraph> listofpie=new ArrayList<>();
        Cursor cursor=database.rawQuery("Select SUM((a.carbohydrates/1000) * b.amount *4),SUM((a.protein/1000) *b.amount * 4),SUM((a.fat/1000) *b.amount*9)," +
                "SUM(((a.carbohydrates/1000) * b.amount *4)+((a.protein/1000) *b.amount * 4)+((a.fat/1000) *b.amount*9))" +
                " from food_nutrients a inner join food_intake b on b.food_id=a.id",null);
        listofpie=getpie(cursor);
        cursor.close();
        return listofpie;
    }

    public ArrayList<piechartgraph>getpie(Cursor cursor){
        ArrayList<piechartgraph> listofpie=new ArrayList<>();
        cursor.moveToFirst();
        do{
            piechartgraph pie=new piechartgraph();
            pie.setCarbohydates(cursor.getDouble(0));
            pie.setProtein(cursor.getDouble(1));
            pie.setFat(cursor.getDouble(2));
            pie.setAmount(3);
            pie.setTotalcalorie(cursor.getDouble(3));
            listofpie.add(pie);
            Log.e("Pie",cursor.getDouble(0)+" "+cursor.getDouble(1)+" "+cursor.getDouble(2)+" "+cursor.getDouble(3));
        }while (cursor.moveToNext());

        return listofpie;

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

    public ArrayList<Profileactivitygraph> getdataforgraph(){

        SQLiteDatabase databaseforgraph=getDataBase().getReadableDatabase();
        ArrayList<Profileactivitygraph> listforgraph=new ArrayList<>();

        Cursor cursorforgraph=databaseforgraph.rawQuery("select weight,date from user_weight order by date",null);

        listforgraph=getdataforgraph(cursorforgraph);

        cursorforgraph.close();
        return  listforgraph;

    }

    public ArrayList<Profileactivitygraph> getdataforgraph(Cursor cursor){

        ArrayList<Profileactivitygraph> listforgraph=new ArrayList<>();

        cursor.moveToFirst();
        if(cursor.getCount()>0){
        do{
            Profileactivitygraph profile=new Profileactivitygraph();
            if (cursor.getString(1).equals("lb")) {
                profile.setWeight(cursor.getDouble(0)*0.454);

            }

            else {
                profile.setWeight(cursor.getDouble(0));
            }

            profile.setDate(cursor.getString(1));

            listforgraph.add(profile);

        } while (cursor.moveToNext());}
        cursor.close();
        return listforgraph;
    }

    public ArrayList<SodiumData> getSodiumData(){
        ArrayList<SodiumData> sodiumDataList=new ArrayList<>();

        SQLiteDatabase db=getDataBase().getReadableDatabase();
        Cursor cursor=db.rawQuery("Select SUM(a.sodium*b.amount/1000), b.date from food_nutrients a " +
                        "inner join food_intake b on b.food_id=a.id group by b.date order by b.date",
                null);

        cursor.moveToFirst();

        if (cursor.getCount()>0) {
            do{
                SodiumData sodiumData=new SodiumData();
                sodiumData.setSodium(cursor.getDouble(0));
                sodiumData.setDate(cursor.getString(1));
                sodiumDataList.add(sodiumData);


            }while (cursor.moveToNext());
        }
        cursor.close();

        return sodiumDataList;
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
