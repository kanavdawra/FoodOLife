package com.example.nutritionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nutritionapp.Modals.Meal;

import java.util.List;

public class MealAdapter extends BaseAdapter {
    List<Meal> mealList;

    public MealAdapter(List<Meal> m) {
        this.mealList = m;
    }
    @Override
    public int getCount() {
        return mealList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.spinnerlayout,
                    viewGroup, false);
        }

        TextView idforspinner=view.findViewById(R.id.textforspinner);

        idforspinner.setText(mealList.get(i).getName());




        return view;
    }
}