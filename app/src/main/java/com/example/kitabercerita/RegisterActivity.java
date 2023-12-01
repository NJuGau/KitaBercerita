package com.example.kitabercerita;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class RegisterActivity extends AppCompatActivity {

    private EditText Email, Password;
    private  Button buttonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Email = findViewById(R.id.editTextTextEmailAddress);
        Password = findViewById(R.id.editTextTextPassword);
        buttonReg = findViewById(R.id.buttonReg);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-up button click
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                if (isValidRegister(email, password)) {
                    // Successful sign-up
                    Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Failed sign-up
                    Toast.makeText(RegisterActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
            //TODO: upload data to firebase
        });
    }
    private boolean isValidRegister(String email, String password) {
        // Implement your sign-up logic here
        // For simplicity, we'll consider it a valid sign-up if username and password are not empty
        return !email.isEmpty() && !password.isEmpty();
    }
}
