package com.example.kitabercerita.adapter;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitabercerita.R;

public class PostViewHolder extends RecyclerView.ViewHolder {
    ImageView profileImageView;
    TextView userTxt, descriptionTxt, likeCountTxt, commentCountTxt;
    ImageButton likeBtn, commentBtn, shareBtn;
    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImageView = itemView.findViewById(com.example.kitabercerita.R.id.profileImageView);
        userTxt = itemView.findViewById(R.id.userTxt);
        descriptionTxt = itemView.findViewById(R.id.descriptionTxt);
        likeCountTxt = itemView.findViewById(R.id.likeCountTxt);
        commentCountTxt = itemView.findViewById(R.id.commentCountTxt);
        likeBtn = itemView.findViewById(R.id.likeBtn);
        commentBtn = itemView.findViewById(R.id.commentBtn);
        shareBtn = itemView.findViewById(R.id.shareBtn);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
