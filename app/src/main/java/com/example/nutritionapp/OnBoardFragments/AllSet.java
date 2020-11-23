package com.example.nutritionapp.OnBoardFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class AllSet extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_set, container, false);
        Log.e("Allseti","Allset1");
        TextView next=view.findViewById(R.id.all_set_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","NextActivity");
                new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard","1");
                Log.e("Allset","Allset");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });
        return view;
    }
}
