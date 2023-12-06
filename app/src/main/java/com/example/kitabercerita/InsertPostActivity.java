package com.example.kitabercerita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kitabercerita.model.Post;
import com.example.kitabercerita.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class InsertPostActivity extends AppCompatActivity {

    EditText postDescriptionFld;
    Button postSubmitBtn;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_post);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        postDescriptionFld = findViewById(R.id.postDescriptionFld);
        postSubmitBtn = findViewById(R.id.insertPostSubmitBtn);

        postSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Post post = new Post(postDescriptionFld.getText().toString(), User.getCurrentUser().getUserId());
                //TODO: Upload data to firebase
                HashMap<String, String> postMap = new HashMap<>();
                postMap.put("postDescription", postDescriptionFld.getText().toString());
                postMap.put("postUserId", "ajndsjfdsnj32knkj4");
                db.collection("Post").add(postMap);
                Intent intent = new Intent(InsertPostActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}