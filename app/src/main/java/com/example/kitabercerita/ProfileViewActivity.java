package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.kitabercerita.databinding.ActivityProfileViewBinding;
import com.example.kitabercerita.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ProfileViewActivity extends AppCompatActivity {

    ImageView ppIv;
    TextView nameTv, statusTv;
    Button editBtn, logoutBtn;

    Switch LnDSwitch;
    Boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment;
    ActivityProfileViewBinding binding;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //profile picture
        ppIv = findViewById(R.id.ppIv);
        String imageID = User.getCurrentUser().getImage();
        storageReference = FirebaseStorage.getInstance().getReference("images/"+imageID);

        try {
            File localFile = File.createTempFile("tempFile", "");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    binding.ppIv.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //text view
        nameTv = findViewById(R.id.nameTv);
        statusTv = findViewById(R.id.statusTv);

        //tampilkan informasi current user
        User u = User.getCurrentUser();
        nameTv.setText(u.getUsername());
        statusTv.setText(u.getStatus());

        //button
        editBtn = findViewById(R.id.editProfileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        LnDSwitch = findViewById(R.id.LnDSwitch);

        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileViewActivity.this, AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        nightMODE = sharedPreferences.getBoolean("nightMode", false);
        LnDSwitch.setChecked(nightMODE);


        if(nightMODE){
            LnDSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        LnDSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nightMODE){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", false);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("nightMode", true);
                }
                editor.apply();
            }
        });

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
                        intent = new Intent(ProfileViewActivity.this, HomeActivity.class);
                        startActivity(intent);
                        switchFragment(homeFragment);
                        return true;

                    case R.id.searchMenu:
                        // Handle search item click
                        intent = new Intent(ProfileViewActivity.this, SearchPostActivity.class);
                        startActivity(intent);
                        switchFragment(searchFragment);
                        return true;

                    case R.id.profileMenu:
                        // Handle profile item click
                        intent = new Intent(ProfileViewActivity.this, ProfileViewActivity.class);
                        startActivity(intent);
                        switchFragment(profileFragment);
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.hide(activeFragment).add(R.id.fragmentContainer, targetFragment).commit();
        } else {
            transaction.hide(activeFragment).show(targetFragment).commit();
        }
        activeFragment = targetFragment;
    }

}