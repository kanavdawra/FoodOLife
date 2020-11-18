package com.example.nutritionapp.QuizFragments;

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

public class GetReady extends Fragment {
    View view;
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.get_ready, container, false);
        TextView next=view.findViewById(R.id.get_ready_next);
        TextView marksIndex=view.findViewById(R.id.get_ready_marks);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("Quiz");
                intent.putExtra("Task","QuizQuestions");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });
        int index= (int) new Utility().getSharedPreferences(getActivity(),"AppData","QuizIndex",0);
        int marks= (int) new Utility().getSharedPreferences(getActivity(),"AppData","QuizMarks",0);
        marksIndex.setText(marks+"/"+(index));
        return view;
    }
}
