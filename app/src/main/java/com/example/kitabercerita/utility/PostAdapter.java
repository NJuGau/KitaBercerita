package com.example.kitabercerita.utility;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kitabercerita.R;
import com.example.kitabercerita.model.Post;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private List<Post> posts;
    private PostClickListener clickListener;
    StorageReference storageReference;

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
        holder.getUserTxt().setText(post.getUserId());
        holder.getDescriptionTxt().setText(post.getDescription());
        holder.getLikeCountTxt().setText(post.getLikeCount().toString());
        holder.getCommentCountTxt().setText(post.getCommentCount().toString());
        FirebaseDatabase.getInstance().getReference().child("User").child(post.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                String imageId = userSnapshot.child("image").getValue(String.class);
                Log.d("image", imageId);
                storageReference = FirebaseStorage.getInstance().getReference("images/"+imageId);

                try {
                    File localFile = File.createTempFile("tempFile", "");
                    storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                            holder.getProfileImageView().setImageBitmap(bitmap);
                            Log.d("image binding", "success " + imageId);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setClickListener(PostClickListener clickListener) {
        this.clickListener = clickListener;
    }


}