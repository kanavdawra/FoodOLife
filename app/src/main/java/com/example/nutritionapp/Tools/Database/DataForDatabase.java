package com.example.nutritionapp.Tools.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nutritionapp.Tools.Utility;

import java.util.Date;

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

    public void addIntake(String Type, int amount, String date, int food_id){
        SQLiteDatabase db=getDataBase().getWritableDatabase();
        db.execSQL(
                "insert into food_intake(food_id,amount,date,type) values ('"
                        +food_id+"','"
                        +amount+"','"
                        +date+"','"
                        +Type+"'"+
                        ")"
        );
    }

    public void AddFoodData(){
        //This is designed for 100gm of food data

        String[] food_name={"Hamburger",
                "Pad Thai",
                "Udon Noodles",
                "Butter Chicken",
                "Enchiladas",
                "Burritos with Minced meat",
                "Pancakes",
                "spaghetti",
                "Masala Dosa",
                "Pizza",
                "Japanese Sushi",
                "Lamb curry",
                "Yorkshire Pudding",
                "Sausage Roll",
                "Ikan Bakar",
                "Kimchi Stew",
                "Nova Scotian Lobster Rolls",
                "Saskatoon Berry Pie",
                "Montreal Style Smoked meat",
                "Gazpacho",
                "pulpo a la Gallega",
                "Paella",
                "Raclette",
                "Rosti",
                "Cheese Fondue",
                "Cheese Waffle",
                "chocolate milk Shake",
                "Soft drink"
        };

        Double[] food_carbohydrates={30300.0,28600.0,16800.0,7000.0,25500.0,27500.0,28000.0,43200.0,2900.0,36000.0,19000.0
        ,0.0,19000.0,26600.0,3000.0,4800.0,15200.0,42000.0,3300.0,4000.0,9800.0,15300.0,400.0,18200.0,1500.0
        ,52000.0,30200.0,12300.0};
        Double[] food_protein={13300.0,17500.0,2900.0,11500.0,11900.0,21000.0,6500.0,8100.0,3900.0,12000.0,3900.0
        ,25200.0,7000.0,11900.0,219000.0,10100.0,19400.0,2900.0,16700.0,800.0,20000.0,11300.0,29200.0,2200.0,15900.0
        ,10000.0,4200.0,0.0};
        Double[] food_fats={10100.0,14100.0,300.0,11500.0,8400.0,15500.0,9800.0,1300.0,3700.0,10000.0,9500.0
        ,21000.0,11500.0,32200.0,34000.0,7800.0,9900.0,9800.0,5000.0,3400.0,3200.0,9900.0,31600.0,7700.0,17200.0
        ,28000.0,4200.0,0.0};
        Double[] food_vitaminA={0.0,0.0,0.0,10.8,6.8,6.5,4.0,0.0,0.1,7.7,2.1,
        0.0,4.7,1.5,0.0,8.7,2.7,2.9,0.0,15.4,0.0,8.6,18.4,5.2,10.1,0.0,4.0,0.0};
        Double[] food_vitaminB={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0
        ,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        Double[] food_vitaminC={0.0,0.0,0.0,9.6,1.7,4.5,0.5,0.0,0.6,2.5,3.6
        ,0.0,0.0,0.0,0.0,1.1,5.7,18.9,0.0,84.4,0.0,19.8,0.0,14.3,0.2,0.0,0.0,0.0};
        Double[] food_vitaminE={0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0
        ,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
        Double[] food_sodium={396.0,684.0,4.8,125.5,528.7,710.0,440.0,1.0,94.0,640.0,268.5,
        73.2,213.3,631.4,50.0,307.2,434.0,144.2,1183.3,319.8,900.0,365.4,696.8,68.9,381.5,1080.0,
        116.7,9.0};
        Double[] food_iron={0.0,0.0,4.4,9.6,10.2,0.0,10.0,0.0,4.7,15.0,3.6
        ,10.7,4.7,11.9,0.0,6.4,9.9,6.9,13.3,2.0,0.0,5.0,0.8,5.2,1.2,8.0,2.1,0.0};
        Double[] food_fibre={1100.0,2400.0,700.0,1000.0,1900.0,2000.0,0.0,2500.0,900.0,2500.0,1600.0,
        0.0,0.6,1100.0,0.0,1.5,0.9,0.0,0.0,900.0,800.0,500.0,0.0,2000.0,100.0,2000.0,0.3,0.0};
        Double[] food_calcium={0.0,0.0,0.8,7.2,11.0,0.0,16.8,0.0,0.4,15.0,2.2
        ,1.3,7.9,1.3,0.0,9.8,6.8,1.8,0.0,1.2,0.0,2.3,98.8,1.3,41.1,16.0,13.3,0.0};
        Double[] food_calories={390.0,158.0,84.6,178.0,229.8,335.0,227.5,210.5,170.0,285.0,174.5,
        300.0,205.4,448.0,126.0,128.2,234.5,259.0,133.3,480.0,153.0,198.9,403.2,148.2,269.3
        ,500.0,175.1,45.0};

        addfoodintodb(food_name,food_carbohydrates,food_protein,food_fats,food_vitaminA,food_vitaminB,food_vitaminC,food_vitaminE,
                food_sodium,food_iron,food_fibre,food_calcium,food_calories);


    }

    private void addfoodintodb(String[] name,Double[] carbo,Double[] prot,Double[] fat,Double [] vitamina,Double[] vitaminb,
                               Double[] vitaminc,Double[] vitammine,Double[] sodium,Double[] iron,Double[] fibre,Double[] calcium,
                               Double[] calorie){

        SQLiteDatabase dt=getDataBase().getWritableDatabase();
        if(new Utility().getSharedPreferences(context,"AppData","dataforfood",0)==0){
            for(int i=0;i<28;i++){

                dt.execSQL(
                        "insert into food_nutrients values("+i+",'"
                                +name[i]+"','"
                                  +carbo[i]+"','"
                                   +prot[i]+"','"
                                +fat[i]+"','"
                                +vitamina[i]+"','"
                                +vitaminb[i]+"','"
                                +vitaminc[i]+"','"
                                +vitammine[i]+"','"
                                +sodium[i]+"','"
                                +iron[i]+"','"
                                +fibre[i]+"','"
                                +calcium[i]+"','"
                                +calorie[i]+
                                "')"



                );
            }

            new Utility().setSharedPreferences(context,"AppData","dataforfood",1);


        }




    }



}
