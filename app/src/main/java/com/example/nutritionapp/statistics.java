package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nutritionapp.Modals.calorieintake;
import com.example.nutritionapp.Modals.piechartgraph;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class statistics extends AppCompatActivity {

    private LineChart chart;
    private PieChart piechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        bottomNavigationBar();

        chart=(LineChart)findViewById(R.id.barchart);
        piechart=(PieChart) findViewById(R.id.piechart);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);

        ArrayList<Entry> yvalues=new ArrayList<>();
        List<String> xaxisvalues=new ArrayList<>();
        ArrayList<calorieintake> valuesforgraph=new ArrayList<>();
        ArrayList<PieEntry> datavalues=new ArrayList<>();
        ArrayList<piechartgraph> dataforpie=new ArrayList<>();
        dataforpie=new DatabaseUtility(this).getpie();
        valuesforgraph=new DatabaseUtility(this).getcalorie();

        for(int i=0;i<valuesforgraph.size();i++){
            yvalues.add(new Entry(i,(float)valuesforgraph.get(i).getcalorie()));
            xaxisvalues.add(valuesforgraph.get(i).getDate().substring(1,7));
        }

        for(int k=0;k<dataforpie.size();k++){
            datavalues.add(new PieEntry(dataforpie.get(k).getfat()));
            datavalues.add(new PieEntry(dataforpie.get(k).getCarbohydates()));
            datavalues.add(new PieEntry(dataforpie.get(k).getProtein()));
        }



        XAxis xAxis=chart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xaxisvalues));
        chart.animateX(2000);
        chart.invalidate();
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        LineDataSet set1=new LineDataSet(yvalues,"Data Set1");

        ArrayList<ILineDataSet> dataSets=new ArrayList<>();

        dataSets.add(set1);
        LineData data=new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
        set1.setColor(Color.parseColor("#3d5aff"));





    }

    private void bottomNavigationBar()
    {
        final LinearLayout profile=findViewById(R.id.bottom_navigation_profile);
        final LinearLayout quiz=findViewById(R.id.bottom_navigation_quiz);
        final LinearLayout dashboard=findViewById(R.id.bottom_navigation_dashboard);
        final LinearLayout leaderboard=findViewById(R.id.bottom_navigation_leaderboard);
        final LinearLayout stats=findViewById(R.id.bottom_navigation_stats);
        stats.setBackgroundResource(R.drawable.view_top_right_border_blue);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.setBackgroundResource(R.drawable.view_top_right_border_blue);
                quiz.setBackgroundResource(R.drawable.view_top_right_border_black);
                dashboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                leaderboard.setBackgroundResource(R.drawable.view_top_right_border_black);
                stats.setBackgroundResource(R.drawable.view_top_right_border_black);
                startActivity(new Intent(statistics.this,ProfileActivity.class));
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
                startActivity(new Intent(statistics.this,Quiz.class));
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
                startActivity(new Intent(statistics.this,DashBoardActivity.class));
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
                startActivity(new Intent(statistics.this,LeaderBoardActivity.class));
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

            }
        });


    }
}