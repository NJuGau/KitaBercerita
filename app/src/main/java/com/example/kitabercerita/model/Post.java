package com.example.kitabercerita.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Post implements Comparable<Post> {
    private String postId;
    private String description;
    private String userId;
    private Integer likeCount;
    private Integer commentCount;
    private static FirebaseDatabase db;
    private static DatabaseReference rf;

    public Post(String postId, String description, String userId, Integer likeCount, Integer commentCount) {
        this.postId = postId;
        this.description = description;
        this.userId = userId;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public int compareTo(Post post) {
        return post.getLikeCount() - this.likeCount;
    }
}
