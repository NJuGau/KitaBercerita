package com.example.kitabercerita;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AuthenticationActivity extends AppCompatActivity {

    Button gotoLoginBtn, gotoRegisterBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        //GET ANDROID PERMISSION
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(AuthenticationActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(AuthenticationActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        gotoLoginBtn = findViewById(R.id.gotoLoginBtn);
        gotoRegisterBtn = findViewById(R.id.gotoRegisterBtn);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.authFragment, new LoginFragment());
        ft.commit();

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.authFragment, new LoginFragment());
                ft.commit();
            }
        });

        gotoRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.authFragment, new RegisterFragment());
                ft.commit();
            }
        });
    }
}