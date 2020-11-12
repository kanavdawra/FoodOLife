package com.example.nutritionapp.Tools;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBase {
    public DatabaseReference getReference(){
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        return database.getReference("UserId").child(getUserId(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
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


}
