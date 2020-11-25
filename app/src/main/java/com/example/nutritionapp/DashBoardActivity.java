package com.example.nutritionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nutritionapp.Modals.QuizRankList;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.example.nutritionapp.Tools.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.mateware.snacky.Snacky;

public class DashBoardActivity extends AppCompatActivity {
    Dialog AmountDialog;
    double weight=-1;
    int unit=-1;// kg=1 lb=0
    String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        bottomNavigationBar();
        loadtoFirebase();

        LinearLayout btn_breakfast = findViewById(R.id.dashboard_add_breakfast);
        LinearLayout btn_lunch = findViewById(R.id.dashboard_add_lunch);
        LinearLayout btn_dinner = findViewById(R.id.dashboard_add_dinner);
        LinearLayout btn_snack = findViewById(R.id.dashboard_add_snack);
        weightDialog();
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
        myResults.putExtras(myBundle); //adds the bundle to the intent
        startActivity(myResults); //start activity with the intent object
    }

    private void weightDialog(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        formattedDate = df.format(c);
        String last_update = new DatabaseUtility(DashBoardActivity.this).getLastWeightUpdateDate();

        AmountDialog = new Dialog(this);
        AmountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AmountDialog.setContentView(R.layout.weight_dialog);
        AmountDialog.setCanceledOnTouchOutside(false);
        if (!last_update.equals(formattedDate)) {
            AmountDialog.show();
        }
        else{
            return;
        }

        TextView bt_save = AmountDialog.findViewById(R.id.weight_dialog_save);
        TextView bt_cancel = AmountDialog.findViewById(R.id.weight_dialog_cancel);
        final TextView kg = AmountDialog.findViewById(R.id.weight_dialog_kg);
        final TextView lb = AmountDialog.findViewById(R.id.weight_dialog_lb);
        final TextView weightText=AmountDialog.findViewById(R.id.weight_dialog_weight);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weightText.getText().toString().equals("")){
                    weightText.setError("* Weight cannot be empty");
                }
                else if(unit==-1){
                    weightText.setError("Please select units");
                    Log.e("Weight",weightText.getText().toString());
                }
                else if(weight==0){
                    weightText.setError("Weight cannot be 0");
                }
                else{
                    if(unit==0){
                        addWeightSQLQuery(weight,formattedDate,"lb");
                    }
                    else {
                        addWeightSQLQuery(weight,formattedDate,"kg");
                    }
                    AmountDialog.dismiss();
                }



            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountDialog.dismiss();
            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if(!weightText.getText().toString().equals("")){

                    weight=Double.parseDouble(weightText.getText().toString());
                    if(unit==1){}
                    else if (unit==0){
                        weight=weight*.454;
                        weightText.setText(round(weight));
                    }
                    else {}

                }
                unit=1;
                kg.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_selected));
                lb.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_un_selected));
            }
        });

        lb.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if(!weightText.getText().toString().equals("")){

                    weight=Double.parseDouble(weightText.getText().toString());
                    if(unit==1){

                        weight=(weight*1000)/454;
                        weightText.setText(round(weight));
                    }
                    else if (unit==0){ }
                    else {}

                }
                unit=0;
                lb.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_selected));
                kg.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_un_selected));
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
        dashboard.setBackgroundResource(R.drawable.view_top_right_border_blue);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_blue);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_black);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                stats.setBackgroundResource(R.drawable.view_top_right_border_black);
                startActivity(new Intent(DashBoardActivity.this,ProfileActivity.class));
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
                startActivity(new Intent(DashBoardActivity.this,Quiz.class));
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
                startActivity(new Intent(DashBoardActivity.this,LeaderBoardActivity.class));
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

    public void addWeightSQLQuery(double weight, String date,String unit){
        SQLiteDatabase db=new DatabaseUtility(this).getDataBase().getWritableDatabase();

        db.execSQL(
                "insert into user_weight(weight,date,unit) values ('"
                        +weight+"','"
                        +date+"','"
                        +unit+"'"+
                        ")"
        );
    }

    private void loadtoFirebase(){

        if(new Utility().getSharedPreferences(this,"UserData","Name","Name").equals("Name")) {

            final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

            DatabaseReference databaseReference = firebaseDatabase.getReference("UserId");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {





                    String email = null;
                    String imageURI = null;
                    String name = null;
                    try {

                        email = firebaseAuth.getCurrentUser().getEmail();

                        imageURI = Objects.requireNonNull(dataSnapshot.child(new DatabaseUtility(DashBoardActivity.this).getUserId(firebaseAuth.getCurrentUser().getEmail())).child("ImageURI").getValue()).toString();

                        name = Objects.requireNonNull(dataSnapshot.child(new DatabaseUtility(DashBoardActivity.this).getUserId(firebaseAuth.getCurrentUser().getEmail())).child("Name").getValue()).toString();
                        Log.e("Name", name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    new Utility().setSharedPreferences(DashBoardActivity.this, "UserData", "Email", email);
                    new Utility().setSharedPreferences(DashBoardActivity.this, "UserData", "ImageURI", imageURI);
                    new Utility().setSharedPreferences(DashBoardActivity.this, "UserData", "Name", name);
                    new Utility().setSharedPreferences(DashBoardActivity.this, "UserData", "OnBoard", "1");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public String round(double weight){
        weight=Math.round(weight*100)/100.0;
        if(Math.floor(weight)==weight){
            int weightInt=(int)weight;
            return String.valueOf(weightInt);
        }
        else {
            return String.valueOf(weight);
        }
    }
}