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
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
    EditText editText;
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

                    Intent intent = new Intent(SearchPostActivity.this, SearchPost2Activity.class);
                    intent.putExtra("userInput", userInput);
                    startActivity(intent);

                    return true; // Consume the event
                }
                return false; // Let the system handle the event
            }
        });
    }
}