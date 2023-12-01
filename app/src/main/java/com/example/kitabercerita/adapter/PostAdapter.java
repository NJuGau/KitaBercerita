package com.example.kitabercerita.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.kitabercerita.R;
import com.example.kitabercerita.model.Post;

import java.util.List;
import java.util.zip.Inflater;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private List<Post> posts;
    private ClickListener clickListener;

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
//        holder.userTxt.setText(); //Add user name
        holder.getDescriptionTxt().setText(post.getDescription());
        holder.getLikeCountTxt().setText(post.getLikeCount().toString());
        holder.getCommentCountTxt().setText(post.getCommentCount().toString());
        holder.getLikeBtn().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post.setLikeCount(post.getLikeCount()+1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}