package com.example.nutritionapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import com.example.nutritionapp.Interface.MainActivityInterface;
import com.example.nutritionapp.OnBoardFragments.AllSet;
import com.example.nutritionapp.OnBoardFragments.GetAge;
import com.example.nutritionapp.OnBoardFragments.GetGoal;
import com.example.nutritionapp.OnBoardFragments.GetHeight;
import com.example.nutritionapp.OnBoardFragments.GetSex;
import com.example.nutritionapp.OnBoardFragments.GetWeight;
import com.example.nutritionapp.OnBoardFragments.SignIn;
import com.example.nutritionapp.OnBoardFragments.SignUp;
import com.example.nutritionapp.Receiver.MainActivityReceiver;
import com.example.nutritionapp.Tools.FireBase;
import com.example.nutritionapp.Tools.Utility;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    MainActivityReceiver mainActivityReceiver;
    MainActivityInterface mainActivityInterface;

    String Sex,Goal,WeightUnit,HeightUnit;
    double Age,Weight,Height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EnterFullScreen();

        mainActivityInterface=new MainActivityInterface() {
            @Override
            public void SignUp() {
                loadFragment(new SignUp());
            }

            @Override
            public void SignIn() {
                loadFragment(new SignIn());
            }

            @Override
            public void GetSex() {
                loadFragment(new GetSex());
            }

            @Override
            public void GetAge(String sex) {
                Sex=sex;
                loadFragment(new GetAge());
            }

            @Override
            public void GetWeight(double age) {
                Age=age;
                loadFragment(new GetWeight());
            }

            @Override
            public void GetHeight(double weight,String weightUnit) {
                Weight=weight;
                WeightUnit=weightUnit;
                loadFragment(new GetHeight());
            }

            @Override
            public void GetGoal(double height,String heightUnit) {
                Height=height;
                HeightUnit=heightUnit;
                loadFragment(new GetGoal());
            }

            @Override
            public void AllSet(String goal) {
                Goal=goal;
                loadFragment(new AllSet());
            }
            @Override
            public void NextActivity() {
                SetFirebaseUserData();

                startActivity(new Intent(MainActivity.this, DashBoardActivity.class));
            }

        };
        RegisterReceiver(mainActivityInterface);
        loadFragment(new SignIn());
    }

    public void RegisterReceiver(MainActivityInterface mainActivityInterface){
        mainActivityReceiver = new MainActivityReceiver(mainActivityInterface);
        registerReceiver(mainActivityReceiver, new IntentFilter("MainActivity"));
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        androidx.fragment.app.FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_Fragment, fragment);
        fragmentTransaction.commit();
    }

    public void EnterFullScreen(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Objects.requireNonNull(getSupportActionBar()).hide();
    }

    public void SetFirebaseUserData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserId").child(getUserId(FirebaseAuth.getInstance().getCurrentUser().getEmail()));


        myRef.child("Age").setValue(Age);
        myRef.child("Sex").setValue(Sex);
        myRef.child("Weight").setValue(Weight);
        myRef.child("WeightUnit").setValue(WeightUnit);
        myRef.child("Height").setValue(Height);
        myRef.child("HeightUnit").setValue(HeightUnit);
        myRef.child("Goal").setValue(Goal);
        myRef.child("OnBoard").setValue("1");
        new Utility().setSharedPreferences(MainActivity.this,"UserData","OnBoard","1");

    }
    public String getUserId(String email){
        StringBuilder userId= new StringBuilder();
        int temp=-2;
        for(int i=0;i<email.length();i++){
            if(email.charAt(i)=='@'){
                temp=-1;
            }
            if(temp==-1 && email.charAt(i)=='.'){
                break;
            }
            userId.append(email.charAt(i));
        }
        return userId.toString();
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mainActivityReceiver);
    }


    @Override
    protected void onResume() {
        super.onResume();
        RegisterReceiver(mainActivityInterface);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        RegisterReceiver(mainActivityInterface);
    }
}