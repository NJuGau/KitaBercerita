package com.example.kitabercerita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InsertPostActivity extends AppCompatActivity {

    EditText postDescriptionFld;
    Button postSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_post);

        postDescriptionFld = findViewById(R.id.postDescriptionFld);
        postSubmitBtn = findViewById(R.id.insertPostSubmitBtn);

        postSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Insert Post data to firebase

                Intent intent = new Intent(InsertPostActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}