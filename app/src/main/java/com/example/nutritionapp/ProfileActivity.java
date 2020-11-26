package com.example.nutritionapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nutritionapp.Tools.FireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity implements ValueEventListener {

        private FirebaseAuth firebaseAuth;
        private FirebaseDatabase firebaseDatabase;


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
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        final TextView usernam=findViewById(R.id.user_name_text);

        final ImageView profilepic=findViewById(R.id.circularprofilepic);

        final TextView maintainweight=findViewById(R.id.maintainweight);

        final TextView ageofuser=findViewById(R.id.age);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();

        DatabaseReference databaseReference=firebaseDatabase.getReference("UserId");
        Log.e("snap",firebaseAuth.getCurrentUser().getEmail());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                usernam.setText( dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("Name").getValue().toString());

                Picasso.get().load(  dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("ImageURI").getValue().toString()).into(profilepic);

                maintainweight.setText(dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("Goal").getValue().toString());

                ageofuser.setText(dataSnapshot.child( getUserId(firebaseAuth.getCurrentUser().getEmail()) ) .child("Age").getValue().toString());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        GraphView graph= (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series=new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0,1),


                new DataPoint(3,6),



        });

        graph.addSeries(series);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }



}