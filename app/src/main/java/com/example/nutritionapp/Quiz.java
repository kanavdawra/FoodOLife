package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nutritionapp.Interface.MainActivityInterface;
import com.example.nutritionapp.Interface.QuizInterface;
import com.example.nutritionapp.QuizFragments.GetReady;
import com.example.nutritionapp.QuizFragments.QuizFinish;
import com.example.nutritionapp.QuizFragments.QuizQuestions;
import com.example.nutritionapp.Receiver.MainActivityReceiver;
import com.example.nutritionapp.Receiver.QuizReceiver;
import com.example.nutritionapp.Tools.Database.DataForDatabase;
import com.example.nutritionapp.Tools.Utility;

public class Quiz extends AppCompatActivity {
    QuizInterface quizInterface;
    QuizReceiver quizReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        new DataForDatabase(this).AddQuizData();
        bottomNavigationBar();

        quizInterface=new QuizInterface() {
            @Override
            public void GetReady() {
                loadFragment(new GetReady());
            }

            @Override
            public void QuizQuestions() {
                loadFragment(new QuizQuestions());
            }

            @Override
            public void QuizFinish() {
                loadFragment(new QuizFinish());
            }
        };

        loadFragmentLogic();
    }

    private void bottomNavigationBar()
    {
        final LinearLayout profile=findViewById(R.id.bottom_navigation_profile);
        final LinearLayout quiz=findViewById(R.id.bottom_navigation_quiz);
        final LinearLayout dashboard=findViewById(R.id.bottom_navigation_dashboard);
        final LinearLayout leaderboard=findViewById(R.id.bottom_navigation_leaderboard);
        final LinearLayout stats=findViewById(R.id.bottom_navigation_stats);
        quiz.setBackgroundResource(R.drawable.view_top_right_border_blue);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_blue);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_black);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                stats.setBackgroundResource(R.drawable.view_top_right_border_black);
                startActivity(new Intent(Quiz.this,ProfileActivity.class));
                finish();
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_black);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_blue);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                stats.setBackgroundResource(R.drawable.view_top_right_border_black);
            }
        });

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_black);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_black);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_blue);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                stats.setBackgroundResource(R.drawable.view_top_right_border_black);
                startActivity(new Intent(Quiz.this,DashBoardActivity.class));
                finish();
            }
        });

        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_black);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_black);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_blue);
                stats.setBackgroundResource(R.drawable.view_top_right_border_black);
                startActivity(new Intent(Quiz.this,LeaderBoardActivity.class));
                finish();
            }
        });

        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_black);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_black);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                stats.setBackgroundResource(R.drawable.view_top_right_border_blue);
                startActivity(new Intent(Quiz.this,statistics.class));
                finish();
            }
        });


    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_Fragment, fragment);
        fragmentTransaction.commit();
    }

    private void loadFragmentLogic(){
        int index= (int) new Utility().getSharedPreferences(this,"AppData","QuizIndex",0);
        Log.e("index",String.valueOf(index) );
        if(index<11){
            loadFragment(new GetReady());
        }
        else{
            loadFragment(new QuizFinish());
        }
    }

    public void RegisterReceiver(QuizInterface quizInterface){
        quizReceiver = new QuizReceiver(quizInterface);
        registerReceiver(quizReceiver, new IntentFilter("Quiz"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(quizReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        RegisterReceiver(quizInterface);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RegisterReceiver(quizInterface);
    }
}