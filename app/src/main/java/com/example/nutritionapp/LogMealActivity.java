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
import android.widget.ListView;
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
        TextView txtViewHeader = findViewById(R.id.header);
        ArrayList<Meal> m = new ArrayList<Meal>();;
        ArrayAdapter<Meal> adapter;
        date=(EditText) findViewById(R.id.enterDate);
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
        try {
            final String type = getIntent().getExtras().getString("type");
            txtViewHeader.setText(type);
            // Get the intent, verify the action and get the query
            meals =new DatabaseUtility(this).getMeal();
            ListView listViewMeals = findViewById(R.id.meal_list);
            final MealAdapter myAdapter = new MealAdapter(meals);
            listViewMeals.setAdapter(myAdapter);
            listViewMeals.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    AmountDialog = new Dialog(context);
                    food_id =i;
                    AmountDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    AmountDialog.setContentView(R.layout.amt_dialog);
                    AmountDialog.show();
                    amt = (EditText) AmountDialog.findViewById(R.id.amt_dia);
                    Button bt_yes = (Button)AmountDialog.findViewById(R.id.btn_yes);
                    Button bt_no = (Button)AmountDialog.findViewById(R.id.btn_no);

                    bt_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new DataForDatabase(LogMealActivity.this).addIntake(type, Integer.valueOf(amt.getText().toString()), date.getText().toString(), food_id);
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
            });


        } catch (Exception ex) {
            Log.e("meal", ex.getMessage());
        }
    }
}
