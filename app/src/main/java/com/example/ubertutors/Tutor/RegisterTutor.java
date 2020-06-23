package com.example.ubertutors.Tutor;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ubertutors.Login;
//import com.example.ubertutors.Profile;
import com.example.ubertutors.R;
import com.example.ubertutors.SplashActivity;
import com.example.ubertutors.Student.MainActivityStudent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterTutor extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText tFirstName, tLastName, tEmail, tPassword, tPhone;
    Button tRegisterBtn;
    TextView tLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String tutorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_tutor);

        tFirstName = findViewById(R.id.tutorFirstName);
        tLastName = findViewById(R.id.tutorLastName);
        tEmail = findViewById(R.id.tutorEmail);
        tPassword = findViewById(R.id.tutorPassword);
        tPhone = findViewById(R.id.tutorPhone);
        tRegisterBtn= findViewById(R.id.registerBtn);
        tLoginBtn   = findViewById(R.id.loginText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //If user is logged in already, send to MainActivity
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivityTutor.class));
            finish();
        }


        tRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = tEmail.getText().toString().trim();
                String password = tPassword.getText().toString().trim();
                final String firstName = tFirstName.getText().toString();
                final String lastName = tLastName.getText().toString();
                final String phone    = tPhone.getText().toString();
                final String userType = "tutor";

                //Validation
                if(TextUtils.isEmpty(email)){
                    tEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    tPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    tPassword.setError("Password Must be >= 6 Characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            // send verification link

                            FirebaseUser fuser = fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(
                                            RegisterTutor.this,
                                            "Verification email has been sent.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());
                                }
                            });

                            Toast.makeText(
                                    RegisterTutor.this, "User Created.",
                                    Toast.LENGTH_SHORT).show();
                            //Saves user's information
                            tutorID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference =
                                    fStore.collection("tutors").document(tutorID);
                            Map<String,Object> tutor = new HashMap<>();
                            tutor.put("firstName",firstName);
                            tutor.put("lastName", lastName);
                            tutor.put("email",email);
                            tutor.put("phone",phone);
                            tutor.put("userType",userType);
                            //insert into cloud db
                            documentReference.set(tutor).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+ tutorID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivityTutor.class));

                        }else {
                            Toast.makeText(RegisterTutor.this, "Error ! " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        tLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}
