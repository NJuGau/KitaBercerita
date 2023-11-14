package com.example.kitabercerita.model;

public class Post {
    private int postId;
    private String description;
    private int userId;
    private Integer likeCount;

    public Post(String description, int userId) {
        this.description = description;
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount(){
        return 0;
    }
}
