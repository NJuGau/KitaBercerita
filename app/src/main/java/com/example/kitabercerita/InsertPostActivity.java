package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitabercerita.model.Post;
import com.example.kitabercerita.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;

public class InsertPostActivity extends AppCompatActivity {

    EditText postDescriptionFld;
    Button postSubmitBtn;
    TextView backBtn;
    FirebaseDatabase db;
    DatabaseReference rf;
//    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_post);
//        FirebaseApp.initializeApp(this);
//        db = FirebaseFirestore.getInstance();
        postDescriptionFld = findViewById(R.id.postDescriptionFld);
        postSubmitBtn = findViewById(R.id.insertPostSubmitBtn);
        backBtn = findViewById(R.id.postBackBtn);

        postSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Post post = new Post("129031209381293",postDescriptionFld.getText().toString(), User.getCurrentUser().getUserId());
                Log.d("Z", "Clicked");
                HashMap<String, Object> postMap = new HashMap<>();
                postMap.put("postDescription", postDescriptionFld.getText().toString());
                postMap.put("postUserId", User.getCurrentUser().getUserId());
                postMap.put("postLikeCount", 0);
                postMap.put("postCommentCount", 0);

                db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
                rf = db.getReference("Post");
                String postId =FirebaseDatabase.getInstance().getReference().push().getKey();
                rf.child(postId).setValue(postMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Z", "Post successfully sent");
                        Toast.makeText(InsertPostActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}