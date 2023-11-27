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
import android.widget.ListView;

import com.example.kitabercerita.adapter.PostAdapter;
import com.example.kitabercerita.adapter.Search_top3Adapter;
import com.example.kitabercerita.model.Post;

import java.util.ArrayList;

public class SearchPostActivity extends AppCompatActivity {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);
        ceritaTrendingHariIni();
        top3();
    }

    private void top3() {
        RecyclerView postView;
        postView = findViewById(R.id.search_post_top3);
        ArrayList<Post> postList = new ArrayList<>();
        //TODO: insert data to postList using Firebase

        postList.add(new Post("lololol", 1));
        postList.add(new Post("lululul", 2));
        postList.add(new Post("lelelel", 1));

        postView.setLayoutManager(new LinearLayoutManager(this));
        postView.setAdapter(new PostAdapter(this, postList));
    }


    private void ceritaTrendingHariIni(){
        ListView listView = findViewById(R.id.listView);
//        listView.setEnabled(false);
        // Example data
        ArrayList<String> dataList = new ArrayList<>();
        dataList.add("Banyak dilihat");
        dataList.add("Banyak dikomentar");
        dataList.add("Banyak disebar");
        dataList.add("Banyak disukai");

        ArrayList<String> dataList1 = new ArrayList<>();
        dataList1.add("Parkir");
        dataList1.add("Toilet");
        dataList1.add("Lift");
        dataList1.add("Dosen baca PPT");

        ArrayList<String> dataList2 = new ArrayList<>();
        dataList2.add("3 posts");
        dataList2.add("1 posts");
        dataList2.add("2 posts");
        dataList2.add("4 posts");

        // Create and set the adapter
        Search_top3Adapter adapter = new Search_top3Adapter(this, dataList, dataList1, dataList2);
        listView.setAdapter(adapter);
    }
}