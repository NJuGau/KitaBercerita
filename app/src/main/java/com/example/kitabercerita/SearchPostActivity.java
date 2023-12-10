package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kitabercerita.utility.PostAdapter;
import com.example.kitabercerita.utility.PostClickListener;
import com.example.kitabercerita.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class SearchPostActivity extends AppCompatActivity implements PostClickListener {

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
    EditText editText;
    RecyclerView top3View;
    ArrayList<Post> postList;
    FirebaseDatabase db;
    DatabaseReference rf;
    PostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);

        EditText editText = findViewById(R.id.searchedittext);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // User pressed the Enter key, start the new activity
                    String userInput = editText.getText().toString();

                    Log.d("SearchPostActivity", "User Input: " + userInput);

                    Intent intent = new Intent(SearchPostActivity.this, SearchPostResultActivity.class);
                    intent.putExtra("userInput", userInput);
                    startActivity(intent);

                    return true; // Consume the event
                }
                return false; // Let the system handle the event
            }
        });

        top3View = findViewById(R.id.top3View);
        postList = new ArrayList<>();

        top3View.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(this, postList);
        adapter.setClickListener(this);
        top3View.setAdapter(adapter);

        db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        rf = db.getReference().child("Post");
        rf.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postList.clear();
                for(DataSnapshot sn : snapshot.getChildren()){
                    Integer postLikeCount = sn.child("postLikeCount").getValue(Integer.class);
                    String postId = sn.getKey().toString();
                    String postDescription = sn.child("postDescription").getValue(String.class);
                    String postUserId = sn.child("postUserId").getValue(String.class);
                    Integer postCommentCount = sn.child("postCommentCount").getValue(Integer.class);
                    postList.add(new Post(postId, postDescription, postUserId, postLikeCount, postCommentCount));
                }
                Collections.sort(postList);
                postList = (ArrayList<Post>) postList.stream().limit(3).collect(Collectors.toList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClickLikeBtn(View view, int position) {
        Log.d("SearchPost", "like btn on " + position + " clicked");
        String postId = postList.get(position).getPostId();
        Integer oldLikeCount = postList.get(position).getLikeCount();
        db.getReference().child("Post").child(postId).child("postLikeCount").setValue(oldLikeCount+1);
        postList.get(position).setLikeCount(oldLikeCount+1); //TODO: Somehow this did not work
        adapter.notifyDataSetChanged();
        Log.d("SearchPost", "like btn on " + position + " finished");
    }

    @Override
    public void onClickCommentBtn(View view, int position) {
        Intent i = new Intent(SearchPostActivity.this, PostDetailActivity.class);
        i.putExtra("postId", postList.get(position).getPostId());
        startActivity(i);
    }
}