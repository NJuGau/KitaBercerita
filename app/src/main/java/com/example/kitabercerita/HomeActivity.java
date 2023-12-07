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

import com.example.kitabercerita.adapter.PostClickListener;
import com.example.kitabercerita.adapter.PostAdapter;
import com.example.kitabercerita.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements PostClickListener {

    ArrayList<Post> postList;
    FirebaseDatabase db;
    DatabaseReference rf;
    PostAdapter adapter;
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
        //test -kp
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
        //TODO: insert data to postList using Firebase
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
//                    HashMap<String, Object> map = (HashMap<String, Object>) sn.getValue();
//                    Log.d("post Id", sn.getKey().toString());
//                    Log.d("description", sn.child("postDescription").getValue(String.class));
//                    Log.d("user", sn.child("postUserId").getValue(String.class));

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

//        postList.add(new Post("129031209381293","lololol" +
//                "xcxzczxczxczxczxczxc" +
//                "zxczxczxczxc", "129031209381293"));
//        postList.add(new Post("afkhdsif8ew9fcs","lululul", "129031209381293"));
//        postList.add(new Post("sdufhsduf89sfs9","lelelel", "129031209381293"));
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
}