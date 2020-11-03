package com.example.nutritionapp;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        GraphView graph= (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series=new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0,1),


                new DataPoint(3,6),



        });

        graph.addSeries(series);

    }
}