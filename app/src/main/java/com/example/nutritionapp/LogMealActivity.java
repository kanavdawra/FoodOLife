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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nutritionapp.Modals.Meal;
import com.example.nutritionapp.Tools.Database.DataForDatabase;
import com.example.nutritionapp.Tools.Database.Database;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.example.nutritionapp.Tools.Utility;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;

import org.w3c.dom.Text;

import static java.security.AccessController.getContext;

public class LogMealActivity extends AppCompatActivity {
    Context context;
    DatePickerDialog picker;
    EditText date;
    ArrayList<Meal> meals;
    Dialog AmountDialog;
    EditText amt;
    int food_id;
    String spinnerItem;
    String food_type;

    TextView food_serving_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_meal);
        context = this;
        food_type= String.valueOf(getIntent().getBundleExtra("type"));
        TextView header=findViewById(R.id.log_meal_header);
                header.setText(food_type);

        food_serving_save=findViewById(R.id.save_food_serving);

        setAmountDialog();

        LinearLayout imageforspinner=findViewById(R.id.spinnerview);

        meals=new DatabaseUtility(context).getMeal();

        Log.e("Meal 2",meals.get(1).getName());

        imageforspinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListView listforsps=findViewById(R.id.listforspinnerpopulation);
                listforsps.setVisibility(View.VISIBLE);
                MealAdapter myadapter=new MealAdapter(meals,LogMealActivity.this,(TextView) findViewById(R.id.log_meal_spinner_textView),listforsps);
                listforsps.setAdapter(myadapter);

            }
        });

        food_serving_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id= (int)new Utility().getSharedPreferences(context,"TempData","CurrentFoodid",1);
                int amount=(int)new Utility().getSharedPreferences(context,"TempData","CurrentFoodServing",2);


                Database database=new DatabaseUtility(context).getDataBase();

                database.getWritableDatabase()
                        .execSQL("insert into food_intake (amount,food_id,date,type) values ("+
                                amount+"," +
                                id+",'"+
                                date+"','"+
                                food_type+"')");


            }
        });

    }








    private void setAmountDialog(){

        date=(EditText) findViewById(R.id.log_meal_get_date);

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
    }

    public void setSpinnerItem(String spinnerItem) {
        this.spinnerItem = spinnerItem;

    }
}
