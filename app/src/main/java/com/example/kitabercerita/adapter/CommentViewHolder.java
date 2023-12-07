package com.example.kitabercerita.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitabercerita.R;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    ImageView profileImageView;
    TextView userTxt, descriptionTxt, likeCountTxt;
    ImageButton likeBtn;

    public CommentViewHolder(@NonNull View itemView, CommentClickListener listener) {
        super(itemView);
        profileImageView = itemView.findViewById(R.id.commentProfileImageView);
        userTxt = itemView.findViewById(R.id.commentUserTxt);
        descriptionTxt = itemView.findViewById(R.id.commentDescriptionTxt);
        likeCountTxt = itemView.findViewById(R.id.commentLikeCountTxt);
        likeBtn = itemView.findViewById(R.id.commentLikeBtn);

        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClickLikeBtn(view, getAdapterPosition());
            }
        });
    }

}
