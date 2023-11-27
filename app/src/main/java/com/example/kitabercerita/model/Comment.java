package com.example.kitabercerita.model;

public class Comment {
    private int commentId;
    private String description;
    private Integer likeCount;
    private int userId;
    private int postId;

    public Comment(String description, int userId, int postId) {
        this.description = description;
        this.userId = userId;
        this.postId = postId;
        this.likeCount = 0;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
