package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.kitabercerita.model.Comment;
import com.example.kitabercerita.model.User;
import com.example.kitabercerita.utility.CommentNotificationService;
import com.example.kitabercerita.utility.PostClickListener;
import com.example.kitabercerita.utility.PostAdapter;
import com.example.kitabercerita.model.Post;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class HomeActivity extends AppCompatActivity implements PostClickListener {

    ArrayList<Post> postList;
    FirebaseDatabase db;
    DatabaseReference rf;
    PostAdapter adapter;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent;
        if(itemId == R.id.homeMenu) {
            intent = new Intent(this.getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }else if(itemId == R.id.searchMenu) {
            intent = new Intent(this.getApplicationContext(), SearchPostActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.profileMenu) {
            intent = new Intent(this.getApplicationContext(), ProfileViewActivity.class);
            startActivity(intent);
        }else if (itemId == R.id.notify){
            Log.d("CommentPressed", "true");
            notifyUserComment();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.option_menu,menu);
        return true;
    }

    FloatingActionButton gotoInsertPostBtn;
    RecyclerView postView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        postView = findViewById(R.id.homePostView);
        gotoInsertPostBtn = findViewById(R.id.gotoInsertPostBtn);


        //go to insert post
        gotoInsertPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, InsertPostActivity.class);
                startActivity(intent);
            }
        });

        //recycler view bind

        postList = new ArrayList<>();
        db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        rf = db.getReference().child("Post");
        postList = new ArrayList<>();

        postView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(this, postList);
        adapter.setClickListener(this);
        postView.setAdapter(adapter);

        rf.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot sn : snapshot.getChildren()){

                    String postId = sn.getKey().toString();
                    String postDescription = sn.child("postDescription").getValue(String.class);
                    String postUserId = sn.child("postUserId").getValue(String.class);
                    Integer postLikeCount = sn.child("postLikeCount").getValue(Integer.class);
                    Integer postCommentCount = sn.child("postCommentCount").getValue(Integer.class);
                    postList.add(new Post(postId, postDescription, postUserId, postLikeCount, postCommentCount));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onClickLikeBtn(View view, int position) {
        Log.d("HomeActivity", "like btn on " + position + " clicked");
        String postId = postList.get(position).getPostId();
        Integer oldLikeCount = postList.get(position).getLikeCount();
        db.getReference().child("Post").child(postId).child("postLikeCount").setValue(oldLikeCount+1);
        adapter.notifyDataSetChanged();
        Log.d("HomeActivity", "like btn on " + position + " finished");
    }

    @Override
    public void onClickCommentBtn(View view, int position) {
        Intent i = new Intent(HomeActivity.this, PostDetailActivity.class);
        i.putExtra("postId", postList.get(position).getPostId());
        startActivity(i);
    }


    public void notifyUserComment() {
        db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        rf = db.getReference().child("Comment");
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot sn : snapshot.getChildren()){
                    String originalPostUserId = sn.child("originalPostUserId").getValue(String.class);
                    Boolean isNotified = sn.child("commentIsNotified").getValue(Boolean.class);
//                    Log.d("commentId", sn.getKey().toString());
                    if(originalPostUserId.equals(User.getCurrentUser().getUsername()) && !isNotified){
                        rf.child(sn.getKey().toString()).child("commentIsNotified").setValue(true);
                        CommentNotificationService.makeCommentNotification(sn.child("commentDescription").getValue(String.class), sn.child("commentPostId").getValue(String.class), getApplicationContext());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}