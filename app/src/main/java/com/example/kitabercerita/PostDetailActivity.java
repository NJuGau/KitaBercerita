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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kitabercerita.adapter.CommentAdapter;
import com.example.kitabercerita.model.Comment;

import java.util.ArrayList;

public class PostDetailActivity extends AppCompatActivity {

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
    private TextView userTxt, descriptionTxt, likeCountTxt, commentCountTxt;
    private ImageButton likeBtn, commentBtn, shareBtn;
    private RecyclerView commentRecyclerView;
    private ArrayList<Comment> commentList;

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
        shareBtn = findViewById(R.id.detailShareBtn);
        commentRecyclerView = findViewById(R.id.commentRecyclerView);

        //TODO: get data from database

        //uses dummy data
        userTxt.setText("Username");
        descriptionTxt.setText("Some decription");
        likeCountTxt.setText("1");
        commentCountTxt.setText("1");

        commentList = new ArrayList<>();
        commentList.add(new Comment("129031209381293","haloooo", "129031209381293", "129031209381293"));
        commentList.add(new Comment("129031209381293","sfsdfdsjfndsklf", "129031209381293", "129031209381293"));
        commentList.add(new Comment("129031209381293","231i3n12kj3n", "129031209381293", "129031209381293"));

        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommentAdapter adapter = new CommentAdapter(this, commentList);
        commentRecyclerView.setAdapter(adapter);
    }
}