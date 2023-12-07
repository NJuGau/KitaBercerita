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

import com.example.kitabercerita.adapter.PostAdapter;
import com.example.kitabercerita.model.Post;

import java.util.ArrayList;

public class SearchPost2Activity extends AppCompatActivity{

    private ArrayList<Post> postList;
    RecyclerView postView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post2);
        postView = findViewById(R.id.searchpostView);
        postList = new ArrayList<>();
        //TODO: insert data to postList using Firebase

        postList.add(new Post("129031209381293","lololol", "129031209381293", 0, 0));
        postList.add(new Post("afkhdsif8ew9fcs","lululul", "129031209381293", 0, 0));
        postList.add(new Post("sdufhsduf89sfs9","lelelel", "129031209381293", 0, 0));

        postView.setLayoutManager(new LinearLayoutManager(this));
        PostAdapter adapter = new PostAdapter(this, postList);
        postView.setAdapter(adapter);

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
}