package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.nutritionapp.Tools.Database.DataForDatabase;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashBoardActivity extends AppCompatActivity {
    Dialog AmountDialog;
    EditText weight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        bottomNavigationBar();
        Button btn_breakfast = (Button) findViewById(R.id.btn_breakfast);
        Button btn_lunch = (Button) findViewById(R.id.btn_lunch);
        Button btn_dinner = (Button) findViewById(R.id.btn_dinner);
        Button btn_snack = (Button) findViewById(R.id.btn_snack);
        enterStreak();
        btn_breakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addMeal(1);
            }
        });
        btn_lunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addMeal(2);
            }
        });
        btn_dinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addMeal(3);
            }
        });
        btn_snack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addMeal(4);
            }
        });

       //String auth=FirebaseAuth.getInstance().getCurrentUser().getEmail();


    }
    public void addMeal(int mealType){
        //EditText dateEdit = (EditText) findViewById(R.id.editDate);
        Intent myResults = new Intent(DashBoardActivity.this, LogMealActivity.class);
        //Create a bundle and put values in it
        Bundle myBundle = new Bundle();
        switch (mealType) {
            case 1:
                myBundle.putString("type", "Breakfast");
                break;
            case 2:
                myBundle.putString("type", "Lunch");
                break;
            case 3:
                myBundle.putString("type", "Dinner");
                break;
            case 4:
                myBundle.putString("type", "Snack");
                break;
            default:
                myBundle.putString("type", "");
        }
       // myBundle.putInt("type", mealType); //syntax or put is keyname, value name
        ///myBundle.putString("date",dateEdit.getText().toString());
        //put the bundle into the intent
        myResults.putExtras(myBundle); //adds the bundle to the intent
        startActivity(myResults); //start activity with the intent object
    }
    private void enterStreak(){
        AmountDialog = new Dialog(this);
        AmountDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        AmountDialog.setContentView(R.layout.streak_dialog);
        AmountDialog.show();
        Button bt_yes = (Button)AmountDialog.findViewById(R.id.btn_yes);
        Button bt_no = (Button)AmountDialog.findViewById(R.id.btn_no);
        bt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = (EditText) AmountDialog.findViewById(R.id.weight_dia);
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = df.format(c);
                String last_update = new DatabaseUtility(DashBoardActivity.this).getLastUpdate();
                if (last_update != formattedDate) {
                    new DataForDatabase(DashBoardActivity.this).addStreak(Double.valueOf(weight.getText().toString()), formattedDate);
                }
                AmountDialog.dismiss();
            }
        });
        bt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountDialog.dismiss();
            }
        });
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
            }
        });


    }
}