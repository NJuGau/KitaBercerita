package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kitabercerita.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
public class LoginActivity extends AppCompatActivity {
    private EditText Username, Password;
    private Button Login;
    FirebaseDatabase db;
    DatabaseReference rf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //GET ANDROID PERMISSION
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }



        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.editTextTextPassword2);
        Login = findViewById(R.id.LoginBtn);

        Button goToRegisterButton = findViewById(R.id.toRegister);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userName = Username.getText().toString();
                String password = Password.getText().toString();

                db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
                rf = db.getReference("User");
                if(isValidLogin(userName,password)){

                    rf.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(userName)){
                                String getPassword = snapshot.child(userName).child("password").getValue(String.class);
                                if (password.equals(getPassword)){
                                    String email = snapshot.child(userName).child("email").getValue(String.class);
                                    String status = snapshot.child(userName).child("status").getValue(String.class);
                                    String phoneNumber = snapshot.child(userName).child("phoneNumber").getValue(String.class);
                                    Integer image = snapshot.child(userName).child("image").getValue(Integer.class);
                                    Toast.makeText(LoginActivity.this, "Success!, you are now logged in!", Toast.LENGTH_SHORT).show();
                                    User.setCurrentUser(new User(userName, email, password, status, phoneNumber, image));
                                    Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "error...! Username or password incorrect!!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean isValidLogin(String email, String password) {
        return !email.isEmpty() && !password.isEmpty();
    }
}