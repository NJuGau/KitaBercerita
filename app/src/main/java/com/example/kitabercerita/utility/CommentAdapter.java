package com.example.kitabercerita.utility;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitabercerita.R;
import com.example.kitabercerita.model.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    Context context;
    List<Comment> comments;
    CommentClickListener clickListener;

    public CommentAdapter(Context context, List<Comment> comments, CommentClickListener listener){
        this.context = context;
        this.comments = comments;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_comment_adapter, parent, false);
        return new CommentViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment  = comments.get(position);
        holder.userTxt.setText(comment.getUserId()); //TODO:Add user name
        holder.descriptionTxt.setText(comment.getDescription());
        holder.likeCountTxt.setText(comment.getLikeCount().toString());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }
}