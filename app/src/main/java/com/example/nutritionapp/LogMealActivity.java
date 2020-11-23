package com.example.nutritionapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutritionapp.Modals.Meal;
import com.example.nutritionapp.Tools.Database.DataForDatabase;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import static java.security.AccessController.getContext;

public class LogMealActivity extends AppCompatActivity {
    Context context;
    DatePickerDialog picker;
    EditText date;
    ArrayList<Meal> meals;
    Dialog AmountDialog;
    EditText amt;
    int food_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_meal);
        context = this;


        ArrayList<Meal> m = new ArrayList<Meal>();
         final ArrayAdapter<Meal> adapter;
        date=(EditText) findViewById(R.id.log_meal_get_date);
        ImageView imageforspinner=findViewById(R.id.spinnerview);
        final ListView listforspinner=findViewById(R.id.listforspinnerpopulation);
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(LogMealActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

       imageforspinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               meals=new DatabaseUtility(context).getMeal();
               ListView listforsps=findViewById(R.id.listforspinnerpopulation);
               MealAdapter myadapter=new MealAdapter(meals);
               listforsps.setAdapter(myadapter);

            


            }
        });

    }
}
