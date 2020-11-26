package com.example.nutritionapp.OnBoardFragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionapp.DashBoardActivity;
import com.example.nutritionapp.ProfileActivity;
import com.example.nutritionapp.R;
import com.example.nutritionapp.Tools.Utility;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import de.mateware.snacky.Snacky;

public class SignIn extends Fragment {

    View view;
    String email;
    int onBoard;
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    TextView signIn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sign_in, container, false);
        mAuth = FirebaseAuth.getInstance();
        TextView clickHere=view.findViewById(R.id.sign_in_click_here);
        signIn=view.findViewById(R.id.sign_in_submit);
        final TextView signIn_google=view.findViewById(R.id.sign_in_google);
        clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","SignUp");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SI","SignInButton");
                GetData();
            }
        });
        signIn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckGoogleSignIn();
            }
        });
        return view;
    }

    public void GetData(){
        MaterialEditText emailText=view.findViewById(R.id.sign_in_email);
        MaterialEditText passwordText=view.findViewById(R.id.sign_in_password);
        email= Objects.requireNonNull(emailText.getText()).toString();
        String password= Objects.requireNonNull(passwordText.getText()).toString();

        int check=-1;

        if(email.equals("")){
            emailText.setError("*This field is required");
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Not a valid email id");
        }
        else if(password.equals("")){
            passwordText.setError("*This field is required");
        }
        else if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            CheckSignIn(email,password);
        }

    }

    public void CheckSignIn(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            if(user.isEmailVerified()){
                                CheckOnBoard();
                            }
                            if(!user.isEmailVerified()){
                                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                        .setBackgroundColor(Color.parseColor("#00c853"))
                                        .setText("Please verify your email first").build().show();
                                user.sendEmailVerification();
                            }
                        } else {
                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                        .setBackgroundColor(Color.parseColor("#ff1744"))
                                        .setText("Email or Password is Wrong").build().show();
                            }
                            catch (Exception e){
                                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                        .setBackgroundColor(Color.parseColor("#ff1744"))
                                        .setText("Something went wrong! Please Try Again").build().show();
                                Log.e("SignIn",e.toString());
                            }

                        }


                    }
                });
    }

    public void CheckGoogleSignIn(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleSignInClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()), gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                        .setBackgroundColor(Color.parseColor("#ff1744"))
                        .setText("Authentication Failed").build().show();

            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        signIn.setText(R.string.signing_in);
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            email=user.getEmail();
                            String name=user.getDisplayName();

                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference myRef = database.getReference("UserId")
                                    .child(getUserId(email));

                            myRef.child("Name").setValue(name);
                            myRef.child("Email").setValue(email);
                            myRef.child("Password").setValue("");
                            CheckOnBoard();

                        } else {
                            Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                    .setBackgroundColor(Color.parseColor("#ff1744"))
                                    .setText("Authentication Failed").build().show();
                            signIn.setText(R.string.sign_in);
                        }


                    }
                });
    }

    public void CheckOnBoard(){
//        FirebaseAuth mAuth=FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        int onBoard= (int) new Utility().getSharedPreferences(Objects.requireNonNull(getActivity()),"UserData","OnBoard",0);
//        Log.e("OnBoard",String.valueOf(onBoard));
//        int verified=0;
//        if(currentUser!=null) {
//            if (currentUser.isEmailVerified()) {
//                verified = 1;
//            }
//            if (currentUser != null && onBoard == 0 && verified == 1) {
//                Intent intent = new Intent("MainActivity");
//                intent.putExtra("Task", "GetSex");
//                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
//            }
//            if (currentUser != null && onBoard == 6 && verified == 1) {
//                getActivity().finish();
//                getActivity().startActivity(new Intent(getActivity(), DashBoardActivity.class));
//
//            }
//        }


        final FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();

        DatabaseReference databaseReference=firebaseDatabase.getReference("UserId");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                float age= 0;
                int quizIndex= 0,quizMarks=0;
                String email= null;
                String goal= null;
                float height= 0;
                String heightUnit= null;
                String imageURI= null;
                String name= null;
                String sex= null;
                float weight= 0;
                String weightUnit= null;
                String onBoard = "0";
                try {
                    age = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child(getUserId(Objects.requireNonNull(firebaseAuth.getCurrentUser().getEmail()))).child("Age").getValue()).toString());

                    email = firebaseAuth.getCurrentUser().getEmail();

                    goal = Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("Goal").getValue()).toString();

                    height = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("Height").getValue()).toString());

                    heightUnit = Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("HeightUnit").getValue()).toString();

                    imageURI = Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("ImageURI").getValue()).toString();

                    name = Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("Name").getValue()).toString();

                    sex = Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("Sex").getValue()).toString();

                    weight = Float.parseFloat(Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("Weight").getValue()).toString());

                    weightUnit = Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("WeightUnit").getValue()).toString();

                    onBoard = Objects.requireNonNull(dataSnapshot.child(getUserId(firebaseAuth.getCurrentUser().getEmail())).child("OnBoard").getValue()).toString();

                    quizIndex = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(getUserId(Objects.requireNonNull(firebaseAuth.getCurrentUser().getEmail()))).child("QuizIndex").getValue()).toString());

                    quizMarks = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(getUserId(Objects.requireNonNull(firebaseAuth.getCurrentUser().getEmail()))).child("QuizMarks").getValue()).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (onBoard.equals("1")) {
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "Age", age);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "Email", email);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "Goal", goal);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "Height", height);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "HeightUnit", heightUnit);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "ImageURI", imageURI);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "Name", name);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "Sex", sex);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "Weight", weight);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "WeightUnit", weightUnit);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "UserData", "OnBoard", onBoard);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "AppData", "QuizIndex", quizIndex);
                    new Utility().setSharedPreferences(Objects.requireNonNull(getActivity()), "AppData", "QuizMarks", quizMarks);
                    updateUI(1);
                } else {
                    updateUI(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void updateUI(int task){

        if(task==0){
            Intent intent=new Intent("MainActivity");
            intent.putExtra("Task","GetSex");
            try {
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (task==1){
            Objects.requireNonNull(getActivity()).startActivity(new Intent(getActivity(),DashBoardActivity.class));
            getActivity().finish();
        }



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
