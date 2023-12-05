package com.example.kitabercerita.model;

public class Post {
    private String postId;
    private String description;
    private String userId;
    private Integer likeCount;

    public Post(String postId, String description, String userId) {
        this.postId = postId;
        this.description = description;
        this.userId = userId;
        this.likeCount = 0;
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

    public Integer getCommentCount(){
        return 0;
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
}
