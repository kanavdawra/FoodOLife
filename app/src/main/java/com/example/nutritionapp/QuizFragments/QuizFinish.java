package com.example.nutritionapp.QuizFragments;

import android.annotation.SuppressLint;
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

public class QuizFinish extends Fragment {
    View view;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_finish, container, false);


        TextView text=view.findViewById(R.id.quiz_finish_text);

        int index= (int) new Utility().getSharedPreferences(getActivity(),"AppData","QuizIndex",0);
        int marks= (int) new Utility().getSharedPreferences(getActivity(),"AppData","QuizMarks",0);

        text.setText("Your current score is "+marks+" out of "+(index)+" you can check you ranking in LEADERBOARD tab.");

        return view;
    }
}
