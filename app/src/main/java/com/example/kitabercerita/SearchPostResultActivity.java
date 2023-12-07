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
import android.widget.TextView;

import com.example.kitabercerita.adapter.PostAdapter;
import com.example.kitabercerita.adapter.PostClickListener;
import com.example.kitabercerita.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class SearchPostResultActivity extends AppCompatActivity implements PostClickListener {

    ArrayList<Post> postList;
    RecyclerView postView;
    FirebaseDatabase db;
    DatabaseReference rf;
    PostAdapter adapter;
    TextView backBtn, searchTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post_result);
        postView = findViewById(R.id.searchpostView);
        backBtn = findViewById(R.id.searchBackBtn);
        searchTextView = findViewById(R.id.searchedQueryTxtView);
        postList = new ArrayList<>();
        String userQuery = getIntent().getStringExtra("userInput");

        searchTextView.setText("Result for '" + userQuery + "'");

        postView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(this, postList);
        postView.setAdapter(adapter);
        adapter.setClickListener(this);

        db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        rf = db.getReference().child("Post");
        rf.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot sn : snapshot.getChildren()){
                    String postDescription = sn.child("postDescription").getValue(String.class);
                    if(postDescription.contains(userQuery)){
                        Integer postLikeCount = sn.child("postLikeCount").getValue(Integer.class);
                        String postId = sn.getKey().toString();
                        String postUserId = sn.child("postUserId").getValue(String.class);
                        Integer postCommentCount = sn.child("postCommentCount").getValue(Integer.class);
                        postList.add(new Post(postId, postDescription, postUserId, postLikeCount, postCommentCount));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

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
        Intent i = new Intent(SearchPostResultActivity.this, PostDetailActivity.class);
        i.putExtra("postId", postList.get(position).getPostId());
        startActivity(i);
    }
}