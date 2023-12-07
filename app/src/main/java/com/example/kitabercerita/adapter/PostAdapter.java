package com.example.kitabercerita.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitabercerita.R;
import com.example.kitabercerita.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private List<Post> posts;
    private PostClickListener clickListener;

    public PostAdapter(Context context, List<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.activity_post_adapter, parent, false);
        return new PostViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.getUserTxt().setText(post.getUserId()); //TODO: Add user name
        holder.getDescriptionTxt().setText(post.getDescription());
        holder.getLikeCountTxt().setText(post.getLikeCount().toString());
        holder.getCommentCountTxt().setText(post.getCommentCount().toString());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setClickListener(PostClickListener clickListener) {
        this.clickListener = clickListener;
    }


}