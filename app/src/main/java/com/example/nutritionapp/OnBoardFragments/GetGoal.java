package com.example.nutritionapp.OnBoardFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionapp.R;
import com.example.nutritionapp.Tools.Utility;

import java.util.Objects;

public class GetGoal extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.get_goal, container, false);
        LinearLayout lose_weight=view.findViewById(R.id.get_goal_lose_weight);
        LinearLayout maintain_weight=view.findViewById(R.id.get_goal_maintain_weight);
        LinearLayout gain_weight=view.findViewById(R.id.get_goal_gain_weight);
        lose_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","AllSet");
                intent.putExtra("Goal","Lose Weight");
                new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",5);
                new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Goal","Lose Weight");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });

        maintain_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","AllSet");
                intent.putExtra("Goal","Maintain Weight");
                new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",5);
                new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Goal","Maintain Weight");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });

        gain_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","AllSet");
                intent.putExtra("Goal","Gain Weight");
                new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",5);
                new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","Goal","Gain Weight");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });
        return view;
    }
}
