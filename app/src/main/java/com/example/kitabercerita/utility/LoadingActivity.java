package com.example.kitabercerita.utility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.kitabercerita.R;

public class LoadingActivity extends AppCompatActivity {

    ProgressBar loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        loadingBar = findViewById(R.id.loadingBar);
        
    }
}