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

        db.execSQL(CREATE_QUIZ_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
