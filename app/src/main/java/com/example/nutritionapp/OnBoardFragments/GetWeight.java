package com.example.nutritionapp.OnBoardFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionapp.R;
import com.example.nutritionapp.Tools.Utility;

import java.util.Objects;

import de.mateware.snacky.Snacky;

public class GetWeight extends Fragment {
    View view;
    double weight=0;
    int unit=-1;// kg=1 lb=0
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.get_weight, container, false);
        TextView next=view.findViewById(R.id.get_weight_next);
        final TextView weightText=view.findViewById(R.id.get_weight_weight);
        final TextView kg=view.findViewById(R.id.get_weight_kg);
        final TextView lb=view.findViewById(R.id.get_weight_lb);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","GetHeight");
                if(!weightText.getText().toString().equals("")){
                    weight=Double.parseDouble(weightText.getText().toString());
                }
                if(weight!=0){
                    intent.putExtra("Weight",weight);
                    if(unit==0){
                        intent.putExtra("WeightUnit","LB");
                        new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","WeightUnit","LB");

                    }
                    if(unit==1){
                        intent.putExtra("WeightUnit","KG");
                        new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","WeightUnit","KG");
                    }
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",3);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Weight",(float) weight);
                    Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                }
                if(weightText.getText().toString().equals("")){
                    weightText.setError("* Weight cannot be empty");
                }
                if(weight==0 && !weightText.getText().toString().equals("")){
                    Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                            .setBackgroundColor(Color.parseColor("#ff1744"))
                            .setText("Weight cannot be 0").build().show();
                }





            }
        });

        kg.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if(!weightText.getText().toString().equals("")){

                    weight=Double.parseDouble(weightText.getText().toString());
                    if(unit==1){}
                    else if (unit==0){
                        weight=weight*.454;
                        weightText.setText(round(weight));
                    }
                    else {}

                }
                unit=1;
                kg.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_selected));
                lb.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_un_selected));
            }
        });

        lb.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                if(!weightText.getText().toString().equals("")){

                    weight=Double.parseDouble(weightText.getText().toString());
                    if(unit==1){

                        weight=(weight*1000)/454;
                        weightText.setText(round(weight));
                    }
                    else if (unit==0){ }
                    else {}

                }
                unit=0;
                lb.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_selected));
                kg.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_un_selected));
            }
        });

        return view;
    }
    public String round(double weight){
        weight=Math.round(weight*100)/100.0;
        if(Math.floor(weight)==weight){
            int weightInt=(int)weight;
            return String.valueOf(weightInt);
        }
        else {
            return String.valueOf(weight);
        }
    }
}
