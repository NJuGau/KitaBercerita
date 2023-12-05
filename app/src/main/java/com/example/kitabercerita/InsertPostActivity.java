package com.example.kitabercerita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kitabercerita.model.Post;
import com.example.kitabercerita.model.User;

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
                Post post = new Post("129031209381293",postDescriptionFld.getText().toString(), User.getCurrentUser().getUserId());
                //TODO: Upload data to firebase
                Intent intent = new Intent(InsertPostActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}