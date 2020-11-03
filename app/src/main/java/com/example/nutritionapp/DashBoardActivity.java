package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashBoardActivity extends AppCompatActivity {
    /*LogMeals breakfast = new LogMeals(R.drawable.bread_48,"Add breakfast", "Recommanded 480cal");
    LogMeals lunch = new LogMeals(R.drawable.bento_48,"Add lunch", "Recommanded 480cal");
    LogMeals dinner = new LogMeals(R.drawable.food_and_wine_48,"Add dinner", "Recommanded 480cal");
    LogMeals snack = new LogMeals(R.drawable.doughnut_48,"Add snack", "Recommanded 480cal");
    List <LogMeals> logMeals = new ArrayList<>(Arrays.asList(breakfast,lunch,dinner,snack));*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);
        /*ListView listViewMeal = findViewById(R.id.listViewMeal);
        final MealsAdapter myAdapter = new MealsAdapter(logMeals);
        listViewMeal.setAdapter(myAdapter);

        listViewMeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/
        EditText date = findViewById(R.id.editDate);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        date.setText(formattedDate);

    }
}