package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent = null;
        switch(itemId){
            case R.id.homeMenu:
                intent = new Intent(this.getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.searchMenu:
                intent = new Intent(this.getApplicationContext(), SearchPostActivity.class);
                startActivity(intent);
                break;
            case R.id.profileMenu:
                intent = new Intent(this.getApplicationContext(), ProfileViewActivity.class);
                startActivity(intent);
                break;
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
        setContentView(R.layout.activity_home);
    }
}