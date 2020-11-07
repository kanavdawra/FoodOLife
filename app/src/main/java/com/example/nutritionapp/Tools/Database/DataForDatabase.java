package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nutritionapp.Tools.Utility;

public class DataForDatabase {
    private final Context context;

    public DataForDatabase(Context context) {
        this.context=context;
    }

    public Database getDataBase(){
        return new Database(context,"FoodOLife",null,1);
    }

    public void AddQuizData(){

        String[] questions ={"What percentage of Americans eat enough vegetables?",
                "If you eat seafood, how often should you have it?",
                "Which of these sea foods should you NOT eat more of?",
                "Which of the following is a whole grain?",
                "How much of your grains should be whole grains?",
                "Which of the following are whole grains that may be listed as ingredients on a food label?",
                "What type of food is the biggest single source of sodium in the American diet?",
                "Where do Americans get the most added sugar in their diet?",
                "Which of the following is not an added sugar?",
                "Which of these is a solid fat?",
                "Which of these oils and fats is healthiest for cooking?"

        };

        String[] answer ={"About 9%",
                "Twice a Week",
                "King mackerel",
                "Popcorn",
                "At least 50%",
                "All of the above",
                "Burgers and sandwiches",
                "Soda, energy and sports drinks",
                "Fructose",
                "All of the above",
                "Canola oil"
        };

        String[] option1 ={"About 33%",
                "Once a Week",
                "King mackerel",
                "Popcorn",
                "At least 10%",
                "Whole oats",
                "Chicken dishes",
                "Grain-based desserts (cakes, cookies, and pies)",
                "High-fructose corn syrup",
                "Butter",
                "Butter"
        };

        String[] option2 ={"About 60%",
                "Twice a Week",
                "Salmon",
                "Couscous",
                "At least 20%",
                "Wild rice",
                "Pizza",
                "Dairy desserts",
                "Maple syrup",
                "Chicken fat",
                "Canola oil"
        };

        String[] option3 ={"About 9%",
                "Thrice a Week",
                "Tilapia",
                "Multi grain bread",
                "At least 30%",
                "All of the above",
                "Burgers and sandwiches",
                "Soda, energy and sports drinks",
                "Honey",
                "Hydrogenated oils",
                "Margarine"
        };

        String[] option4 ={"About 15%",
                "Four times a Week",
                "Shrimp",
                "Corn Tortilla",
                "At least 50%",
                "Whole-grain triticale",
                "Pasta and pizza dishes",
                "Candy",
                "Fructose",
                "All of the above",
                "Lard"
        };

        String[] optionSelected ={null,null,null,null,null,null,null,null,null,null,null};
        addQuizDataQuery(questions,answer,option1,option2,option3,option4,optionSelected);
    }

    private void addQuizDataQuery(String[] questions,String[] answer,String[] option1,
                                  String[] option2,String[] option3,String[] option4,
                                  String[] optionSelected){
        SQLiteDatabase db=getDataBase().getWritableDatabase();
        if(new Utility().getSharedPreferences(context,"AppData","QuizData",0)==0){
            for(int i=0;i<11;i++){
                db.execSQL(
                        "insert into foodolife_quiz values ("+i+",'"
                        +questions[i]+"','"
                        +answer[i]+"','"
                        +option1[i]+"','"
                        +option2[i]+"','"
                        +option3[i]+"','"
                        +option4[i]+"',"
                        +optionSelected[i]+
                        ")"
                );
            }
            new Utility().setSharedPreferences(context,"AppData","QuizData",1);
        }
    }
}
