package com.example.nutritionapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.nutritionapp.Modals.Meal;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {
    //this,android.R.layout.simple_spinner_item, meals
    ArrayList<Meal> m;
    public SpinnerAdapter(@NonNull Context context, int textViewResourceId, @NonNull ArrayList<Meal> meals) {
        super(context, textViewResourceId, meals);
        this.m = meals;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.WHITE);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(m.get(position).getName());
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }
    @Override
    public View getDropDownView(int i, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner, parent, false);
        } else {
            view = convertView;
        }

        TextView textView1 = (TextView) view.findViewById(R.id.name);
        TextView textView2 = (TextView) view.findViewById(R.id.cal);
        textView1.setText(m.get(i).getName());
        textView2.setText("Calorie :"+m.get(i).getCalorie());

        return view;
    }
}
