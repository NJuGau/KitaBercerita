package com.example.kitabercerita.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitabercerita.R;

public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private ImageView profileImageView;
    private TextView userTxt, descriptionTxt, likeCountTxt, commentCountTxt;
    private ImageButton likeBtn, commentBtn, shareBtn;
    private ClickListener clickListener;
    public PostViewHolder(@NonNull View itemView, ClickListener listener) {
        super(itemView);
        clickListener = listener;
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
                if(clickListener != null) clickListener.onClick(view, getAdapterPosition());
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    public void setProfileImageView(ImageView profileImageView) {
        this.profileImageView = profileImageView;
    }

    public TextView getUserTxt() {
        return userTxt;
    }

    public void setUserTxt(TextView userTxt) {
        this.userTxt = userTxt;
    }

    public TextView getDescriptionTxt() {
        return descriptionTxt;
    }

    public void setDescriptionTxt(TextView descriptionTxt) {
        this.descriptionTxt = descriptionTxt;
    }

    public TextView getLikeCountTxt() {
        return likeCountTxt;
    }

    public void setLikeCountTxt(TextView likeCountTxt) {
        this.likeCountTxt = likeCountTxt;
    }

    public TextView getCommentCountTxt() {
        return commentCountTxt;
    }

    public void setCommentCountTxt(TextView commentCountTxt) {
        this.commentCountTxt = commentCountTxt;
    }

    public ImageButton getLikeBtn() {
        return likeBtn;
    }

    public void setLikeBtn(ImageButton likeBtn) {
        this.likeBtn = likeBtn;
    }

    public ImageButton getCommentBtn() {
        return commentBtn;
    }

    public void setCommentBtn(ImageButton commentBtn) {
        this.commentBtn = commentBtn;
    }

    public ImageButton getShareBtn() {
        return shareBtn;
    }

    public void setShareBtn(ImageButton shareBtn) {
        this.shareBtn = shareBtn;
    }

}
