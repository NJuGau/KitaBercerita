package com.example.kitabercerita;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kitabercerita.databinding.ActivityEditProfileBinding;
import com.example.kitabercerita.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    Button saveBtn, uploadBtn, selectBtn;
    EditText emailEt, passEt, phoNumEt, statusEt;
    TextView nameTv, backBtn;
    String email, pass, phoNum, status, uname, image;
    FirebaseDatabase db;
    DatabaseReference rf;

    ImageView imageView;
    Uri imageUri;
    Bitmap bitmap;
    ActivityEditProfileBinding binding;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);
        uploadBtn = findViewById(R.id.uploadBtn);
        selectBtn = findViewById(R.id.selectBtn);

        imageView = findViewById(R.id.imageView);
        nameTv = findViewById(R.id.nameTv);
        emailEt = findViewById(R.id.emailEt);
        passEt = findViewById(R.id.passEt);
        phoNumEt = findViewById(R.id.phoneNumberEt);
        statusEt = findViewById(R.id.statusEt);

        //tampilkan current data
        User u = User.getCurrentUser();
        uname = u.getUsername();

        nameTv.setText(uname);
        emailEt.setText(u.getEmail());
        passEt.setText(u.getPassword());
        phoNumEt.setText(u.getPhoneNumber());
        statusEt.setText(u.getStatus());

        db = FirebaseDatabase.getInstance("https://mobile-78ad2-default-rtdb.asia-southeast1.firebasedatabase.app/");
        rf = db.getReference().child("User").child(uname);

        //tampilkan foto pp
        image = u.getImage();
        storageReference = FirebaseStorage.getInstance().getReference("images/"+image);

        try {
            File localFile = File.createTempFile("tempFile", "");
            storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    binding.imageView.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //change foto pp
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkStoragePermission();
            }
        });

        //button
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null) {
                    final StorageReference myRef = FirebaseStorage.getInstance().getReference().child("images/" + imageUri.getLastPathSegment());
                    myRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            myRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    if (uri != null) {
                                        String imgFN = uri.getLastPathSegment();
                                        int lastSlashIndex = imgFN.lastIndexOf("/");
                                        image = imgFN.substring(lastSlashIndex + 1);
                                        Toast.makeText(EditProfileActivity.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ambil perubahannya
                email = String.valueOf(emailEt.getText());
                pass = String.valueOf(passEt.getText());
                phoNum = String.valueOf(phoNumEt.getText());
                status = String.valueOf(statusEt.getText());

                Map<String, Object> updates = new HashMap<>();

                //cek update -> update db & current user
                if(u.getEmail()!=email){
                    updates.put("email", email);
                    User.getCurrentUser().setEmail(email);
                }
                if(u.getPassword()!=pass){
                    updates.put("password", pass);
                    User.getCurrentUser().setPassword(pass);
                }
                if(u.getPhoneNumber()!=phoNum){
                    updates.put("phoneNumber", phoNum);
                    User.getCurrentUser().setPhoneNumber(phoNum);
                }
                if(u.getStatus()!=status){
                    updates.put("status", status);
                    User.getCurrentUser().setStatus(status);
                }
                if(u.getImage()!=image){
                    updates.put("image", image);
                    User.getCurrentUser().setImage(image);
                }

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

    private void checkStoragePermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }else{
                selectImage();
            }
        }else{
            selectImage();
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(intent);
    }

    ActivityResultLauncher<Intent> launcher
            = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if(data!=null && data.getData()!=null){
                        imageUri = data.getData();

                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if(imageUri!=null){
                        binding.imageView.setImageBitmap(bitmap);
                    }
                }
            });

}