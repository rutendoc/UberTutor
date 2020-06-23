package com.example.ubertutors;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ubertutors.Student.AddModules;
import com.example.ubertutors.Student.Home.HomeFragment;
//import com.example.ubertutors.Student.MainActivityStudent;
import com.example.ubertutors.Student.MainActivityStudent;
import com.example.ubertutors.Student.Profile.ProfileStudent;
import com.example.ubertutors.Student.RegisterStudent;
//import com.example.ubertutors.Tutor.ProfileTutor;
import com.example.ubertutors.Tutor.RegisterTutor;
import com.google.firebase.auth.FirebaseAuth;

public class SelectionActivity extends AppCompatActivity {
    private ImageView goToStudentRegImg;
    private TextView goToStudentRegTxt;
    private ImageView goToTutorRegImg;
    private TextView goToTutorRegTxt;
    private ImageView goToAdminRegImg;
    private TextView goToAdminRegTxt;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        goToStudentRegImg = findViewById(R.id.buttonStudent);
        goToStudentRegTxt = findViewById(R.id.textView1);

        goToTutorRegImg = findViewById(R.id.buttonTutor);
        goToTutorRegTxt = findViewById(R.id.textView2);

        goToAdminRegImg = findViewById(R.id.buttonAdmin);
        goToAdminRegTxt = findViewById(R.id.textView3);

        fAuth = FirebaseAuth.getInstance();

        goToStudentRegImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if logged registered already
                //If user is logged in already, send to Profile
                if(fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), /*ListActivity*/MainActivityStudent.class));
                    finish();
                }
                else
                    startActivity(new Intent(getApplicationContext(), RegisterStudent.class));
            }
        });
        goToStudentRegTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if logged registered already
                //If user is logged in already, send to Profile
                if(fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), MainActivityStudent.class));
                    finish();
                }
                else
                    startActivity(new Intent(getApplicationContext(), RegisterStudent.class));
            }
        });

        goToTutorRegImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if logged registered already
                //If user is logged in already, send to Profile
                if(fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), /*ProfileTutor*/com.example.ubertutors.Tutor.Home.HomeFragment.class));
                    finish();
                }
                else
                    startActivity(new Intent(getApplicationContext(), RegisterTutor.class));
            }
        });
        goToTutorRegTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check if logged registered already
                //If user is logged in already, send to Profile
                if(fAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), /*ProfileTutor*/com.example.ubertutors.Tutor.Home.HomeFragment.class));
                    finish();
                }
                else
                    startActivity(new Intent(getApplicationContext(), RegisterTutor.class));
            }
        });
    }
}
