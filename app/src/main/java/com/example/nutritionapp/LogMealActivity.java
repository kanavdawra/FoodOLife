package com.example.nutritionapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutritionapp.Modals.Meal;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;

import java.util.ArrayList;
import android.content.Context;
import static java.security.AccessController.getContext;

public class LogMealActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_meal);
        TextView txtViewResults = findViewById(R.id.header);
        ArrayList<Meal> m = new ArrayList<Meal>();

        try {
            String type = getIntent().getExtras().getString("type");
            String date = getIntent().getExtras().getString("date");

            // Get the intent, verify the action and get the query
            Intent intent = getIntent();
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
                m =new DatabaseUtility(context).getMeal(query);
            }

        } catch (Exception ex) {
            Log.e("meal", ex.getMessage());
        }

    }

}
