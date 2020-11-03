package com.example.nutritionapp.OnBoardFragments;

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

public class GetAge extends Fragment {
    View view;
    double age=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.get_age, container, false);
        TextView next=view.findViewById(R.id.get_age_next);
        final TextView ageText=view.findViewById(R.id.get_age_age);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","GetWeight");
                String ageString=ageText.getText().toString();
                if(!ageString.equals("")){
                    age=Double.parseDouble(ageText.getText().toString());
                }
                if(!ageString.equals("") && age>=13){
                    age=Double.parseDouble(ageText.getText().toString());
                    intent.putExtra("Age",age);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",2);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Age",(float) age);
                    Objects.requireNonNull(getActivity()).sendBroadcast(intent);
                }
                if(ageString.equals("")){
                    ageText.setError("*Age is required");
                }
                if(age<13 && !ageText.getText().toString().equals("")){
                    Log.e("Age",String.valueOf(age));
                    Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                            .setBackgroundColor(Color.parseColor("#ff1744"))
                            .setText("Age must be at least 13").build().show();
                }

            }
        });


        return view;
    }
}
