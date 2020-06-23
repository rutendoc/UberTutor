package com.example.ubertutors.Student.Profile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ubertutors.Login;
import com.example.ubertutors.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class ProfileStudent extends AppCompatActivity {
    private static final int GALLERY_INTENT_CODE = 1023 ;
    TextView sFullName,sEmail,sPhone,verifyMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String studentId;
    Button resendCode;
    Button resetPassLocal,changeProfileImage;
    FirebaseUser student;
    ImageView sProfileImage;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_student);



        sPhone = findViewById(R.id.studentProfilePhone);
        sFullName = findViewById(R.id.studentProfileName);
        sEmail = findViewById(R.id.studentProfileEmail);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);

        sProfileImage = findViewById(R.id.studentProfileImage);
        changeProfileImage = findViewById(R.id.changeProfile);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        //To ensure user pic is not reused by different users
        StorageReference profileRef = storageReference.child("students/"+fAuth.getCurrentUser().
                getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(sProfileImage);
            }
        });

        resendCode = findViewById(R.id.resendCode);
        verifyMsg = findViewById(R.id.verifyMsg);


        studentId = fAuth.getCurrentUser().getUid();
        student = fAuth.getCurrentUser();

        if(!student.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    student.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification email has been sent.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(v.getContext(), "Email not sent. Please try again later",
                                    Toast.LENGTH_SHORT).show();
                            Log.d("tag", "onFailure: Email not sent." + e.getMessage());
                        }
                    });
                }
            });

            verifyMsg.setVisibility(View.GONE);
            resendCode.setVisibility(View.GONE);
        }

//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String name = dataSnapshot.child(studentId).child("firstName")
//                        .getValue(String.class);
//
//                sFullName.setText(name);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        DocumentReference documentReference = fStore.collection("student").document(studentId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    sPhone.setText(documentSnapshot.getString("phone"));
                    sFullName.setText(documentSnapshot.getString("firstName"/* + " " + "lastName"*/));/////////////////////
                    sEmail.setText(documentSnapshot.getString("email"));
                }else {
                    Log.d("tag", "onEvent: Document does not exists");
                }
            }
        });


        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetPassword = new EditText(v.getContext());

                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Enter New Password >= 6 Characters long.");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // extract the email and send reset link
                        String newPassword = resetPassword.getText().toString();
                        student.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileStudent.this, "Password Reset Successfully.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileStudent.this, "Password Reset Failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // close
                    }
                });

                passwordResetDialog.create().show();

            }
        });

        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);

            }
        });

        //loadUserInfo();

    }

    private void loadUserInfo() {
        FirebaseUser student = fAuth.getCurrentUser();

        //String dispalyName = student.getDisplayName();
        if(student.getDisplayName() != null){
            sFullName.setText(student.getDisplayName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                //profileImage.setImageURI(imageUri);

                uploadImageToFirebase(imageUri);


            }
        }

    }

    private void uploadImageToFirebase(Uri imageUri) {
        // upload image to firebase storage
        final StorageReference fileRef =
                storageReference.child("students/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(sProfileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileStudent.this, "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
