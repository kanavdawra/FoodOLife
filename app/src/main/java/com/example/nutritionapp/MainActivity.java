package com.example.nutritionapp;


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
                loadFragment(new GetGoal());
            }

            @Override
            public void AllSet(String goal) {
                Goal=goal;
                loadFragment(new AllSet());
            }
            @Override
            public void NextActivity() {
               finish();
               startActivity(new Intent(MainActivity.this,ProfileActivity.class));
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
    protected void onStart() {
        super.onStart();
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        int onBoard= (int) new Utility().getSharedPreferences(this,"UserData","OnBoard",0);
        int verified=0;
        if(!Objects.equals(currentUser.getEmail(), "")) {
            if (currentUser.isEmailVerified()) {
                verified = 1;
            }
            if (!Objects.equals(currentUser.getEmail(), "") && onBoard == 0 && verified == 1) {
                Intent intent = new Intent("MainActivity");
                intent.putExtra("Task", "GetSex");
                sendBroadcast(intent);
            }
            if (!Objects.equals(currentUser.getEmail(), "") && onBoard == 6 && verified == 1) {
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
            }
        }

    }
}