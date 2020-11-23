package com.example.nutritionapp.Modals;

public class QuizRankList {
    int quizIndex=0;
    int quizScore=0;
    String name="Name";
    String email="name@name.com";
    double Totalscore=0;

    public double getTotalscore() {
        return Totalscore;
    }

    public void setTotalscore(double totalscore) {
        Totalscore = totalscore;
    }



    public int getQuizIndex() {
        return quizIndex;
    }

    public void setQuizIndex(int quizIndex) {
        this.quizIndex = quizIndex;
    }

    public int getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }






}
