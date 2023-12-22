package com.example.kitabercerita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileViewActivity extends AppCompatActivity {

    ImageView ppIv;
    TextView nameTv, statusTv;
    Button editBtn, logoutBtn;
    DatabaseReference rf;
    String sender;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        //profile picture
        ppIv = findViewById(R.id.ppIv);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nopp);

        if (bitmap != null) {
            Bitmap circularBitmap = getCircularBitmap(bitmap);
            ppIv.setImageBitmap(circularBitmap);
        }

        //text view
        nameTv = findViewById(R.id.nameTv);
        statusTv = findViewById(R.id.statusTv);

        Intent intent = getIntent();
        if (intent != null) {
            sender = intent.getStringExtra("EXTRA_MESSAGE");

            if (sender != null) {
                //ambil dari db
                rf = FirebaseDatabase.getInstance().getReference().child("User").child(sender);
                rf.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot sn) {
                        nameTv.setText(sn.child("username").getValue(String.class));
                        statusTv.setText(sn.child("status").getValue(String.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

        //button
        editBtn = findViewById(R.id.editProfileBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

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
                Intent intent = new Intent(ProfileViewActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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

    private Bitmap getCircularBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap circularBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(circularBitmap);

        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        float radius = Math.min(width, height) / 2f;
        canvas.drawCircle(width / 2f, height / 2f, radius, paint);

        return circularBitmap;
    }

}