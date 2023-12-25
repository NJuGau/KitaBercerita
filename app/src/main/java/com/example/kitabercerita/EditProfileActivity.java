package com.example.kitabercerita;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitabercerita.model.Image;
import com.example.kitabercerita.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    Button saveBtn;
    EditText emailEt, passEt, phoNumEt, statusEt;
    TextView nameTv, backBtn;
    String email, pass, phoNum, status, uname;
    FirebaseDatabase db;
    DatabaseReference rf;
//    ImageView imageView;
//    Uri imageUri;

    StorageReference reference = FirebaseStorage.getInstance().getReference().child("images/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);

//        imageView = findViewById(R.id.imageView);
        nameTv = findViewById(R.id.nameTv);
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passEt);
        statusEt = findViewById(R.id.statusEt);

        //ambil dari db
        uname = User.getCurrentUser().getUsername();
        db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        rf = db.getReference().child("User").child(uname);
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot sn) {
                nameTv.setText(uname);
                emailEt.setText(sn.child("email").getValue(String.class));
                passEt.setText(sn.child("password").getValue(String.class));
                phoNumEt.setText(sn.child("phoneNumber").getValue(String.class));
                statusEt.setText(sn.child("status").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("tes");
            }
        });

        //disini
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //image
//        if(User.getCurrentUser().getImage().equals("1")){
//            imageView.setImageResource(R.drawable.nopp);
//        }else{
//            imageView.setImageURI(imageUri);
//        }

//        Image img = new Image();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nopp);
//
//        if (bitmap != null) {
//            Bitmap circularBitmap = img.getCircularBitmap(bitmap);
//            imageView.setImageBitmap(circularBitmap);
//        }


//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickImageFromGallery();
//            }
//        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ambil perubahannya
                email = String.valueOf(emailEt.getText());
                pass = String.valueOf(passEt.getText());
                phoNum = String.valueOf(phoNumEt.getText());
                status = String.valueOf(statusEt.getText());

                //update db
                Map<String, Object> updates = new HashMap<>();
                updates.put("email", email);
                updates.put("password", pass);
                updates.put("phoneNumber", phoNum);
                updates.put("status", status);

                rf.updateChildren(updates)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data updated successfully
                                Log.d("Firebase", "Profile updated successfully");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Handle error
                                Log.e("Firebase", "Error updating: " + e.getMessage());
                            }
                        });

                //back
                finish();
            }
        });
    }

    // Create an ActivityResultLauncher for picking an image
//    private ActivityResultLauncher<String> imagePickerLauncher = registerForActivityResult(
//            new ActivityResultContracts.GetContent(),
//            uri -> {
//                // Handle the selected image Uri here
//                if (uri != null) {
//                    // Use the selected image Uri as needed (e.g., display it in an ImageView)
//                    imageView.setImageURI(uri);
//                }
//            }
//    );

    // Call this method when you want to pick an image from the gallery
//    private void pickImageFromGallery() {
//        imagePickerLauncher.launch("image/*");
//    }

}