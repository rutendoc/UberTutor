package com.example.ubertutors.Student;

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
import com.example.ubertutors.R;
import com.example.ubertutors.Student.Profile.ProfileStudent;
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

public class RegisterStudent extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText sFirstName, sLastName, sEmail, sPassword, sPhone;
    Button sRegisterBtn;
    TextView sLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;
    String studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_student);

        sFirstName = findViewById(R.id.studentFirstName);
        sLastName = findViewById(R.id.studentLastName);
        sEmail = findViewById(R.id.studentEmail);
        sPassword = findViewById(R.id.studentPassword);
        sPhone = findViewById(R.id.studentPhone);
        sRegisterBtn= findViewById(R.id.registerBtn);
        sLoginBtn   = findViewById(R.id.loginText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //If user is logged in already, send to MainActivity
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), ProfileStudent.class));
            finish();
        }


        sRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = sEmail.getText().toString().trim();
                String password = sPassword.getText().toString().trim();
                final String firstName = sFirstName.getText().toString();
                final String lastName = sLastName.getText().toString();
                final String phone    = sPhone.getText().toString();
                final String userType = "student";

                //Validation
                if(TextUtils.isEmpty(email)){
                    sEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    sPassword.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    sPassword.setError("Password Must be >= 6 Characters");
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
                                            RegisterStudent.this,
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
                                    RegisterStudent.this, "User Created.",
                                    Toast.LENGTH_SHORT).show();
                            //Saves user's information
                            studentID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference =
                                    fStore.collection("students").document(studentID);
                            Map<String,Object> student = new HashMap<>();
                            student.put("firstName",firstName);
                            student.put("lastName", lastName);
                            student.put("email",email);
                            student.put("phone",phone);
                            student.put("userType",userType);
                            //insert into cloud db
                            documentReference.set(student).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user profile is created for "+ studentID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), AddModules.class));

                        }else {
                            Toast.makeText(RegisterStudent.this, "Error! " +
                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });



        sLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

    }
}
