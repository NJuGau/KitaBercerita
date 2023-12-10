package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {

    private EditText Email, Password, Username, Phonenumber;
    private Button buttonReg;
    FirebaseDatabase db;
    DatabaseReference rf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        Username = findViewById(R.id.Username);
        Phonenumber = findViewById(R.id.PhoneNumber);
        buttonReg = findViewById(R.id.buttonReg);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-up button click

                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String username = Username.getText().toString();
                String phoneNumber = Phonenumber.getText().toString();
                if (isValidRegister(username, email, password, phoneNumber)) {
                    db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
                    rf = db.getReference("User");
                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("email", email);
                    userMap.put("password", password);
                    userMap.put("phoneNumber", phoneNumber);
                    userMap.put("image", 1);
                    userMap.put("status", "No Status");

                    rf.child(username).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("Z", "Post successfully sent");
                            Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });



                } else {
                    // Failed sign-up
                    Toast.makeText(RegisterActivity.this, "Registration invalid!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private boolean isValidRegister(String username, String email, String password, String phoneNumber) {
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phoneNumber.isEmpty();
    }
}
