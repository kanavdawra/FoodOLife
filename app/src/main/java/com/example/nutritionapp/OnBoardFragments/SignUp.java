package com.example.nutritionapp.OnBoardFragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nutritionapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;

import de.mateware.snacky.Snacky;

import static android.app.Activity.RESULT_OK;


public class SignUp extends Fragment {

    View view;
    String name,email,password,confirmPassword;
    TextView nameText,emailText,passwordText,confirmPasswordText,submit;
    CircularImageView signUpImageView;
    Bitmap selectedImage;
    private static final int RESULT_LOAD_IMAGE = 5164654;
    private FirebaseAuth mAuth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sign_up, container, false);
        mAuth = FirebaseAuth.getInstance();
        TextView clickHere=view.findViewById(R.id.sign_up_click_here);
        submit=view.findViewById(R.id.sign_up_submit);

        clickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("MainActivity");
                intent.putExtra("Task","SignIn");
                Objects.requireNonNull(getActivity()).sendBroadcast(intent);


            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });

        signUpImageView=view.findViewById(R.id.sign_up_profile_image);
        signUpImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        return view;
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                assert imageUri != null;
                final InputStream imageStream = Objects.requireNonNull(getActivity())
                        .getContentResolver().openInputStream(imageUri);
                selectedImage = BitmapFactory.decodeStream(imageStream);
                signUpImageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
        }
    }
    public void getData(){
        nameText=view.findViewById(R.id.sign_up_fullName);
        emailText=view.findViewById(R.id.sign_up_email);
        passwordText=view.findViewById(R.id.sign_up_password);
        confirmPasswordText=view.findViewById(R.id.sign_up_confirm_password);
        name=nameText.getText().toString();
        email=emailText.getText().toString();
        password=passwordText.getText().toString();
        confirmPassword=confirmPasswordText.getText().toString();
        dataValidation();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void dataValidation(){
        boolean check=true;
        if(name.equals("")){
            nameText.setError("* Name is required");
            check=false;
        }
        if(email.equals("")){
            emailText.setError("* Email is required");
            check=false;
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailText.setError("Email is not valid");
            check=false;
        }
        if(password.equals("")){
            passwordText.setError("* Password is required");
            check=false;
        }
        if(confirmPassword.equals("")){
            confirmPasswordText.setError("* Password is required");
            check=false;
        }
        if(!password.equals(confirmPassword)){
            confirmPasswordText.setError("Password and Confirm Password does not match");
            check=false;
        }
        if(password.length()<6){
            confirmPasswordText.setError("Password must be at least 6 characters");
            passwordText.setError("Password must be at least 6 characters");
            check=false;
        }
        if(check){
            submit.setBackground(getResources().getDrawable(R.drawable.blue_button_selected));
            submit.setText("Signing up...");
            submit.setTextColor(Color.parseColor("#3d5aff"));
            createUser();
        }
    }

    public void createUser(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                    .setBackgroundColor(Color.parseColor("#00c853"))
                                    .setText("Success. Please verify your email").build().show();
                            submit.setBackground(getResources().getDrawable(R.drawable.blue_button_un_selected));
                            submit.setText("Sign Up");
                            submit.setTextColor(Color.parseColor("#ffffff"));
                            saveUserData();

                        } else {

                            try {
                                throw Objects.requireNonNull(task.getException());
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                        .setBackgroundColor(Color.parseColor("#ff1744"))
                                        .setText("Try Entering Strong Password").build().show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                        .setBackgroundColor(Color.parseColor("#ff1744"))
                                        .setText("Invalid Credentials").build().show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                                        .setBackgroundColor(Color.parseColor("#ff1744"))
                                        .setText("User Already Exists.").build().show();
                            } catch(Exception e) {
                                Log.e("Error", "createUserWithEmail:failure", task.getException());
                            }


                            submit.setBackground(getResources().getDrawable(R.drawable.blue_button_un_selected));
                            submit.setText("Sign Up");
                            submit.setTextColor(Color.parseColor("#ffffff"));


                        }

                    }
                });
    }

    public void saveUserData(){


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("UserId").child(getUserId(email));

        myRef.child("Name").setValue(name);
        myRef.child("Email").setValue(email);
        myRef.child("Password").setValue(password);
        saveImage();
    }

    public int saveImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        //FirebaseStorage customStorage = FirebaseStorage.getInstance("gs://nutrition-app-a847c.appspot.com");
        StorageReference storageRef = storage.getReference();
        StorageReference spaceRef = storageRef.child("User Data/User Profile Image/"+email+".jpg");

        signUpImageView.setDrawingCacheEnabled(true);
        signUpImageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) signUpImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = spaceRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Snacky.builder().setActivity(Objects.requireNonNull(getActivity()))
                        .setBackgroundColor(Color.parseColor("#ff1744"))
                        .setText("Image Upload Failed, Try Again inside App").build().show();
                Log.e("Image","Image Upload Failed");


            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.e("Image","Image Upload Success");

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("UserId").push();

                myRef.child("Image").setValue(email+".jpg");
            }
        });

        return 1;
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
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
