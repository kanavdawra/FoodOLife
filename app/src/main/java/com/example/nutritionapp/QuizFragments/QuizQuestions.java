package com.example.nutritionapp.QuizFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionapp.Modals.Quiz;
import com.example.nutritionapp.R;
import com.example.nutritionapp.Tools.Database.DatabaseUtility;
import com.example.nutritionapp.Tools.FireBase;
import com.example.nutritionapp.Tools.Utility;

import java.util.Objects;

public class QuizQuestions extends Fragment {

    int index;
    int marks;
    View view;
    TextView questionNo,question,option1,option2,option3,option4,marksText,overlayText;
    ImageView leftArrow;
    Quiz[] quiz;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.quiz_questions, container, false);
        questionNo=view.findViewById(R.id.quiz_question_number);
        question=view.findViewById(R.id.quiz_question_question);
        option1=view.findViewById(R.id.quiz_question_option1);
        option2=view.findViewById(R.id.log_meal_header);
        option3=view.findViewById(R.id.quiz_question_option3);
        option4=view.findViewById(R.id.quiz_question_option4);
        marksText=view.findViewById(R.id.quiz_question_marks);
        overlayText=view.findViewById(R.id.quiz_question_overlay_text);
        leftArrow=view.findViewById(R.id.quiz_question_back);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetReady();
            }
        });
        overlayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setOption1();
        setOption2();
        setOption3();
        setOption4();
        quiz=new DatabaseUtility(getActivity()).getQuiz();
        getMarksIndex();
        UpdateQuestion();
        return view;
    }

    private void setOption1(){
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    QuestionAnswerCheck(option1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setOption2(){
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    QuestionAnswerCheck(option2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setOption3(){
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    QuestionAnswerCheck(option3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setOption4(){
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    QuestionAnswerCheck(option4);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void ColorOptions(TextView selected,TextView right){
        if(selected==right){
            selected.setBackgroundResource(R.drawable.green_button_round_un_selected);
        }
        else {
            selected.setBackgroundResource(R.drawable.red_button_round_un_selected);
            right.setBackgroundResource(R.drawable.green_button_round_un_selected);
        }
    }
    private void ReColorOptions(){

        option1.setBackgroundResource(R.drawable.blue_button_rounded_un_selected);
        option2.setBackgroundResource(R.drawable.blue_button_rounded_un_selected);
        option3.setBackgroundResource(R.drawable.blue_button_rounded_un_selected);
        option4.setBackgroundResource(R.drawable.blue_button_rounded_un_selected);


    }

    private void UpdateDatabase(String optionSelected,int id){
        SQLiteDatabase db= new DatabaseUtility(getActivity()).getDataBase().getWritableDatabase();

        db.execSQL(
                "update foodolife_quiz set optionSelected='"+optionSelected+"' where id="+id
        );
    }

    private void UpdateQuestion(){
        if(index<11 && quiz[index].getOptionSelected()==null ) {
            SetData(quiz[index].getQuestion(), quiz[index].getOption1(), quiz[index].getOption2(),
                    quiz[index].getOption3(), quiz[index].getOption4(), "Question " + (index + 1));
            ReColorOptions();
        }
        else if(index>=11){
            QuizFinish();
        }
        else {
            index++;
            UpdateQuestion();
        }
    }

    @SuppressLint("SetTextI18n")
    private void QuestionAnswerCheck(TextView optionSelectedTextView) throws InterruptedException {
        String option1=this.option1.getText().toString();
        String option2=this.option2.getText().toString();
        String option3=this.option3.getText().toString();
        String option4=this.option4.getText().toString();
        String optionSelected=optionSelectedTextView.getText().toString();
        if(optionSelected.equals(quiz[index].getAnswer())){
            ColorOptions(optionSelectedTextView,optionSelectedTextView);
            marks++;
            this.marksText.setText(marks+"/"+(index+1));
            UpdateDatabase(optionSelected,index);

        }
        else if(option1.equals(quiz[index].getAnswer())){
            ColorOptions(optionSelectedTextView,this.option1);
            this.marksText.setText(marks+"/"+(index+1));
            UpdateDatabase(optionSelected,index);

        }
        else if(option2.equals(quiz[index].getAnswer())){
            ColorOptions(optionSelectedTextView,this.option2);
            this.marksText.setText(marks+"/"+(index+1));
            UpdateDatabase(optionSelected,index);

        }
        else if(option3.equals(quiz[index].getAnswer())){
            ColorOptions(optionSelectedTextView,this.option3);
            this.marksText.setText(marks+"/"+(index+1));
            UpdateDatabase(optionSelected,index);

        }
        else if(option4.equals(quiz[index].getAnswer())){
            ColorOptions(optionSelectedTextView,this.option4);
            this.marksText.setText(marks+"/"+(index+1));
            UpdateDatabase(optionSelected,index);

        }

        overlayText.setVisibility(View.VISIBLE);
        overlayText.setText("5...");
        index++;
        setMarksIndex();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overlayText.setText("4...");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        overlayText.setText("3...");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                overlayText.setText("2...");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        overlayText.setText("1...");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                UpdateQuestion();
                                                overlayText.setVisibility(View.GONE);
                                            }
                                        },1000);
                                    }
                                },1000);
                            }
                        },1000);
                    }
                },1000);
            }
        },1000);


    }


    @SuppressLint("SetTextI18n")
    private void SetData(String question, String option1, String option2
            , String option3, String option4, String questionNo){

        this.questionNo.setText(questionNo);
        this.question.setText(question);
        this.option1.setText(option1);
        this.option2.setText(option2);
        this.option3.setText(option3);
        this.option4.setText(option4);
        this.marksText.setText(marks+"/"+(index+1));

    }

    private void getMarksIndex() {
        marks= (int) new Utility().getSharedPreferences(getActivity(),"AppData","QuizMarks",0);
        index= (int) new Utility().getSharedPreferences(getActivity(),"AppData","QuizIndex",0);
    }

    private void setMarksIndex() {
        new Utility().setSharedPreferences(getActivity(),"AppData","QuizMarks",marks);
        new Utility().setSharedPreferences(getActivity(),"AppData","QuizIndex",index);
        new FireBase().getReference().child("QuizMarks").setValue(marks);
        new FireBase().getReference().child("QuizIndex").setValue(index);
    }

    private void GetReady(){
        Intent intent=new Intent("Quiz");
        intent.putExtra("Task","GetReady");
        Objects.requireNonNull(getActivity()).sendBroadcast(intent);
    }
    private void QuizFinish(){
        Intent intent=new Intent("Quiz");
        intent.putExtra("Task","QuizFinish");
        Objects.requireNonNull(getActivity()).sendBroadcast(intent);
    }

}
