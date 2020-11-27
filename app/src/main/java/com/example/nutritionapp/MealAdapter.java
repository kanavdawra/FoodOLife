package com.example.nutritionapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
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
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.example.nutritionapp.Tools.Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MealAdapter extends BaseAdapter {
    int serving=0;
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
                foodservingdialog(i);

            }
        });





        return view;
    }


    private void foodservingdialog(final int i){


        final Dialog AmountDialog;




        AmountDialog = new Dialog(context);
        AmountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        AmountDialog.setContentView(R.layout.amt_dialog);
        AmountDialog.setCanceledOnTouchOutside(false);

        TextView bt_save = AmountDialog.findViewById(R.id.amt_dialog_serving_save);
        TextView bt_cancel = AmountDialog.findViewById(R.id.amt_dialog_serving_cancel);

        final TextView weightText=AmountDialog.findViewById(R.id.amt_dialog_serving_edittext);

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    serving=Integer.parseInt(weightText.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    weightText.setError("* Serving  cannot be empty");
                }
                if(weightText.getText().toString().equals("")){
                    weightText.setError("* Serving  cannot be empty");
                }

                else if(serving==0){
                    weightText.setError("Serving  cannot be 0");
                }
                else{
                    spinnerTextView.setText(mealList.get(i).getName()+", Amt: "+serving );
                    new Utility().setSharedPreferences(context,"TempData","CurrentFoodServing",serving);
                    new Utility().setSharedPreferences(context,"TempData","CurrentFoodid",i);


                    AmountDialog.dismiss();
                }

            }
        });

                AmountDialog.show();

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmountDialog.dismiss();
            }
        });

    }
}