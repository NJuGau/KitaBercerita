package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kitabercerita.utility.PostAdapter;
import com.example.kitabercerita.utility.PostClickListener;
import com.example.kitabercerita.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPostActivity extends AppCompatActivity implements PostClickListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment;

    EditText editText;
    RecyclerView top3View;
    ArrayList<Post> postList;
    FirebaseDatabase db;
    DatabaseReference rf;
    PostAdapter adapter;
    Button searchBtn;

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.hide(activeFragment).add(R.id.fragmentContainer, targetFragment).commit();
        } else {
            transaction.hide(activeFragment).show(targetFragment).commit();
        }
        activeFragment = targetFragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_post);

        EditText editText = findViewById(R.id.searchedittext);
        searchBtn = findViewById(R.id.searchBtn);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.darker_gray)));
        bottomNavigationView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(android.R.color.darker_gray)));

        // Initialize your fragments
        final Fragment homeFragment = new HomeFragment();
        final Fragment searchFragment = new SearchFragment();
        final Fragment profileFragment = new ProfileFragment();

        // Set the default fragment
        activeFragment = searchFragment;
        fragmentManager.beginTransaction().add(R.id.fragmentContainer, homeFragment).commit();

        // Set listener to handle item clicks
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.homeMenu:
                        // Handle home item click
                        intent = new Intent(SearchPostActivity.this, HomeActivity.class);
                        startActivity(intent);
                        switchFragment(homeFragment);
                        return true;

                    case R.id.searchMenu:
                        // Handle search item click
                        intent = new Intent(SearchPostActivity.this, SearchPostActivity.class);
                        startActivity(intent);
                        switchFragment(searchFragment);
                        return true;

                    case R.id.profileMenu:
                        // Handle profile item click
                        intent = new Intent(SearchPostActivity.this, ProfileViewActivity.class);
                        startActivity(intent);
                        switchFragment(profileFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (keyEvent != null && keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    // User pressed the Enter key, start the new activity
                    String userInput = editText.getText().toString();

                    Log.d("SearchPostActivity", "User Input: " + userInput);

                    if(!userInput.equals("")) {
                        Intent intent = new Intent(SearchPostActivity.this, SearchPostResultActivity.class);
                        intent.putExtra("userInput", userInput);
                        startActivity(intent);
                    }

                    return true; // Consume the event
                }
                return false; // Let the system handle the event
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userInput = editText.getText().toString();

                Log.d("SearchPostActivity", "User Input: " + userInput);
                if(!userInput.equals("")) {
                    Intent intent = new Intent(SearchPostActivity.this, SearchPostResultActivity.class);
                    intent.putExtra("userInput", userInput);
                    startActivity(intent);
                }
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
                ArrayList<Post> allPost = new ArrayList<>();
                postList.clear();
                for(DataSnapshot sn : snapshot.getChildren()){
                    Integer postLikeCount = sn.child("postLikeCount").getValue(Integer.class);
                    String postId = sn.getKey().toString();
                    String postDescription = sn.child("postDescription").getValue(String.class);
                    String postUserId = sn.child("postUserId").getValue(String.class);
                    Integer postCommentCount = sn.child("postCommentCount").getValue(Integer.class);
                    allPost.add(new Post(postId, postDescription, postUserId, postLikeCount, postCommentCount));
                }
                Collections.sort(allPost);
                for(int i = 0; i < 2 && i < allPost.size(); i++){
                    postList.add(allPost.get(i));
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