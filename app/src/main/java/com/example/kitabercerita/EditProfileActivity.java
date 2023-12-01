package com.example.kitabercerita;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfileActivity extends AppCompatActivity {

    Button backBtn, saveBtn;
    EditText nameEt, statusEt;
    String name, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        backBtn = findViewById(R.id.backBtn);
        saveBtn = findViewById(R.id.saveBtn);

        nameEt = findViewById(R.id.nameEt);
        statusEt = findViewById(R.id.statusEt);

        //ambil dari db
//        name.setText();
//        status.setText();

        //disini
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ambil perubahannya
                name = String.valueOf(nameEt.getText());
                status = String.valueOf(statusEt.getText());

                //update db

                //back
                finish();
            }
        });


    }
}