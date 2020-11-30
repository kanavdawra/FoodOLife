package com.example.nutritionapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nutritionapp.Modals.Profileactivitygraph;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.example.nutritionapp.Tools.FireBase;
import com.example.nutritionapp.Tools.Utility;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.mateware.snacky.Snacky;

public class ProfileActivity extends AppCompatActivity implements ValueEventListener {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private LineChart mchart;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        bottomNavigationBar();
        getStreak();
        getLogout();
        getfoodtips();
        mchart=(LineChart) findViewById(R.id.graph);
        mchart.setDragEnabled(true);
        mchart.setScaleEnabled(false);

        ArrayList<Entry> yvalues=new ArrayList<>();
        List<String> xaxisValues=new ArrayList<>();

        ArrayList<Profileactivitygraph> valuesfordata=new ArrayList<>();
        valuesfordata=new DatabaseUtility(this).getdataforgraph();
        if (valuesfordata.size()==0) {
            Snacky.builder().setActivity(this).setBackgroundColor(Color.parseColor("#ff1744")).setText("No Weight Data Found :(").build().show();
        }

        for(int i=0;i<valuesfordata.size();i++){

            Log.e("values",String.valueOf(valuesfordata.get(i).getWeight()));
            yvalues.add(new Entry(i,(float)valuesfordata.get(i).getWeight()));
            xaxisValues.add(valuesfordata.get(i).getDate());

        }

        XAxis xAxis=mchart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        mchart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xaxisValues));
        mchart.animateX(2000);
        mchart.invalidate();
        mchart.getLegend().setEnabled(false);
        mchart.getDescription().setEnabled(false);


        LineDataSet set1=new LineDataSet(yvalues,"Data Set1");

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();

        dataSets.add(set1);
        LineData data=new LineData(dataSets);
        mchart.setData(data);
        mchart.invalidate();
        set1.setColor(Color.parseColor("#3d5aff"));
        mchart.setBackgroundColor(Color.parseColor("#212121"));
        mchart.setBorderColor(Color.WHITE);
        mchart.setGridBackgroundColor(Color.WHITE);
        mchart.getAxisLeft().setTextColor(Color.WHITE);
        mchart.getAxisRight().setTextColor(Color.WHITE);
        mchart.getXAxis().setTextColor(Color.WHITE);


        final TextView usernam=findViewById(R.id.user_name_text);

        final ImageView profilepic=findViewById(R.id.circularprofilepic);

        final TextView maintainweight=findViewById(R.id.maintainweight);

        final TextView ageofuser=findViewById(R.id.age);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        DatabaseReference databaseReference=firebaseDatabase.getReference("UserId");
        Log.e("snap",firebaseAuth.getCurrentUser().getEmail());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                usernam.setText( dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("Name").getValue().toString());

                Picasso.get().load(  dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("ImageURI").getValue().toString()).into(profilepic);

                maintainweight.setText(dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("Goal").getValue().toString());

                ageofuser.setText(dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("Age").getValue().toString());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void getStreak(){

        TextView currStreakText=findViewById(R.id.profile_curr_streak);
        TextView longestStreakText=findViewById(R.id.profile_longest_streak);

        currStreakText.setText("Current "+(int)new Utility().getSharedPreferences(this,"UserData","Streak",0));
        longestStreakText.setText("Longest "+(int)new Utility().getSharedPreferences(this,"UserData","LongestStreak",0));


    }

    private void getfoodtips(){
        View layoutforfoodtips=findViewById(R.id.profile_activity_food_tips);

        layoutforfoodtips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browse=new Intent(Intent.ACTION_VIEW, Uri.parse("https://intermountainhealthcare.org/blogs/topics/sports-medicine/2014/02/eating-and-exercise-5-tips-to-maximize-your-workouts/"));
                startActivity(browse);
            }
        });
    }

    private void getLogout(){
        TextView logout=findViewById(R.id.profile_logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
                ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData();
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
        profile.setBackgroundResource(R.drawable.view_top_right_border_blue);
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
                startActivity(new Intent(ProfileActivity.this,Quiz.class));
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
                startActivity(new Intent(ProfileActivity.this,DashBoardActivity.class));
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
                startActivity(new Intent(ProfileActivity.this,LeaderBoardActivity.class));
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
                startActivity(new Intent(ProfileActivity.this,statistics.class));
                finish();
            }
        });


    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }



}