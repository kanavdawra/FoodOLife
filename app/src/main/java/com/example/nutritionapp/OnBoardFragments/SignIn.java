package com.example.nutritionapp.OnBoardFragments;

import android.annotation.SuppressLint;
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

import com.example.nutritionapp.R;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

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
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            if(user.isEmailVerified()){

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
                            updateUI();

                        } else {
                            Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                    .setBackgroundColor(Color.parseColor("#ff1744"))
                                    .setText("Authentication Failed").build().show();
                            signIn.setText(R.string.sign_in);
                        }


                    }
                });
    }

    public void updateUI(){

        Intent intent=new Intent("MainActivity");
        intent.putExtra("Task","GetSex");
        Objects.requireNonNull(getActivity()).sendBroadcast(intent);

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
