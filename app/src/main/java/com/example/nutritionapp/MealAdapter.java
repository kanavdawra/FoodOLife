package com.example.nutritionapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nutritionapp.Modals.Meal;

import java.util.List;

public class MealAdapter extends BaseAdapter {
    List<Meal> mealList;
    LayoutInflater layoutInflater;
    Context context;
    TextView spinnerTextView;
    ListView listView;
    public MealAdapter(List<Meal> m,Context context,TextView spinnerTextView, ListView listView) {
        this.mealList = m;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        this.spinnerTextView=spinnerTextView;
        this.listView=listView;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

            view = layoutInflater.inflate(R.layout.spinnerlayout, null);


        TextView spinnerItemTextView=view.findViewById(R.id.make_shift_spinner_item_textView);
        LinearLayout spinnerItem=view.findViewById(R.id.make_shift_spinner_item);

        spinnerItemTextView.setText(mealList.get(i).getName());

        spinnerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               spinnerTextView.setText(mealList.get(i).getName());
               listView.setVisibility(View.GONE);
            }
        });



        return view;
    }
}