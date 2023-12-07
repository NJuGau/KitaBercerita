package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    Button saveBtn;
    EditText nameEt, statusEt;
    TextView emailTv, backBtn;
    String name, status;
    DatabaseReference rf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);

        emailTv = findViewById(R.id.emailTv);
        nameEt = findViewById(R.id.nameEt);
        statusEt = findViewById(R.id.statusEt);

        //ambil dari db
        rf = FirebaseDatabase.getInstance().getReference().child("User").child("chrism"); //TODO: ganti jd id current user
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot sn) {
                emailTv.setText(sn.child("email").getValue(String.class));
                nameEt.setText(sn.child("username").getValue(String.class));
                statusEt.setText(sn.child("status").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //disini
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ambil perubahannya
                name = String.valueOf(nameEt.getText());
                status = String.valueOf(statusEt.getText());

                //update db
                Map<String, Object> updates = new HashMap<>();
                updates.put("username", name);
                updates.put("status", status);

                rf.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data updated successfully
                                Log.d("Firebase", "Age updated successfully");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle error
                                Log.e("Firebase", "Error updating: " + e.getMessage());
                            }
                        });

                //back
                finish();
            }
        });


    }
}