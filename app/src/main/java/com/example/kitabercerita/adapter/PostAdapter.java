package com.example.kitabercerita.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.kitabercerita.model.Post;

import java.util.List;
import java.util.zip.Inflater;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}