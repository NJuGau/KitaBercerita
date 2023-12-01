package com.example.kitabercerita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity {

    private EditText Email, Password, Username, Phonenumber;
    private  Button buttonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        buttonReg = findViewById(R.id.buttonReg);
        Username = findViewById(R.id.Username);
        Phonenumber = findViewById(R.id.PhoneNumber);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-up button click
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String username = Username.getText().toString();
                String phoneNumber = Phonenumber.getText().toString();
                if (isValidRegister(username, email, password, phoneNumber)) {
                    // Successful sign-up
                    Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                } else {
                    // Failed sign-up
                    Toast.makeText(RegisterActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }

            }
            //TODO: upload data to firebase
        });
    }
    private boolean isValidRegister(String username, String email, String password, String phoneNumber) {
        return !username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !phoneNumber.isEmpty();
    }
}
