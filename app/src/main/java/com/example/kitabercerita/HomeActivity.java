package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.kitabercerita.adapter.ClickListener;
import com.example.kitabercerita.adapter.PostAdapter;
import com.example.kitabercerita.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity implements ClickListener {

    private ArrayList<Post> postList;
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

        postList.add(new Post("129031209381293","lololol", "129031209381293"));
        postList.add(new Post("afkhdsif8ew9fcs","lululul", "129031209381293"));
        postList.add(new Post("sdufhsduf89sfs9","lelelel", "129031209381293"));

        postView.setLayoutManager(new LinearLayoutManager(this));
        PostAdapter adapter = new PostAdapter(this, postList);
        adapter.setClickListener(this);
        postView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view, int position) {
        System.out.println(position + " is clicked!");
        Intent i = new Intent(HomeActivity.this, PostDetailActivity.class);
        i.putExtra("postId", postList.get(position).getPostId());
        startActivity(i);
    }
}