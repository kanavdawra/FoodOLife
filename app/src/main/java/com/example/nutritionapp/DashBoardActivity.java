package com.example.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DashBoardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText dateEdit = (EditText)findViewById(R.id.editDate);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = df.format(c);
        dateEdit.setText(formattedDate);
        ImageView iv= (ImageView)findViewById(R.id.imagePrevious);
        iv.setImageResource(R.drawable.previous_24);
        Button btn_breakfast = (Button) findViewById(R.id.btn_breakfast);
        btn_breakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                addMeal(1);
            }
        });
    }
    public void addMeal(int mealType){
//at this point cost is computed
        //Now create intent object
        dateEdit = (EditText)findViewById(R.id.editDate);
        Intent myResults = new Intent(DashBoardActivity.this, LogMealActivity.class);
        //Create a bundle and put values in it
        Bundle myBundle = new Bundle();
        myBundle.putInt("type", mealType); //syntax or put is keyname, value name
        myBundle.putString("date",dateEdit.getText().toString());
        //put the bundle into the intent
        myResults.putExtras(myBundle); //adds the bundle to the intent
        startActivity(myResults); //start activity with the intent object
    }
}