package com.example.nutritionapp.OnBoardFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class GetHeight extends Fragment {
    View view;
    double height=0;
    int unit=-1;// cm=1 ft=0
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.get_height, container, false);
        TextView next=view.findViewById(R.id.get_height_next);
        final TextView heightText=view.findViewById(R.id.get_height_height);
        final TextView cm=view.findViewById(R.id.get_height_cm);
        final TextView ft=view.findViewById(R.id.get_height_ft);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","GetGoal");
                if(height!=0 && !heightText.getText().toString().equals("")){
                    intent.putExtra("Height",height);
                    if(unit==0){
                        intent.putExtra("HeightUnit","FT");
                        new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","HeightUnit","FT");
                    }
                    if(unit==1){
                        new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","HeightUnit","CM");
                        intent.putExtra("HeightUnit","CM");
                    }
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",4);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Height",(float)height);
                    Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                }
                if(heightText.getText().toString().equals("")){
                    heightText.setError("* Height cannot be empty");
                }
                if(height==0 && !heightText.getText().toString().equals("")){
                    Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                            .setBackgroundColor(Color.parseColor("#ff1744"))
                            .setText("Height cannot be 0").build().show();
                }
            }
        });


        Log.e("unit",String.valueOf(unit));

        cm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                Log.e("unitcm",String.valueOf(unit));
                if(!heightText.getText().toString().equals("")){

                    height=Double.parseDouble(heightText.getText().toString());
                    if(unit==1){}
                    else if (unit==0){

                        int foot=(int)Math.floor(height);
                        Log.e("foot",String.valueOf(foot));
                        int inch=(int)Math.round((height-foot)*10);
                        Log.e("inch",String.valueOf(inch));
                        if(inch>12){
                            heightText.setError("Inches cannot be greater that 12");
                            return;
                        }
                        else{
                            double cm=(foot*12*2.54)+(inch*2.54);
                            heightText.setText(round(cm));
                        }

                    }
                    else {}

                }
                unit=1;
                cm.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_selected));
                ft.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_un_selected));
            }
        });

        ft.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
            @Override
            public void onClick(View v) {
                Log.e("unitft",String.valueOf(unit));
                if(!heightText.getText().toString().equals("")){

                    height=Double.parseDouble(heightText.getText().toString());
                    if(unit==1){

                        double inchTotal=height/2.54;
                        double footTotal=inchTotal/12;
                        int foot=(int)Math.floor(footTotal);
                        double footRemain=footTotal-foot;
                        double inch=footRemain*12;
                        inch=Math.round(inch);
                        heightText.setText((int)foot+"."+(int)inch);
                    }
                    else if (unit==0){ }
                    else {}

                }
                unit=0;
                ft.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_selected));
                cm.setBackground(getResources().getDrawable(R.drawable.blue_button_rounded_un_selected));
            }
        });
        return view;
    }

    public String round(double height){
        height=Math.round(height*100)/100.0;
        if(Math.floor(height)==height){
            int heightInt=(int)height;
            return String.valueOf(heightInt);
        }
        else {
            return String.valueOf(height);
        }
    }
}
