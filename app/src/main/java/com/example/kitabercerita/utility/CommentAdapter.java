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
import com.example.kitabercerita.model.Comment;
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

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    Context context;
    List<Comment> comments;
    CommentClickListener clickListener;
    StorageReference storageReference;

    public CommentAdapter(Context context, List<Comment> comments, CommentClickListener listener){
        super();
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
        holder.userTxt.setText(comment.getUserId());
        holder.descriptionTxt.setText(comment.getDescription());
        holder.likeCountTxt.setText(comment.getLikeCount().toString());
        FirebaseDatabase.getInstance().getReference().child("User").child(comment.getUserId()).addValueEventListener(new ValueEventListener() {
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
                            holder.profileImageView.setImageBitmap(bitmap);
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
        return comments.size();
    }
}