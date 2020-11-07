package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

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
                int onBoard= (int) new Utility().getSharedPreferences(SplashActivity.this,"UserData","OnBoard",0);
                int verified=0;
                if(currentUser!=null) {
                    if (!Objects.equals(currentUser.getEmail(), "")) {
                        if (currentUser.isEmailVerified()) {
                            verified = 1;
                        }
                        if (!Objects.equals(currentUser.getEmail(), "") && onBoard == 0 && verified == 1) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                        if (!Objects.equals(currentUser.getEmail(), "") && onBoard == 6 && verified == 1) {
                            finish();
                            startActivity(new Intent(SplashActivity.this, ProfileActivity.class));
                        }
                    }
                }
                new DataForDatabase(SplashActivity.this).AddQuizData();



                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}