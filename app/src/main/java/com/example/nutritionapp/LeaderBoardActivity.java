package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nutritionapp.Interface.RankListInterface;
import com.example.nutritionapp.Modals.QuizRankList;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {
    ArrayList<QuizRankList> rankList;
    RecyclerView rankRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        rankRecyclerView=findViewById(R.id.leaderboard_recyclerView);
        bottomNavigationBar();

        RankListInterface rankListInterface=new RankListInterface() {
            @Override
            public void QuizRankList(ArrayList<QuizRankList> rankList1) {
                rankList=rankList1;
                sortRankList();

            }

            @Override
            public void GoalRankList() {

            }
        };

        new DatabaseUtility(this).getQuizRankList(rankListInterface);

    }

    private void setQuizRankListAdaptor(){

        LeaderBoardAdapter adapter=new LeaderBoardAdapter(this,rankList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        rankRecyclerView.setLayoutManager(layoutManager);
        rankRecyclerView.setAdapter(adapter);

    }

    private void sortRankList(){

        for(int i=0;i<rankList.size();i++){

            for(int j=i;j<rankList.size();j++){
                QuizRankList temp;
                if(rankList.get(j).getTotalscore()>rankList.get(i).getTotalscore()){


                    temp=rankList.get(j);
                    rankList.set(j,rankList.get(i));
                    rankList.set(i,temp);
                }
            }

            Log.e("score",rankList.get(i).getTotalscore()+"");

        }
        setQuizRankListAdaptor();

    }

    private void bottomNavigationBar()
    {
        final LinearLayout profile=findViewById(R.id.bottom_navigation_profile);
        final LinearLayout quiz=findViewById(R.id.bottom_navigation_quiz);
        final LinearLayout dashboard=findViewById(R.id.bottom_navigation_dashboard);
        final LinearLayout leaderboard=findViewById(R.id.bottom_navigation_leaderboard);
        final LinearLayout stats=findViewById(R.id.bottom_navigation_stats);
        leaderboard.setBackgroundResource(R.drawable.view_top_right_border_blue);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_blue);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_black);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                stats.setBackgroundResource(R.drawable.view_top_right_border_black);
                startActivity(new Intent(LeaderBoardActivity.this,ProfileActivity.class));
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
                startActivity(new Intent(LeaderBoardActivity.this,Quiz.class));
                finish();
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
                startActivity(new Intent(LeaderBoardActivity.this,DashBoardActivity.class));
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
               startActivity(new Intent(LeaderBoardActivity.this,statistics.class));
               finish();
            }
        });


    }
}