package com.example.ubertutors.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ubertutors.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddModules extends AppCompatActivity {
    Button saveBtn;
    CheckBox c1, c2, c3, c4, c5, c6, c7, c8;
    FirebaseDatabase database;
    DatabaseReference reference;
    Module module;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_modules);

        reference = database.getInstance().getReference().child("modules");

        module = new Module();

        saveBtn = findViewById(R.id.saveBtn);
        c1 = findViewById(R.id.modCode1);
        c2 = findViewById(R.id.modCode2);
        c3 = findViewById(R.id.modCode3);
        c4 = findViewById(R.id.modCode4);
        c5 = findViewById(R.id.modCode5);
        c6 = findViewById(R.id.modCode6);
        c7 = findViewById(R.id.modCode7);
        c8 = findViewById(R.id.modCode8);

        final String mCode1 = "MATH301";
        final String mName1 = "Advanced Linear Algebra";

        final String mCode2 = "MATH302";
        final String mName2 = "Advanced Real Analysis";

        final String mCode3 = "MATH303";
        final String mName3 = "Modern Algebra";

        final String mCode4 = "MATH304";
        final String mName4 = "Complex Analysis";

        final String mCode5 = "WRPV301";
        final String mName5 = "Advanced Programming 1";

        final String mCode6 = "WRPV302";
        final String mName6 = "Advanced Programming 2";

        final String mCode7 = "WRLV301";
        final String mName7 = "Automata Theory";

        final String mCode8 = "WRDB301";
        final String mName8 = "Database Systems";

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //if there's any change in the database
                if(dataSnapshot.exists()){
                    i = (int)dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Modules have been added", Toast.LENGTH_SHORT)
                        .show();
                startActivity(new Intent(getApplicationContext(), MainActivityStudent.class));
                //Set all modules to Inactive initially
                //module.setStatus("Inactive");
                if(c1.isChecked()){
                    module.setModuleCode(mCode1);
                    module.setModuleName(mName1);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName1);
                    map.put("moduleCode", mCode1);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

                if(c2.isChecked()){
                    module.setModuleCode(mCode2);
                    module.setModuleName(mName2);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName2);
                    map.put("moduleCode", mCode2);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

                if(c3.isChecked()){
                    module.setModuleCode(mCode3);
                    module.setModuleName(mName3);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName3);
                    map.put("moduleCode", mCode3);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

                if(c4.isChecked()){
                    module.setModuleCode(mCode4);
                    module.setModuleName(mName4);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName4);
                    map.put("moduleCode", mCode4);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

                if(c5.isChecked()){
                    module.setModuleCode(mCode5);
                    module.setModuleName(mName5);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName5);
                    map.put("moduleCode", mCode5);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

                if(c6.isChecked()){
                    module.setModuleCode(mCode6);
                    module.setModuleName(mName6);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName6);
                    map.put("moduleCode", mCode6);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

                if(c7.isChecked()){
                    module.setModuleCode(mCode7);
                    module.setModuleName(mName7);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName7);
                    map.put("moduleCode", mCode7);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

                if(c8.isChecked()){
                    module.setModuleCode(mCode8);
                    module.setModuleName(mName8);
                    reference = FirebaseDatabase.getInstance().getReference()
                            .child("modules").push();
                    Map<String, Object> map = new HashMap<>();
                    map.put("moduleName", mName8);
                    map.put("moduleCode", mCode8);
                    map.put("status", "Inactive");

                    reference.setValue(map);
                }

            }
        });
    }
}
