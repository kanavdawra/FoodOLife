package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.nutritionapp.Tools.Database.DataForDatabase;
import com.example.nutritionapp.Tools.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                FirebaseAuth mAuth=FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String onBoard= new Utility().getSharedPreferences(SplashActivity.this,"UserData","OnBoard","0");
                int verified=0;

                try {

                    if(!Objects.equals(currentUser.getEmail(), "") && onBoard.equals("1") && currentUser.isEmailVerified()){
                        Log.e("Email", currentUser.getEmail());
                        startActivity(new Intent(SplashActivity.this, DashBoardActivity.class));

                    }
                    else{
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }
                } catch (Exception e) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    e.printStackTrace();
                }


                new DataForDatabase(SplashActivity.this).AddQuizData();
                new DataForDatabase(SplashActivity.this).AddFoodData();



                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}