package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitabercerita.utility.CommentAdapter;
import com.example.kitabercerita.utility.CommentClickListener;
import com.example.kitabercerita.model.Comment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity implements CommentClickListener {

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent = null;
        if(itemId == R.id.homeMenu) {
            intent = new Intent(this.getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }else if(itemId == R.id.searchMenu) {
            intent = new Intent(this.getApplicationContext(), SearchPostActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.profileMenu) {
            intent = new Intent(this.getApplicationContext(), ProfileViewActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.option_menu,menu);
        return true;
    }

    private ImageView profileImg;
    private TextView userTxt, descriptionTxt, likeCountTxt, commentCountTxt, backBtn;
    private ImageButton likeBtn, commentBtn;
    private RecyclerView commentRecyclerView;
    ArrayList<Comment> commentList;
    FirebaseDatabase db;
    DatabaseReference rf;
    private Button gotoInsertCommentBtn;
    CommentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        profileImg = findViewById(R.id.detailProfileImageView);
        userTxt = findViewById(R.id.detailUserTxt);
        descriptionTxt = findViewById(R.id.detailDescriptionTxt);
        likeCountTxt = findViewById(R.id.detailLikeCountTxt);
        commentCountTxt = findViewById(R.id.detailCommentCountTxt);
        likeBtn = findViewById(R.id.detailLikeBtn);
        commentBtn = findViewById(R.id.detailCommentBtn);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);
        gotoInsertCommentBtn = findViewById(R.id.gotoInsertCommentBtn);
        backBtn = findViewById(R.id.detailBackBtn);

        String postId = getIntent().getStringExtra("postId");

        db = FirebaseDatabase.getInstance();
        rf = db.getReference().child("Post").child(postId);
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userTxt.setText(snapshot.child("postUserId").getValue(String.class));//TODO: Try to get user object
                descriptionTxt.setText(snapshot.child("postDescription").getValue(String.class));
                likeCountTxt.setText(snapshot.child("postLikeCount").getValue(Integer.class).toString());
                commentCountTxt.setText(snapshot.child("postCommentCount").getValue(Integer.class).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostDetailActivity.this, "ERROR", Toast.LENGTH_SHORT);
            }
        });

        commentList = new ArrayList<>();
        db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        rf = db.getReference().child("Comment");
        commentList = new ArrayList<>();

        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentAdapter(this, commentList, this);
        commentRecyclerView.setAdapter(adapter);

        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("CommentGetter", "processing");
                commentList.clear();
                for(DataSnapshot sn : snapshot.getChildren()){
                    String commentPostId = sn.child("commentPostId").getValue(String.class);
                    if(commentPostId.equals(postId)){
                        String commentId = sn.getKey().toString();
//                    Log.d("CommentGetter", commentId);
                        String commentDescription = sn.child("commentDescription").getValue(String.class);
                        String commentUserId = sn.child("commentUserId").getValue(String.class);

                        Integer likeCount = sn.child("commentLikeCount").getValue(Integer.class);
                        Boolean isNotified = sn.child("commentIsNotified").getValue(Boolean.class);
                        commentList.add(new Comment(commentId, commentDescription, commentUserId, commentPostId, likeCount, isNotified));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        gotoInsertCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostDetailActivity.this, InsertCommentActivity.class);
                intent.putExtra("postId", postId);
                intent.putExtra("oldCommentCount", Integer.parseInt(commentCountTxt.getText().toString()));
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer oldLikeCount = Integer.parseInt(likeCountTxt.getText().toString());
                db.getReference().child("Post").child(postId).child("postLikeCount").setValue(oldLikeCount+1);
                oldLikeCount += 1;
                likeCountTxt.setText(oldLikeCount.toString());
            }
        });
    }

    @Override
    public void onClickLikeBtn(View view, int position) {
        Log.d("HomeActivity", "like btn on " + position + " clicked");
        String commentId = commentList.get(position).getCommentId();
        Integer oldLikeCount = commentList.get(position).getLikeCount();
        db.getReference().child("Comment").child(commentId).child("commentLikeCount").setValue(oldLikeCount+1);
        adapter.notifyDataSetChanged();
        Log.d("HomeActivity", "like btn on " + position + " finished");
    }
}