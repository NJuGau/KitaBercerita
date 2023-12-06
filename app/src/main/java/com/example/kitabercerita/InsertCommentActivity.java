package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitabercerita.model.Comment;
import com.example.kitabercerita.model.Post;
import com.example.kitabercerita.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InsertCommentActivity extends AppCompatActivity {

    EditText commentDescriptionFld;
    Button commentSubmitBtn;
    TextView backBtn;
//    FirebaseDatabase db;
//    DatabaseReference rf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_comment);

        FirebaseApp.initializeApp(this);
//        db = FirebaseDatabase.getInstance();
        commentDescriptionFld = findViewById(R.id.commentDescriptionFld);
        commentSubmitBtn = findViewById(R.id.insertCommentSubmitBtn);
        backBtn = findViewById(R.id.commentBackBtn);

        commentSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Comment comment = new Comment("sadasdasdasd", commentDescriptionFld.getText().toString(), User.getCurrentUser().getUserId(), "get post id");
                System.out.println("hello");
//                rf = db.getReference("Comment");
//                rf.child(comment.getCommentId()).setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        System.out.println("Comment successfully sent");
//                        Toast.makeText(InsertCommentActivity.this, "Congratulations!", Toast.LENGTH_SHORT).show();
//                    }
//                });
                Intent intent = new Intent(InsertCommentActivity.this, HomeActivity.class);
                startActivity(intent);
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