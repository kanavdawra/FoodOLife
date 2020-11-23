package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodOLife";

    public static final String TABLE_QUIZ = "foodolife_quiz";
    public static final String KEY_QUIZ_QUESTION_ID = "id";
    public static final String KEY_QUIZ_QUESTION = "question";
    public static final String KEY_QUIZ_ANSWER = "answer";
    public static final String KEY_QUIZ_OPTION_1 = "option1";
    public static final String KEY_QUIZ_OPTION_2 = "option2";
    public static final String KEY_QUIZ_OPTION_3 = "option3";
    public static final String KEY_QUIZ_OPTION_4 = "option4";
    public static final String KEY_QUIZ_OPTION_SELECTED = "optionSelected";


    public static final String TABLE_FOOD_NUTRIENTS="food_nutrients";
    public static final String KEY_FOOD_NUTRIENTS="id";
    public static final String FOOD_NAME="food_name";
    public static final String CARBOHYDRATES="carbohydrates";
    public static final String PROTEIN="protein";
    public static final String FAT="fat";
    public static final String VITAMINA="vitamina";
    public static final String VITAMINB="vitaminb";
    public static final String VITAMINC="vitaminc";
    public static final String VITAMINE="vitamine";
    public static final String SODIUM="sodium";
    public static final String IRON="iron";
    public static final String CALCIUM="calcium";
    public static final String CALORIE="calorie";
    public static final String FIBRE="fibre";

    public static final String TABLE_INTAKE = "food_intake";
    public static final String INTAKE_ID = "id";
    public static final String FOOD_ID = "food_id";
    public static final String AMOUNT = "amount";
    public static final String DATE = "date";
    public static final String TYPE = "type";

    public static final String TABLE_WEIGHT = "user_weight";
    public static final String WEIGHT_ID = "id";
    public static final String WEIGHT = "weight";
    public static final String UNIT = "unit";
   // public static final String DATE = "date";



    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_QUIZ_TABLE = "CREATE TABLE " + TABLE_QUIZ +
                "(" +
                KEY_QUIZ_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // primary key
                KEY_QUIZ_QUESTION + " TEXT NOT NULL," +
                KEY_QUIZ_ANSWER + " TEXT NOT NULL," +
                KEY_QUIZ_OPTION_1 + " VARCHAR(255) NOT NULL," +
                KEY_QUIZ_OPTION_2 + " VARCHAR(255) NOT NULL," +
                KEY_QUIZ_OPTION_3 + " VARCHAR(255) NOT NULL," +
                KEY_QUIZ_OPTION_4 + " VARCHAR(255) NOT NULL," +
                KEY_QUIZ_OPTION_SELECTED + " VARCHAR(255)" +
                ")";

        String CREATE_FOOD_NUTRIENTS="CREATE TABLE "+ TABLE_FOOD_NUTRIENTS+
                "("+
                KEY_FOOD_NUTRIENTS+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+//primary key
                FOOD_NAME +" TEXT NOT NULL," +
                CARBOHYDRATES+" DOUBLE NOT NULL,"+
                PROTEIN+" DOUBLE NOT NULL,"+
                FAT+" DOUBLE NOT NULL,"+
                VITAMINA+" DOUBLE NOT NULL,"+
                VITAMINB+" DOUBLE NOT NULL,"+
                VITAMINC+" DOUBLE NOT NULL,"+
                VITAMINE+" DOUBLE NOT NULL,"+
                SODIUM+" DOUBLE NOT NULL,"+
                IRON+" DOUBLE NOT NULL,"+
                FIBRE+" DOUBLE NOT NULL,"+
                CALCIUM+" DOUBLE NOT NULL,"+
                CALORIE+" DOUBLE NOT NULL"+
                ")";


        String CREATE_INTAKE_TABLE = "CREATE TABLE " + TABLE_INTAKE+
                "(" +
                INTAKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // primary key
                AMOUNT + " INTEGER NOT NULL," +
                FOOD_ID + " INTEGER NOT NULL," +
                DATE+ " TEXT NOT NULL,"+
                TYPE+" TEXT NOT NULL"+
                ")";

        String CREATE_WEIGHT_TABLE = "CREATE TABLE " + TABLE_WEIGHT+
                "(" +
                WEIGHT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // primary key
                DATE+ " VARCHAR(20) NOT NULL," +
                WEIGHT + " DOUBLE NOT NULL," +
                UNIT + " VARCHAR(5) NOT NULL" +
                ")";

        db.execSQL(CREATE_QUIZ_TABLE);
        db.execSQL(CREATE_FOOD_NUTRIENTS);
        db.execSQL(CREATE_INTAKE_TABLE);
        db.execSQL(CREATE_WEIGHT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
