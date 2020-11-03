package com.example.nutritionapp.OnBoardFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class GetSex extends Fragment {
    View view;
    int sex=1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.get_sex, container, false);
        TextView next=view.findViewById(R.id.get_sex_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","GetAge");
                if(sex==0){
                    intent.putExtra("Sex","Female");
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Sex","Female");
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",1);

                    Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                }
                if(sex==1){
                    intent.putExtra("Sex","Male");
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",1);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Sex","Male");
                    Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                }


            }
        });

        final TextView male=view.findViewById(R.id.get_sex_male);
        final TextView female=view.findViewById(R.id.get_sex_female);
        male.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                sex=1;
                male.setBackground(getResources()
                        .getDrawable(R.drawable.blue_button_rounded_selected));
                female.setBackground(getResources()
                        .getDrawable(R.drawable.blue_button_rounded_un_selected));
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                sex=0;
                female.setBackground(getResources()
                        .getDrawable(R.drawable.blue_button_rounded_selected));
                male.setBackground(getResources()
                        .getDrawable(R.drawable.blue_button_rounded_un_selected));
            }
        });

        return view;
    }
}
