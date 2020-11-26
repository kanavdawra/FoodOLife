package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.nutritionapp.Modals.SodiumData;
import com.example.nutritionapp.Modals.calorieintake;
import com.example.nutritionapp.Modals.piechartgraph;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class statistics extends AppCompatActivity {

    private LineChart chart;
    private PieChart piechart;
    ArrayList<Entry> yvalues;
    List<String> xaxisvalues;
    ArrayList<calorieintake> valuesforgraph;
    ArrayList<PieEntry> datavalues;
    ArrayList<piechartgraph> dataforpie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        bottomNavigationBar();

        getCaloriGraph();
        getAvgMacrosPie();
        getSodiumChart();

    }

    private void getCaloriGraph(){
        valuesforgraph=new ArrayList<>();
        valuesforgraph=new DatabaseUtility(this).getcalorie();
        chart=(LineChart)findViewById(R.id.barchart);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        yvalues=new ArrayList<>();
        xaxisvalues=new ArrayList<>();
        for(int i=0;i<valuesforgraph.size();i++){
            yvalues.add(new Entry(i,(float)valuesforgraph.get(i).getcalorie()));
            xaxisvalues.add(valuesforgraph.get(i).getDate().substring(0,5));
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

    private void getAvgMacrosPie(){

        datavalues=new ArrayList<>();
        piechart=(PieChart)findViewById(R.id.piechart);
        dataforpie=new ArrayList<>();
        dataforpie=new DatabaseUtility(this).getpie();
        String labels[]=new String[]{"Fats","Carbohydrates","Proteins"};

        PieEntry fats=new PieEntry((float) (dataforpie.get(0).getFat()/dataforpie.get(0).getTotalcalorie())*100);
        fats.setLabel("Fats");
        datavalues.add(fats);
        PieEntry carbohydrates=new PieEntry((float) (dataforpie.get(0).getCarbohydates()/dataforpie.get(0).getTotalcalorie())*100);
        carbohydrates.setLabel("Carbohydrates");
        datavalues.add(carbohydrates);
        PieEntry protein=new PieEntry((float) (dataforpie.get(0).getProtein()/dataforpie.get(0).getTotalcalorie())*100);
        protein.setLabel("Proteins");
        datavalues.add(protein);

        PieData pData=new PieData();
        PieDataSet pDataSet=new PieDataSet(datavalues,"");
        pDataSet.setColors(new int[]{Color.parseColor("#3d5aff"),Color.parseColor("#ff1744"),Color.parseColor("#00c853")});
        pDataSet.setValueTextColor(Color.WHITE);
        pData.setDataSet(pDataSet);
        piechart.setData(pData);
        piechart.setNoDataTextColor(Color.WHITE);
        piechart.getLegend().setTextColor(Color.WHITE);
        piechart.setDrawEntryLabels(false);
        piechart.setEntryLabelColor(Color.WHITE);
        piechart.setHoleColor(Color.parseColor("#212121"));
        Description description=new Description();
        description.setText("Percentage of calories from Macros");
        piechart.setBackgroundColor(Color.parseColor("#212121"));
        description.setTextColor(Color.WHITE);
        piechart.setDescription(description);
        piechart.invalidate();



    }

    private void getSodiumChart(){
        ArrayList<SodiumData> sodiumDataArrayList=new DatabaseUtility(this).getSodiumData();

        LineChart lineChart=findViewById(R.id.statistics_sodium_chart);

        ArrayList<Double> yValues=new ArrayList<>();
        ArrayList<String> xValues=new ArrayList<>();
        ArrayList<Entry> entries=new ArrayList<>();
        for(int i=0;i<sodiumDataArrayList.size();i++){
            yValues.add(sodiumDataArrayList.get(i).getSodium());
            xValues.add(sodiumDataArrayList.get(i).getDate().substring(0,5));
            Entry entry =new Entry(i, (float) sodiumDataArrayList.get(i).getSodium());
            entries.add(entry);


        }


        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);

        XAxis xAxis=lineChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xaxisvalues));
        lineChart.animateX(2000);
        lineChart.invalidate();
        lineChart.getLegend().setEnabled(false);
        lineChart.getDescription().setEnabled(false);
        LineDataSet set1=new LineDataSet(entries,"Sodium");
        ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        lineChart.getLegend().setTextColor(Color.WHITE);
        set1.setColor(Color.WHITE);

        dataSets.add(set1);
        LineData data=new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();
        set1.setColor(Color.parseColor("#ff1744"));

        lineChart.setBackgroundColor(Color.parseColor("#212121"));
        lineChart.setBorderColor(Color.WHITE);
        lineChart.setGridBackgroundColor(Color.WHITE);
        lineChart.getAxisLeft().setTextColor(Color.WHITE);
        lineChart.getAxisRight().setTextColor(Color.WHITE);
        lineChart.getXAxis().setTextColor(Color.WHITE);
        Description description=new Description();
        description.setText("Daily Sodium Content");
        lineChart.setDescription(description);
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