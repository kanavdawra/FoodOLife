package com.example.nutritionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MealsAdapter extends BaseAdapter {
    List<LogMeals> logMeals;
    public MealsAdapter(List<LogMeals> logMeals) {
        this.logMeals = logMeals;
    }

    @Override
    public int getCount() {
        return logMeals.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Here, i corresponds to the ith item/view in the adapter
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.log_meal,
                    viewGroup, false);
        }
        TextView header = view.findViewById(R.id.feature_header);
        TextView description = view.findViewById(R.id.feature_describe);
        ImageView imgViewIcon = view.findViewById(R.id.icon);
        header.setText(logMeals.get(i).header);
        description.setText(logMeals.get(i).description);
        imgViewIcon.setImageResource(logMeals.get(i).img);
        return view;
    }
}
