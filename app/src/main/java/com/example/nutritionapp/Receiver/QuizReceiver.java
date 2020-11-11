package com.example.nutritionapp.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.nutritionapp.Interface.QuizInterface;

public class QuizReceiver extends BroadcastReceiver {
    QuizInterface quizInterface;
    public QuizReceiver(QuizInterface quizInterface) {
        this.quizInterface=quizInterface;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String task = intent.getStringExtra("Task");
        assert task != null;
        if(task.equals("GetReady")){
            quizInterface.GetReady();
        }
        if(task.equals("QuizQuestions")){
            quizInterface.QuizQuestions();
        }
        if(task.equals("QuizFinish")){
            quizInterface.QuizFinish();
        }
    }
}
